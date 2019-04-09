package com.maf.assignment.newsapp.allNews;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maf.assignment.newsapp.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class AllNewsViewHolder extends RecyclerView.ViewHolder {

    ImageView ivBanners;
    TextView tvNewsHeadline;
    CardView cvNews;

    AllNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        ivBanners = itemView.findViewById(R.id.iv_news_banner);
        tvNewsHeadline = itemView.findViewById(R.id.tv_headline);
        cvNews = itemView.findViewById(R.id.cv_news);
    }
}
