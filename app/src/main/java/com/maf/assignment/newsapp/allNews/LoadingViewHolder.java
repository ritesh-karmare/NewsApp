package com.maf.assignment.newsapp.allNews;

import android.view.View;
import android.widget.ProgressBar;

import com.maf.assignment.newsapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class LoadingViewHolder extends RecyclerView.ViewHolder {

    ProgressBar progressBar;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }
}
