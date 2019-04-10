package com.maf.assignment.newsapp.newsDetails;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.maf.assignment.newsapp.R;
import com.maf.assignment.newsapp.network.responseModel.ArticleData;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailsActivity extends AppCompatActivity {

    private ArticleData articleData;

    private TextView tv_title, tv_description, tv_content, tv_source_url;
    private ImageView iv_news_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        setupView();
        initData();
    }

    private void setupView() {
        try {
            tv_title = findViewById(R.id.tv_title);
            tv_description = findViewById(R.id.tv_description);
            tv_content = findViewById(R.id.tv_content);
            iv_news_banner = findViewById(R.id.iv_news_banner);
            tv_source_url = findViewById(R.id.tv_source_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        try {
            articleData = (ArticleData) getIntent().getSerializableExtra("articleData");

            tv_title.setText(articleData.getTitle());
            tv_description.setText(articleData.getDescription());
            tv_content.setText(articleData.getContent());
            tv_source_url.setText(getString(R.string.source_url_text,articleData.getUrl()));


            Glide.with(this)
                    .load(articleData.getUrlToImage())
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_launcher_background)
                            .placeholder(R.drawable.ic_launcher_background))
                    .into(iv_news_banner);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
