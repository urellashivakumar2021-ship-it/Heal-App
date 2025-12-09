package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class InsightsActivity extends AppCompatActivity {

    LineChart chartSleep, chartWater, chartHeart;
    DBHelper db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights);

        db = new DBHelper(this);

        // Get user ID
        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sp.getInt("user_id", -1);

        // Link views
        chartSleep = findViewById(R.id.chartSleep);
        chartWater = findViewById(R.id.chartWater);
        chartHeart = findViewById(R.id.chartHeart);

        loadCharts();
    }

    private void loadCharts() {

        ArrayList<Entry> sleepData = new ArrayList<>();
        ArrayList<Entry> waterData = new ArrayList<>();
        ArrayList<Entry> heartData = new ArrayList<>();

        Cursor c = db.getUserLogs(userId);
        int index = 0;

        while (c.moveToNext()) {
            int sleep = c.getInt(c.getColumnIndexOrThrow("sleep"));
            double water = c.getDouble(c.getColumnIndexOrThrow("water"));
            int heart = c.getInt(c.getColumnIndexOrThrow("heartrate"));

            sleepData.add(new Entry(index, sleep));
            waterData.add(new Entry(index, (float) water));
            heartData.add(new Entry(index, heart));
            index++;
        }

        c.close();

        // Sleep chart
        LineDataSet sleepSet = new LineDataSet(sleepData, "Sleep (hrs)");
        sleepSet.setLineWidth(2f);
        chartSleep.setData(new LineData(sleepSet));
        Description d1 = new Description();
        d1.setText("");
        chartSleep.setDescription(d1);
        chartSleep.invalidate();


        // Water chart
        LineDataSet waterSet = new LineDataSet(waterData, "Water (L)");
        waterSet.setLineWidth(2f);
        chartWater.setData(new LineData(waterSet));
        Description d2 = new Description();
        d2.setText("");
        chartWater.setDescription(d2);
        chartWater.invalidate();


        // Heart chart
        LineDataSet heartSet = new LineDataSet(heartData, "Heart Rate (BPM)");
        heartSet.setLineWidth(2f);
        chartHeart.setData(new LineData(heartSet));
        Description d3 = new Description();
        d3.setText("");
        chartHeart.setDescription(d3);
        chartHeart.invalidate();
    }
}
