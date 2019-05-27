package rk.information.news.allNews;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rk.information.news.network.APIImpl;
import rk.information.news.network.ApiClient;
import rk.information.news.network.responseModel.AllNewsRes;
import rk.information.news.network.responseModel.ArticleData;

public class AllNewsViewModel extends ViewModel {


    private int pageNum = 1;

    MutableLiveData<List<ArticleData>> articleLiveDataList = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> errorCodeLiveData = new MutableLiveData<>();

    public void fetchAllNews(final boolean isLoadMore) {
        try {
            if (!isLoadMore) pageNum = 1;
            APIImpl apiImpl = ApiClient.getClient().create(APIImpl.class);
            Call<AllNewsRes> call = apiImpl.getAllNews(pageNum);
            call.enqueue(new Callback<AllNewsRes>() {
                @Override
                public void onResponse(@NonNull Call<AllNewsRes> call, @NonNull Response<AllNewsRes> response) {
                    try {
                        isLoadingLiveData.postValue(isLoadMore);
                        if (response.isSuccessful()) {
                            AllNewsRes newsData = response.body();
                            if (newsData != null &&
                                    newsData.getArticles() != null &&
                                    newsData.getArticles().size() > 0) {
                                articleLiveDataList.postValue(newsData.getArticles());
                                managePageNum(true);
                            }
                        } else
                            errorCodeLiveData.postValue(426);

                    } catch (Exception e) {
                        e.printStackTrace();
                        managePageNum(false);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AllNewsRes> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    managePageNum(false);
                    errorCodeLiveData.setValue(-1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            managePageNum(false);
        }
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
