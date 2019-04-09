package com.maf.assignment.newsapp.network;


import com.maf.assignment.newsapp.network.responseModel.AllNewsRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ritesh on 04/09/2019.
 */

public interface APIImpl {

    // Get all news
    @GET("/v2/everything?sources=bbc-news")
    Call<AllNewsRes> getAllNews(@Query("page") int pageNum);


}
