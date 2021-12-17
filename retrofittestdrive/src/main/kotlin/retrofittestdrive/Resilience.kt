package retrofittestdrive

import io.github.resilience4j.core.IntervalFunction
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.retry.RetryRegistry
import org.slf4j.LoggerFactory
import retrofit2.*
import java.lang.reflect.Type

private val log = LoggerFactory.getLogger("Resilience")


/**
 * Joining retrofit2 - specifically [CallAdapter.Factory()] and resilience4j
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

/**
 * A singleton configuration
 */
object ResilienceConf {
    private val retryRegistry = RetryRegistry.ofDefaults()

    init {
        RetryConfig.custom<Response<*>>()
            .maxAttempts(3)
            .retryOnResult { response -> response.code().is5xx() }
            .retryExceptions(Exception::class.java)
            .intervalFunction(IntervalFunction.of(500)) // 500, 500, ...
            .build().also {
                retryRegistry.addConfiguration("500error_linear_3times", it)
            }

        RetryConfig.custom<Response<*>>()
            .maxAttempts(3)
            .retryOnResult { response -> response.code().is5xx() }
            .retryExceptions(Exception::class.java)
            .intervalFunction(IntervalFunction.ofExponentialBackoff(500, 2.0)) // 500, 2 * 500, ...
            .build().also {
                retryRegistry.addConfiguration("500error_exponential_3times", it)
            }
    }

    fun retry(retryId: String, configId: String): Retry =
        retryRegistry.retry(retryId, configId)

    private fun Int.is5xx() = this in 500 until 600
}


/**
 * Retry all calls that result in 5xx errors or encounter an [Exception].
 */
class ResilientCall<T>(
    private val invokedBy: String,
    private val callDelegate: Call<T?>
) : Call<T?> by callDelegate {

    // TODO is this really needed ?
    override fun clone() = ResilientCall(invokedBy, callDelegate)

    override fun execute(): Response<T?> {
        val retryId = "$invokedBy.${callDelegate.request().method()}"
        val retry = ResilienceConf.retry(retryId, "500error_linear_3times")

        return Retry.decorateCheckedSupplier(retry) { callDelegate.clone().execute() }.apply()
    }
}



