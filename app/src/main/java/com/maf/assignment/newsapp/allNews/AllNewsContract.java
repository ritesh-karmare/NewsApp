package com.maf.assignment.newsapp.allNews;

import com.maf.assignment.newsapp.network.responseModel.ArticleData;

import java.util.List;

/**
 * Created by Ritesh on 04/09/2019.
 */

public interface AllNewsContract {

    interface Presenter {

        void fetchAllNews(boolean isLoadMore);

        void unBind();
    }

    interface View {

        void poulateAllNews(List<ArticleData> allNewsList, boolean isLoadMore);

    }

}
