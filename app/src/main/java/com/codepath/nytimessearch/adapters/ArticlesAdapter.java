package com.codepath.nytimessearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by ssunda1 on 5/30/16.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NO_IMAGE = 1, WITH_IMAGE = 2;

    private List<Article> articles;
    private OnItemClickListener listener;

    public ArticlesAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        RecyclerView.ViewHolder viewHolder;
        if (viewType == NO_IMAGE) {
            View articleView = inflater.inflate(R.layout.item_article_result, parent, false);
            viewHolder = new ViewHolder(articleView, listener);
        }
        else {
            View articleView = inflater.inflate(R.layout.item_article_image_result, parent, false);
            viewHolder = new ViewHolderImage(articleView, listener);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Article article = articles.get(position);
        if (holder.getItemViewType() == NO_IMAGE) {
            ViewHolder holderNoImage = (ViewHolder) holder;
            holderNoImage.tvHeadline.setText(article.getHeadline());
        }
        else {
            ViewHolderImage holderImage = (ViewHolderImage) holder;
            Picasso.with(holderImage.ivThumbnail.getContext()).load(article.getThumbnail()).
                    transform(new RoundedCornersTransformation(10, 10)).into(holderImage.ivThumbnail);
            holderImage.tvHeadline.setText(article.getHeadline());
        }


    }

    @Override
    public int getItemViewType(int position) {
        Article article = articles.get(position);
        return article.getThumbnail() == null ? NO_IMAGE : WITH_IMAGE;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvHeadline) TextView tvHeadline;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }

    }

    public static class ViewHolderImage extends RecyclerView.ViewHolder {

        @BindView(R.id.ivThumbnail) ImageView ivThumbnail;
        @BindView(R.id.tvHeadline) TextView tvHeadline;

        public ViewHolderImage(final View itemView, final OnItemClickListener listener) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }

    }
}
