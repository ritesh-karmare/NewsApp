package rk.information.news.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rk.information.news.BuildConfig
import java.util.concurrent.TimeUnit

/**
 * Created by Ritesh on 04/09/2019.
 */
object ApiClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                val logging = HttpLoggingInterceptor()
                if (BuildConfig.DEBUG) logging.setLevel(HttpLoggingInterceptor.Level.BODY) else logging.setLevel(HttpLoggingInterceptor.Level.NONE)
                val okHttpClient = OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(RequestInterceptor())
                        .addInterceptor(logging)
                        .build()
                retrofit = Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
}