package com.maf.assignment.newsapp.allNews;

import android.os.Bundle;
import android.widget.Toast;

import com.maf.assignment.newsapp.R;
import com.maf.assignment.newsapp.network.responseModel.ArticleData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by Ritesh on 04/09/2019.
 */

public class AllNewsActivity extends AppCompatActivity implements AllNewsContract.View, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvNews;

    private AllNewsAdapter allNewsAdapter;
    private AllNewsContract.Presenter presenter;

    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        setupViews();
        initScrollListener();
    }

    private void setupViews() {
        try {
            SwipeRefreshLayout srNews = findViewById(R.id.sr_news);
            rvNews = findViewById(R.id.rv_news);

            srNews.setOnRefreshListener(this);

            rvNews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rvNews.setAdapter(allNewsAdapter = new AllNewsAdapter(this));

            presenter = new AllNewsPresenter(this);
            presenter.fetchAllNews(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initScrollListener() {
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == allNewsAdapter.getList().size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        allNewsAdapter.getList().add(null);
        presenter.fetchAllNews(true);
        rvNews.post(new Runnable() {
            public void run() {
                allNewsAdapter.notifyItemInserted(allNewsAdapter.getList().size() - 1);
            }
        });
    }

    @Override
    public void poulateAllNews(List<ArticleData> allNewsList, boolean isLoadMore) {
        revokeLoadingMore(isLoadMore);
        allNewsAdapter.addAll(allNewsList);
        allNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(int errCode, boolean isLoadMore) {
        revokeLoadingMore(isLoadMore);
        switch (errCode) {
            case -1:
                Toast.makeText(this, getString(R.string.err_connecting), Toast.LENGTH_SHORT).show();
                break;
            case 426:
                Toast.makeText(this, getString(R.string.err_too_many_request), Toast.LENGTH_SHORT).show();
            default:
                Toast.makeText(this, getString(R.string.err_server), Toast.LENGTH_SHORT).show();
        }
    }

    private void revokeLoadingMore(boolean isLoadMore) {
        if (isLoadMore) {
            isLoading = false;
            allNewsAdapter.getList().remove(allNewsAdapter.getList().size() - 1);
            allNewsAdapter.notifyItemRemoved(allNewsAdapter.getList().size());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
    }

    @Override
    public void onRefresh() {
        allNewsAdapter.reset();
        presenter.fetchAllNews(false);
    }
}
