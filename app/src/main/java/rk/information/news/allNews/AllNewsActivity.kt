package rk.information.news.allNews

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import rk.information.news.R
import rk.information.news.databinding.ActivityAllNewsBinding
import rk.information.news.network.responseModel.ArticleData
import rk.information.news.topics.TopicDialogFragment
import rk.information.news.utils.dataStore.DataStoreHelper
import rk.information.news.utils.rvUtils.EndlessRecyclerViewOnScrollListener

/**
 * Created by Ritesh on 04/09/2019.
 */
class AllNewsActivity : AppCompatActivity(), OnRefreshListener {
    private val TAG = AllNewsActivity::class.java.simpleName
    private var allNewsAdapter: AllNewsAdapter? = null
    private lateinit var viewModelDemo: AllNewsViewModel

    private lateinit var dataBinding: ActivityAllNewsBinding
    private lateinit var endlessRecyclerViewOnScrollListener: EndlessRecyclerViewOnScrollListener
    private var isLoading = false

    private var selectedTopic = ""

    private val dataStoreHelper by lazy {
        DataStoreHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initBindings()
            setupViews()
            initListeners()
            initObservers()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getPrefSelection() {

        dataStoreHelper.getSelectedTopic.asLiveData().observe(this, {
            if (selectedTopic == it) return@observe
            selectedTopic = it
            fetchNews(false)
            dataBinding.fabFilter.text = selectedTopic
        })
    }

    // Initialize ViewModel, DataBinding
    private fun initBindings() {
        viewModelDemo = ViewModelProviders.of(this).get(AllNewsViewModel::class.java)
        // Inflate view and obtain an instance of the binding class.
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_news)
        // Assign the component to a property in the binding class.
        dataBinding.viewModel = viewModelDemo
        // Specify the current activity as the lifecycle owner.
        dataBinding.lifecycleOwner = this
    }

    // Setup RecyclerView
    private fun setupViews() {
        dataBinding.rvNews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dataBinding.rvNews.setHasFixedSize(true)
        dataBinding.rvNews.adapter = AllNewsAdapter(this).also { allNewsAdapter = it }
    }

    // Add SwipeRefresh and RecyclerView's scroll listener
    private fun initListeners() {
        dataBinding.srNews.setOnRefreshListener(this)

        dataBinding.rvNews.addOnScrollListener(object : EndlessRecyclerViewOnScrollListener(dataBinding.rvNews.layoutManager as LinearLayoutManager) {
            override fun onLoadMore() {
                fetchNews(true)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    dataBinding.fabFilter.show()
                else if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING)
                    dataBinding.fabFilter.hide()
            }

        }.also { endlessRecyclerViewOnScrollListener = it })

        dataBinding.fabFilter.setOnClickListener {
            val topicDialogFragment = TopicDialogFragment()
            topicDialogFragment.show(supportFragmentManager, "TopicDialogFragment")
        }

    }

    // Observe the data changes from ViewModel
    private fun initObservers() {
        viewModelDemo.articleLiveDataList.observe(this, { articleDataList -> populateAllNews(articleDataList) })
        viewModelDemo.isLoadingLiveData.observe(this, { isLoading -> revokeLoadingMore(isLoading) })
        viewModelDemo.errorCodeLiveData.observe(this, { integer -> onFailure(integer) })
    }

    // On Scroll load more data
    private fun fetchNews(isLoadMore: Boolean) {
        viewModelDemo.fetchAllNews(selectedTopic, isLoadMore)
        if (isLoadMore) {
            dataBinding.rvNews.post {
                allNewsAdapter!!.list.add(null)
                allNewsAdapter!!.notifyItemInserted(allNewsAdapter!!.list.size - 1)
            }
        } else {
            dataBinding.srNews.isRefreshing = true
            allNewsAdapter?.list?.clear()
            allNewsAdapter?.notifyDataSetChanged()
        }
    }

    // Removed loading views
    private fun revokeLoadingMore(isLoadMore: Boolean) {
        if (dataBinding.srNews.isRefreshing) dataBinding.srNews.isRefreshing = false
        if (isLoadMore) {
            Log.i(TAG, "revokeLoadingMore: ${allNewsAdapter!!.list.size})")
            isLoading = false
            if (!allNewsAdapter?.list.isNullOrEmpty() && allNewsAdapter!!.list.size > 0) {
                allNewsAdapter?.list?.removeAt(allNewsAdapter!!.list.size - 1)
                allNewsAdapter?.notifyItemRemoved(allNewsAdapter!!.list.size)
            }
        }
    }

    // Add articleDataList to adapter
    fun populateAllNews(allNewsList: List<ArticleData?>?) {
        allNewsAdapter?.addAll(allNewsList)
    }

    // Display failure message while performing REST API call
    private fun onFailure(errCode: Int) {
        when (errCode) {
            -1 -> Toast.makeText(this, getString(R.string.err_connecting), Toast.LENGTH_SHORT).show()
            426 -> Toast.makeText(this, getString(R.string.err_too_many_request), Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(this, getString(R.string.err_server), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRefresh() {
        allNewsAdapter!!.reset()
        viewModelDemo.fetchAllNews(selectedTopic, false)
    }

    override fun onResume() {
        super.onResume()
        getPrefSelection()
    }
}