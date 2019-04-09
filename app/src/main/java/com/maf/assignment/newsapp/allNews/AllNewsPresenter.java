package com.maf.assignment.newsapp.allNews;

import com.maf.assignment.newsapp.network.APIImpl;
import com.maf.assignment.newsapp.network.ApiClient;
import com.maf.assignment.newsapp.network.responseModel.AllNewsRes;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ritesh on 04/09/2019.
 */

public class AllNewsPresenter implements AllNewsContract.Presenter {

    private AllNewsContract.View viewCallback;
    private int pageNum = 1;

    AllNewsPresenter(AllNewsContract.View viewCallback) {
        this.viewCallback = viewCallback;
    }

    @Override
    public void fetchAllNews(final boolean isLoadMore) {
        try {

            APIImpl apiImpl = ApiClient.getClient().create(APIImpl.class);
            Call<AllNewsRes> call = apiImpl.getAllNews(pageNum);
            call.enqueue(new Callback<AllNewsRes>() {
                @Override
                public void onResponse(@NonNull Call<AllNewsRes> call, @NonNull Response<AllNewsRes> response) {
                    try {
                        if (response.isSuccessful()) {
                            AllNewsRes newsData = response.body();
                            if (newsData != null &&
                                    newsData.getArticles() != null &&
                                    newsData.getArticles().size() > 0) {
                                if (viewCallback != null)
                                    viewCallback.poulateAllNews(newsData.getArticles(), isLoadMore);
                                managePageNum(true);
                            }
                        } else {
                            managePageNum(false);
                            if (viewCallback != null)
                                viewCallback.onFailure(426, isLoadMore);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        managePageNum(false);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AllNewsRes> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    managePageNum(false);
                    if (viewCallback != null)
                        viewCallback.onFailure(-1,isLoadMore);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            managePageNum(false);
        }
    }

    @Override
    public void unBind() {
        viewCallback = null;
    }

    // Increment the pageNum if req was successful, else decrement
    private void managePageNum(boolean isIncrement) {
        if (isIncrement)
            pageNum++;
        else {
            if (pageNum > 1)
                pageNum--;
        }
    }
}
