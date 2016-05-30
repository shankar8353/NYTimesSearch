package com.codepath.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.adapters.ArticlesAdapter;
import com.codepath.nytimessearch.models.Article;
import com.codepath.nytimessearch.models.Settings;
import com.codepath.nytimessearch.net.ArticleSearchClient;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SETTINGS = 1;

    @BindView(R.id.etQuery) EditText etQuery;
    @BindView(R.id.btnSearch) Button btnSearch;
    @BindView(R.id.gvResults) GridView gvResults;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private List<Article> articles;
    private ArticleSearchClient client;
    private ArticlesAdapter articlesAdapter;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        settings = new Settings();
        articles = new ArrayList<>();
        client = new ArticleSearchClient(this);
        articlesAdapter = new ArticlesAdapter(this, articles);
        gvResults.setAdapter(articlesAdapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ArticleActivity.class);
                i.putExtra("url", articles.get(position).getUrl());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("settings", Parcels.wrap(settings));
        startActivityForResult(i, REQUEST_CODE_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SETTINGS) {
            if (resultCode == RESULT_OK) {
                settings = (Settings) Parcels.unwrap(data.getParcelableExtra("settings"));
                Log.d("SearchActivity", settings.toString());
            }
        }
    }

    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();
        articles.clear();
        articlesAdapter.notifyDataSetChanged();
        client.fetchPage(query, settings, 0);
    }

    public void addArticles(List<Article> articles) {
        this.articles.addAll(articles);
        articlesAdapter.notifyDataSetChanged();
    }
}
