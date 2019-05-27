package rk.information.news.newsDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import rk.information.news.R
import rk.information.news.databinding.ActivityNewsDetailBinding
import rk.information.news.network.responseModel.ArticleData

class NewsDetailsActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail)
        val articleData = intent.getSerializableExtra("articleData") as ArticleData
        dataBinding.articleData = articleData

        Glide.with(this)
                .load(dataBinding.articleData?.urlToImage)
                .apply(RequestOptions()
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_background))
                .into(dataBinding.ivNewsBanner)
    }

}
