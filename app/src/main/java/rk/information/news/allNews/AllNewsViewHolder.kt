package rk.information.news.allNews

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import rk.information.news.R

internal class AllNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ivBanners: ImageView
    var tvNewsTitle: TextView
    var tvNewsDesc: TextView
    var tvNewsTime: TextView
    var cvNews: CardView

    init {
        ivBanners = itemView.findViewById(R.id.iv_news_banner)
        cvNews = itemView.findViewById(R.id.cv_news)
        tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle)
        tvNewsDesc = itemView.findViewById(R.id.tvNewsDesc)
        tvNewsTime = itemView.findViewById(R.id.tvNewsTime)
    }
}