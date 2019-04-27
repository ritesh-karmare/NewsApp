package rk.information.news.network;


import rk.information.news.network.responseModel.AllNewsRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ritesh on 04/09/2019.
 */

public interface APIImpl {

    // Get all news (10 news items at a time , source = "bbc-news")
    @GET("/v2/everything?sources=bbc-news&pageSize=10")
    Call<AllNewsRes> getAllNews(@Query("page") int pageNum);


}
