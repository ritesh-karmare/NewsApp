package rk.information.news.network

import okhttp3.Interceptor
import okhttp3.Response
import rk.information.news.BuildConfig
import java.io.IOException

/**
 * Created by Ritesh on 04/09/2019.
 */
// Purpose of interceptor is to append the authorization header
// before sending the request to server.
class RequestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
                .newBuilder()
                .addHeader("authorization", BuildConfig.API_KEY)
                .build()
        return chain.proceed(request)
    }
}