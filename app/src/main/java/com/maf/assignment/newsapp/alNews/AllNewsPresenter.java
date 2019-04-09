package com.maf.assignment.newsapp.alNews;

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
    public void fetchAllNews() {

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
                                    newsData.getArticles().size() > 0){
                                viewCallback.poulateAllNews(newsData.getArticles());
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AllNewsRes> call, @NonNull Throwable t) {
                    t.printStackTrace();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unBind() {
        viewCallback = null;
    }

    private void managePageNum(boolean isIncrement) {
        if (isIncrement)
            pageNum++;
        else {
            if (pageNum > 1)
                pageNum--;
        }
    }
}
