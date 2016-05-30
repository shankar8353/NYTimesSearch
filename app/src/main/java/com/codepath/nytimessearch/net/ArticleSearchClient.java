package com.codepath.nytimessearch.net;

import android.util.Log;

import com.codepath.nytimessearch.activities.SearchActivity;
import com.codepath.nytimessearch.models.Article;
import com.codepath.nytimessearch.models.NewsDesk;
import com.codepath.nytimessearch.models.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ssunda1 on 5/28/16.
 */
public class ArticleSearchClient {

    private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String IMAGE_URL = "http://www.nytimes.com/";
    private static final String API_KEY = "4140e249d9a048b19b951a7aca2fc42a";

    private AsyncHttpClient client;
    private SearchActivity activity;

    public ArticleSearchClient(SearchActivity activity) {
        this.activity = activity;
        client = new AsyncHttpClient();
    }

    public void fetchPage(String query, Settings settings, int page) {
        RequestParams params = new RequestParams();
        params.put("api-key", API_KEY);
        params.put("q", query);
        params.put("page", page);

        if (settings != null) {
            setDateParams(params, "begin_date", settings.getBeginDate());
            setDateParams(params, "end_date", settings.getEndDate());
            setNewsDeskParams(params, settings.getEnabledNewsDesks());
            params.put("sort", settings.getSortOrder().getOrder());
        }

        activity.showProgressBar();

        client.get(BASE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Gson gson = new GsonBuilder().create();
                ResponseContainer rc = gson.fromJson(response, ResponseContainer.class);
                List<Article> articles = new ArrayList<>();
                for (Doc doc : rc.response.docs) {
                    Article article = new Article();
                    article.setHeadline(doc.headline.main);
                    article.setUrl(doc.webUrl);
                    if (!doc.multimedia.isEmpty()) {
                        String url = doc.multimedia.get(0).url;
                        if (url != null) {
                            article.setThumbnail(IMAGE_URL + url);
                        }
                    }
                    articles.add(article);
                }
                activity.hideProgressBar();
                activity.addArticles(articles);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ArticleSearch", "search failed with error: " + responseString);
                activity.hideProgressBar();
            }

        });
    }

    private void setNewsDeskParams(RequestParams params, List<NewsDesk> newsDesks) {
        if (!newsDesks.isEmpty()) {
            StringBuilder s = new StringBuilder("news_desk:(");
            for (NewsDesk n : newsDesks) {
                s.append(" \"").append(n.toString()).append("\"");
            }
            s.append(" )");
            params.put("fq", s.toString());
        }
    }

    private void setDateParams(RequestParams params, String key, Date value) {
        if (value != null) {
            params.put(key, format(value));
        }
    }

    private String format(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date);
    }
}
