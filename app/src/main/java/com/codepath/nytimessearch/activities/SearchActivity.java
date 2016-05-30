package com.codepath.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

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

    @BindView(R.id.gvResults) GridView gvResults;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private List<Article> articles;
    private ArticleSearchClient client;
    private ArticlesAdapter articlesAdapter;
    private Settings settings;
    private MenuItem miActionProgressItem;

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                articles.clear();
                articlesAdapter.notifyDataSetChanged();
                client.fetchPage(query, settings, 0);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);

        return super.onCreateOptionsMenu(menu);
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

    public void addArticles(List<Article> articles) {
        this.articles.addAll(articles);
        articlesAdapter.notifyDataSetChanged();
    }

    public void showProgressBar() {
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        miActionProgressItem.setVisible(false);
    }
}
