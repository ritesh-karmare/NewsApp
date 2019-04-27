package rk.information.news.allNews;

import rk.information.news.network.responseModel.ArticleData;

import java.util.List;

/**
 * Created by Ritesh on 04/09/2019.
 */

// Contract between the View and the Presenter for displaying AllNews
public interface AllNewsContract {

    interface Presenter {

        void fetchAllNews(boolean isLoadMore);

        void unBind();
    }

    interface View {

        void poulateAllNews(List<ArticleData> allNewsList, boolean isLoadMore);

        void onFailure(int errCode, boolean isLoadMore);

    }

}
