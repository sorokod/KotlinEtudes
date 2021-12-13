package retrofittestdrive

import io.github.resilience4j.retry.Retry
import io.vavr.control.Try
import retrofit2.*
import java.lang.reflect.Type


/**
 * We are joining two frameworks: retrofit2 - specifically CallAdapter.Factory() and resilience4j
 *
 * https://github.com/resilience4j/resilience4j
 */
class ResilientRetrofit : CallAdapter.Factory() {


    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {

        @Suppress("UNCHECKED_CAST")
        val next = retrofit.nextCallAdapter(this, returnType, annotations) as CallAdapter<Any?, Any?>

        return object : CallAdapter<Any?, Any?> {
            override fun adapt(call: Call<Any?>): Any {
                val invocation = call.request().tag(Invocation::class.java)!!
                val className = invocation.method().declaringClass.simpleName
                val methodName = invocation.method().name
                return next.adapt(ResilientCall(className, methodName, callDelegate = call))!!
            }

            override fun responseType() = next.responseType()
        }
    }
}

/**
 * Retry only the calls that are in the 5XX range. Using the [code]UnacceptableException[code]
 * is clunky and ideally should be replaced with [code]RetryConfig.custom[code]:
 *
 * https://resilience4j.readme.io/docs/retry
 * val rConfig = RetryConfig.custom<Response<*>>()
 *    .maxAttempts(3)
 *    .waitDuration(Duration.ofMillis(500))
 *    .retryOnResult { response -> response.code() == 500 }
 *    .intervalFunction(IntervalFunction.of(...))
 *    .build()
 *
 *
 */
class ResilientCall<T>(
    private val className: String,
    private val methodName: String,
    private val callDelegate: Call<T?>
) : Call<T?> by callDelegate {

    // Retry 3 times x 500 ms.
    private val retry: Retry =
        Retry.ofDefaults("$className.$methodName.${callDelegate.request().method().uppercase()}")

    override fun clone() = ResilientCall(className, methodName, callDelegate)

    override fun execute(): Response<T?> {
        val result = Try.of(Retry.decorateCheckedSupplier(retry, ::exec))
        return result.get()
    }

    private fun exec() = run {
        callDelegate.clone().execute().apply {
            logFailed(this)
            if (code().is5xx()) throw UnacceptableException(code(), className, methodName)
        }
    }

    private fun Int.is5xx() = this in 500 until 600

    private fun logFailed(response: Response<T?>) {
        if (!response.isSuccessful) {
            println(
                "XXX-logFailed $className.$methodName, status=${response.code()}, url=${request().url()}, method=${request().method()}, " +
                        "error_message=${response.errorBody()?.string() ?: "UNKNOWN"}"
            )
        }
    }
}

data class UnacceptableException(val code: Int, val className: String, val methodName: String) : RuntimeException()


