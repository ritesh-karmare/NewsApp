package rk.information.news.allNews

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import rk.information.news.R
import rk.information.news.network.responseModel.ArticleData
import rk.information.news.newsDetails.NewsDetailsActivity
import rk.information.news.utils.Utility.openChromeCustomTab
import rk.information.news.utils.dateUtils.DateUtils.timeAgo
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Ritesh on 04/09/2019.
 */
internal class AllNewsAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val allNewsList: ArrayList<ArticleData?> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
            AllNewsViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is AllNewsViewHolder) {
            populateItemRows(viewHolder, position)
        }
    }

    override fun getItemCount(): Int {
        return allNewsList.size
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return if (allNewsList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    // Populate item with news headline and image
    private fun populateItemRows(holder: AllNewsViewHolder, position: Int) {
        try {
            val newsArticle = allNewsList[position]
            holder.tvNewsTitle.text = newsArticle!!.title
            holder.tvNewsDesc.text = newsArticle.description
            holder.tvNewsTime.text = timeAgo(newsArticle.publishedAt)
            Glide.with(context)
                    .load(newsArticle.urlToImage)
                    .apply(RequestOptions()
                            .error(R.drawable.ic_launcher_background)
                            .placeholder(R.drawable.ic_launcher_background))
                    .into(holder.ivBanners)
            holder.cvNews.setOnClickListener { v: View? ->
                val url = allNewsList[position]!!.url
                if (url != null && !url.isEmpty() && URLUtil.isValidUrl(url)) openChromeCustomTab(context, url) else {
                    Toast.makeText(context, "Invalid Url", Toast.LENGTH_LONG).show()
                    redirectToNewsPage(holder.ivBanners, allNewsList[position])
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Starting news details activity with shared element transition
    private fun redirectToNewsPage(ivBanner: ImageView, articleData: ArticleData?) {
        try {
            val newsDetailsIntent = Intent(context, NewsDetailsActivity::class.java)
            newsDetailsIntent.putExtra("articleData", articleData)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation((context as Activity),
                    ivBanner, context.getResources().getString(R.string.list_to_detail))
            ActivityCompat.startActivity(context, newsDetailsIntent, options.toBundle())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
    Helper methods
     */
    fun addAll(allNewsList: List<ArticleData?>?) {
        this.allNewsList.clear()
        this.allNewsList.addAll(allNewsList!!)
        notifyDataSetChanged()
    }

    fun reset() {
        allNewsList.clear()
    }

    val list: ArrayList<ArticleData?>
        get() = allNewsList

}