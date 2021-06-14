package rk.information.news.allNews

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import rk.information.news.R

internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

}