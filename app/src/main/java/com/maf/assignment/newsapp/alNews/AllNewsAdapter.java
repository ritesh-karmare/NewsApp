package com.maf.assignment.newsapp.alNews;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maf.assignment.newsapp.R;
import com.maf.assignment.newsapp.network.responseModel.ArticleData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Ritesh on 04/09/2019.
 */

class AllNewsAdapter extends RecyclerView.Adapter<AllNewsViewHolder> {

    private List<ArticleData> allNewsList;
    private Context context;

    public AllNewsAdapter(Context context) {
        this.context = context;
        allNewsList = new ArrayList<>();
    }


    @NonNull
    @Override
    public AllNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new AllNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNewsViewHolder holder, final int position) {

        final ArticleData newsArticle = allNewsList.get(position);

        holder.tvNewsHeadline.setText(newsArticle.getTitle());

        Glide.with(context)
                .load(newsArticle.getUrlToImage())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_background))
                .into(holder.ivBanners);

        holder.cvNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToNewsPage(allNewsList.get(position).getUrl());
            }
        });

    }

    private void redirectToNewsPage(String newsUrl) {
        try {

            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .addDefaultShareMenuItem()
                    .setToolbarColor(context.getResources()
                            .getColor(R.color.colorPrimary))
                    .setShowTitle(true)
                    .build();

            customTabsIntent.launchUrl(context, Uri.parse(newsUrl));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return allNewsList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    void addAll(List<ArticleData> allNewsList) {
        this.allNewsList.addAll(allNewsList);
    }

    void reset() {
        this.allNewsList.clear();
    }
}
