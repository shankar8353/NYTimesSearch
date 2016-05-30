package com.codepath.nytimessearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 * Created by ssunda1 on 5/28/16.
 */
public class ArticlesAdapter extends ArrayAdapter<Article> {

    static class ViewHolder {
        @BindView(R.id.ivThumbnail) ImageView ivThumbnail;
        @BindView(R.id.tvHeadline) TextView tvHeadline;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public ArticlesAdapter(Context context, List<Article> articles) {
        super(context, R.layout.item_article_result, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article_result, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(article.getThumbnail()).
                transform(new RoundedCornersTransformation(10, 10)).into(viewHolder.ivThumbnail);
        viewHolder.tvHeadline.setText(article.getHeadline());

        return convertView;
    }
}
