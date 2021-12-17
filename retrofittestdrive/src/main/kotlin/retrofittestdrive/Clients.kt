package retrofittestdrive

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

private val log: Logger = LoggerFactory.getLogger("Clients")


fun <T> buildBaselineClient(service: Class<T>, baseUrl: String): T =
    Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(buildHttpClient(LoggingInterceptor()))
        addConverterFactory(GsonConverterFactory.create()) // A bit less hassle than Jackson
        validateEagerly(true)
    }.build().create(service)


fun <T> buildResilientClient(service: Class<T>, baseUrl: String): T =
    Retrofit.Builder().apply {
        baseUrl(baseUrl)
        client(buildHttpClient(LoggingInterceptor()))
        addConverterFactory(GsonConverterFactory.create()) // A bit less hassle than Jackson
        addCallAdapterFactory(ResilientRetrofit())
        validateEagerly(true)
    }.build().create(service)


/**
 *
 */
private fun buildHttpClient(vararg interceptors: Interceptor): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(1_000, TimeUnit.MILLISECONDS)
        .readTimeout(5_000, TimeUnit.MILLISECONDS)
        .writeTimeout(5_000, TimeUnit.MILLISECONDS)
        .apply {
            interceptors.forEach { addInterceptor(it) }
        }.build()


/**
 * Returns a string representation of the invoker as ClassName.methodName(arguments)
 * e.g.: LocalTestApi.getLocal([good])
 */
fun Invocation?.getInvokerClassMethodArgs(): String {
    return this?.let { "${method().declaringClass.simpleName}.${method().name}(${arguments()})" } ?: ""
}

/**
 * Same as above but without arguments
 * e.g.: LocalTestApi.getLocal
 */
fun Invocation?.getInvokerClassMethod(): String {
    return this?.let { "${method().declaringClass.simpleName}.${method().name}" } ?: ""
}


/**
 * Sample logging interceptor
 */
@OptIn(ExperimentalTime::class)
class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        val (response, duration) = measureTimedValue {
            chain.proceed(request)
        }
        try {
            log.info(
                "{} {} - HTTP {} in {} ms.",
                request.method(),
                request.tag(Invocation::class.java).getInvokerClassMethodArgs(),
                response.code(),
                duration.inWholeMilliseconds
            )
        } finally {
        }
        return response
    }

}

