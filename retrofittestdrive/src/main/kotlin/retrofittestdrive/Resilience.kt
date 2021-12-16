package retrofittestdrive

import io.github.resilience4j.core.IntervalFunction
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.vavr.control.Try
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.*
import java.lang.reflect.Type

private val log = LoggerFactory.getLogger("Resilience")


/**
 * We are joining retrofit2 - specifically [CallAdapter.Factory()] and resilience4j
 * retrofit2 is managing the HTTP call, resilience4j manages retries
 *
 * https://github.com/resilience4j/resilience4j
 */
class ResilientRetrofit : CallAdapter.Factory() {


    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *> {

        @Suppress("UNCHECKED_CAST")
        val next = retrofit.nextCallAdapter(this, returnType, annotations) as CallAdapter<Any?, Any?>

        return object : CallAdapter<Any?, Any?> {
            override fun adapt(call: Call<Any?>): Any {
                val invokedBy = call.request().tag(Invocation::class.java).getInvokerClassMethodArgs()
                return next.adapt(ResilientCall(invokedBy, callDelegate = call))!!
            }

            override fun responseType() = next.responseType()
        }
    }
}


private fun Int.is5xx() = this in 500 until 600


private val retryConf = RetryConfig.custom<Response<*>>()
    .maxAttempts(3)
    .retryOnResult { response -> response.code().is5xx() }
//        .intervalFunction(IntervalFunction.ofExponentialBackoff(500, 2.0)) // 500 , 2 * 500, ...
    .intervalFunction(IntervalFunction.of(500)) // 500 , 500, ...
    .build()


/**
 * Retry all calls that result in 5xx errors or encounter
 * an [Exception].
 */
class ResilientCall<T>(
    private val invokedBy: String,
    private val callDelegate: Call<T?>
) : Call<T?> by callDelegate {

    // TODO reuse
    private val retry: Retry =
        Retry.of("$invokedBy.${callDelegate.request().method().uppercase()}", retryConf)

    // TODO is this really needed ?
    override fun clone() = ResilientCall(invokedBy, callDelegate)

    override fun execute(): Response<T?> {
        return Try.of(Retry.decorateCheckedSupplier(retry, ::exec)).get()
    }

    private fun exec() = run {
        callDelegate.clone().execute().apply {
            logIfNot2xx(this)
        }
    }


    private fun logIfNot2xx(response: Response<T?>) {
        if (!response.isSuccessful) {
            log.warn(
                "{}, status={}, method={}, url={}, message={}",
                invokedBy,
                response.code(),
                request().method(),
                request().url(),
                response.errorBody()?.string() ?: "UNKNOWN"
            )
        }
    }
}




