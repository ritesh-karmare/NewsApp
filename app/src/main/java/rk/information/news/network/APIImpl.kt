package rk.information.news.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import rk.information.news.network.responseModel.AllNewsRes

/**
 * Created by Ritesh on 04/09/2019.
 */
interface APIImpl {
    // Get all news (10 news items at a time , source = "bbc-news")
    @GET("/v2/everything?pageSize=20&page=1&sortBy=publishedAt")
    fun getAllNews(
            @Query("q") searchQuery: String?,
            @Query("from") from: String?): Call<AllNewsRes?>?
}