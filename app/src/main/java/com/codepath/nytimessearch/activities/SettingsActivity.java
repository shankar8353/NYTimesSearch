package com.codepath.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.models.NewsDesk;
import com.codepath.nytimessearch.models.Settings;
import com.codepath.nytimessearch.models.SortOrder;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.etBeginDate) EditText etBeginDate;
    @BindView(R.id.etEndDate) EditText etEndDate;
    @BindView(R.id.spSortOrder) Spinner spSortOrder;
    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbBusiness) CheckBox cbBusiness;
    @BindView(R.id.cbFashionStyle) CheckBox cbFashionStyle;
    @BindView(R.id.cbSports) CheckBox cbSports;
    @BindView(R.id.cbTechnology) CheckBox cbTechnology;

    private ArrayAdapter<SortOrder> sortOrderAdapter;
    private Settings settings;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        settings = Parcels.unwrap(getIntent().getParcelableExtra("settings"));

        etBeginDate.setText(format(settings.getBeginDate()));
        etEndDate.setText(format(settings.getEndDate()));

        sortOrderAdapter = new ArrayAdapter<SortOrder>(this, android.R.layout.simple_spinner_item, SortOrder.values());
        spSortOrder.setAdapter(sortOrderAdapter);
        spSortOrder.setSelection(sortOrderAdapter.getPosition(settings.getSortOrder()));

        cbArts.setChecked(settings.isEnabled(NewsDesk.ARTS));
        cbBusiness.setChecked(settings.isEnabled(NewsDesk.BUSINESS));
        cbFashionStyle.setChecked(settings.isEnabled(NewsDesk.FASHION_STYLE));
        cbSports.setChecked(settings.isEnabled(NewsDesk.SPORTS));
        cbTechnology.setChecked(settings.isEnabled(NewsDesk.TECHNOLOGY));
    }

    public void onSave(View view) {
        Intent data = new Intent();
        settings.setBeginDate(parse(etBeginDate.getText().toString()));
        settings.setEndDate(parse(etEndDate.getText().toString()));
        settings.setSortOrder(sortOrderAdapter.getItem(spSortOrder.getSelectedItemPosition()));
        settings.enable(NewsDesk.ARTS, cbArts.isChecked());
        settings.enable(NewsDesk.BUSINESS, cbBusiness.isChecked());
        settings.enable(NewsDesk.FASHION_STYLE, cbFashionStyle.isChecked());
        settings.enable(NewsDesk.SPORTS, cbSports.isChecked());
        settings.enable(NewsDesk.TECHNOLOGY, cbTechnology.isChecked());
        data.putExtra("settings", Parcels.wrap(settings));
        setResult(RESULT_OK, data);
        finish();
    }

    public String format(Date date) {
        return date == null ? null : dateFormat.format(date);
    }

    public Date parse(String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }

        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            Log.e("SettingsActivity", "Invalid date");
        }
        return null;
    }
}
