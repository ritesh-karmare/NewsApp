package com.maf.assignment.newsapp.alNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.maf.assignment.newsapp.R;
import com.maf.assignment.newsapp.network.responseModel.AllNewsRes;
import com.maf.assignment.newsapp.network.responseModel.ArticleData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ritesh on 04/09/2019.
 */

public class AllNewsActivity extends AppCompatActivity implements AllNewsContract.View, SwipeRefreshLayout.OnRefreshListener {

    private AllNewsAdapter allNewsAdapter;
    private AllNewsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        setupViews();
    }

    private void setupViews() {
        try {
            SwipeRefreshLayout srNews = findViewById(R.id.sr_news);
            RecyclerView rvNews = findViewById(R.id.rv_news);

            srNews.setOnRefreshListener(this);

            rvNews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
            rvNews.setAdapter(allNewsAdapter = new AllNewsAdapter(this));

            presenter = new AllNewsPresenter(this);
            presenter.fetchAllNews();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void poulateAllNews(List<ArticleData> allNewsList) {
        allNewsAdapter.addAll(allNewsList);
        allNewsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
    }

    @Override
    public void onRefresh() {
        allNewsAdapter.reset();
        presenter.fetchAllNews();
    }
}
