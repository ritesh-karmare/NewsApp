package rk.information.news.allNews;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import rk.information.news.R;
import rk.information.news.databinding.ActivityAllNewsBinding;
import rk.information.news.network.responseModel.ArticleData;

/**
 * Created by Ritesh on 04/09/2019.
 */

public class AllNewsActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvNews;
    private SwipeRefreshLayout srNews;

    private AllNewsAdapter allNewsAdapter;
    private AllNewsViewModel viewModelDemo;

    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initBindings();
            setupViews();
            initListeners();
            initObservers();
            fetchNews(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Initialize ViewModel, DataBinding
    private void initBindings() {
        viewModelDemo = ViewModelProviders.of(this).
                get(AllNewsViewModel.class);
        // Inflate view and obtain an instance of the binding class.
        ActivityAllNewsBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_news);
        // Assign the component to a property in the binding class.
        dataBinding.setViewModel(viewModelDemo);
        // Specify the current activity as the lifecycle owner.
        dataBinding.setLifecycleOwner(this);

        rvNews = dataBinding.rvNews;
        srNews = dataBinding.srNews;
    }

    // Setup RecyclerView
    private void setupViews() {
        rvNews.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvNews.setHasFixedSize(true);
        rvNews.setAdapter(allNewsAdapter = new AllNewsAdapter(this));
    }

    // Add SwipeRefresh and RecyclerView's scroll listener
    private void initListeners() {
        srNews.setOnRefreshListener(this);
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
                        fetchNews(true);
                        isLoading = true;
                    }
                }
            }
        });
    }

    // Observe the data changes from ViewModel
    private void initObservers() {
        viewModelDemo.articleLiveDataList.observe(this, new Observer<List<ArticleData>>() {
            @Override
            public void onChanged(List<ArticleData> articleDataList) {
                poulateAllNews(articleDataList);
            }
        });

        viewModelDemo.isLoadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                revokeLoadingMore(isLoading);
            }
        });

        viewModelDemo.errorCodeLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                onFailure(integer);
            }
        });
    }

    // On Scroll load more data
    private void fetchNews(boolean isLoadMore) {
        viewModelDemo.fetchAllNews(isLoadMore);
        if (isLoadMore) {
            allNewsAdapter.getList().add(null);
            allNewsAdapter.notifyItemInserted(allNewsAdapter.getList().size() - 1);
        } else srNews.setRefreshing(true);
    }

    // Removed loading views
    private void revokeLoadingMore(boolean isLoadMore) {
        if (srNews.isRefreshing()) srNews.setRefreshing(false);

        if (isLoadMore) {
            isLoading = false;
            allNewsAdapter.getList().remove(allNewsAdapter.getList().size() - 1);
            allNewsAdapter.notifyItemRemoved(allNewsAdapter.getList().size());
        }
    }

    // Add articleDataList to adapter
    public void poulateAllNews(List<ArticleData> allNewsList) {
        allNewsAdapter.addAll(allNewsList);
        allNewsAdapter.notifyDataSetChanged();
    }

    // Display failure message while performing REST API call
    public void onFailure(int errCode) {
        switch (errCode) {
            case -1:
                Toast.makeText(this, getString(R.string.err_connecting), Toast.LENGTH_SHORT).show();
                break;
            case 426:
                Toast.makeText(this, getString(R.string.err_too_many_request), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, getString(R.string.err_server), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        allNewsAdapter.reset();
        viewModelDemo.fetchAllNews(false);
    }
}
