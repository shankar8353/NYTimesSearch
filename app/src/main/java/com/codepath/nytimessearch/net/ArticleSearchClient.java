package com.codepath.nytimessearch.net;

import android.util.Log;

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
import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ssunda1 on 5/28/16.
 */
public class ArticleSearchClient {

    private static final String BASE_URL = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String IMAGE_URL = "http://www.nytimes.com/";
    private static final String API_KEY = "4140e249d9a048b19b951a7aca2fc42a";

    private AsyncHttpClient client;
    private SearchListener listener;
    private String query;
    private Settings settings;

    public ArticleSearchClient(SearchListener listener) {
        this.listener = listener;
        client = new AsyncHttpClient();
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void settings(Settings settings) {
        this.settings = settings;
    }

    public void fetchPage(int page) {
        // check if online before making a network request
        if (!NetworkUtils.isOnline()) {
            listener.noNetwork();
            return;
        }

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

        listener.startSearch();

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
                        int selection = new Random().nextInt(doc.multimedia.size());
                        String url = doc.multimedia.get(selection).url;
                        if (url != null) {
                            article.setThumbnail(IMAGE_URL + url);
                        }
                    }
                    articles.add(article);
                }
                listener.endSearch();
                listener.searchResults(articles);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("SEARCH", "Article search failed with error: " + responseString);
                listener.endSearch();
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

    public interface SearchListener {
        void noNetwork();

        void startSearch();

        void endSearch();

        void searchResults(List<Article> articles);
    }
}
