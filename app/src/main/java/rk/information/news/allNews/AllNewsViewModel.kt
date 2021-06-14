package rk.information.news.allNews

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rk.information.news.network.APIImpl
import rk.information.news.network.ApiClient
import rk.information.news.network.responseModel.AllNewsRes
import rk.information.news.network.responseModel.ArticleData
import java.text.SimpleDateFormat
import java.util.*

class AllNewsViewModel : ViewModel() {
    private var pageNum = 1
    var articleLiveDataList = MutableLiveData<List<ArticleData>?>()
    var isLoadingLiveData = MutableLiveData<Boolean>()
    var errorCodeLiveData = MutableLiveData<Int>()
    private val date5DaysAgo: String
        private get() {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -5)
            val s = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            Log.d("DATE", "Date before 5 days: " + s.format(Date(cal.timeInMillis)))
            return s.format(Date(cal.timeInMillis))
        }

    fun fetchAllNews(selectedTopic: String?, isLoadMore: Boolean) {
        try {
            if (!isLoadMore) pageNum = 1
            val apiImpl = ApiClient.client.create(APIImpl::class.java)
            val call = apiImpl.getAllNews(selectedTopic, date5DaysAgo)
            call.enqueue(object : Callback<AllNewsRes?> {
                override fun onResponse(call: Call<AllNewsRes?>, response: Response<AllNewsRes?>) {
                    try {
                        isLoadingLiveData.postValue(isLoadMore)
                        if (response.isSuccessful) {
                            val newsData = response.body()
                            if (newsData?.articles != null && newsData.articles!!.isNotEmpty()) {
                                articleLiveDataList.postValue(newsData.articles)
                                managePageNum(true)
                            }
                        } else errorCodeLiveData.postValue(426)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        managePageNum(false)
                    }
                }

                override fun onFailure(call: Call<AllNewsRes?>, t: Throwable) {
                    t.printStackTrace()
                    managePageNum(false)
                    errorCodeLiveData.value = -1
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            managePageNum(false)
        }
    }

    // Increment the pageNum if req was successful, else decrement
    private fun managePageNum(isIncrement: Boolean) {
        if (isIncrement) pageNum++ else {
            if (pageNum > 1) pageNum--
        }
    }
}