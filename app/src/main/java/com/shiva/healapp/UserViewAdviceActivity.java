package com.shiva.healapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserViewAdviceActivity extends AppCompatActivity {

    private RecyclerView rvAdvice;
    private DBHelper db;
    private ArrayList<AdviceModel> adviceList;
    private AdviceAdapter adapter;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_advice);

        rvAdvice = findViewById(R.id.rvAdvice);
        db = new DBHelper(this);

        // read logged-in user id from shared prefs
        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        userId = sp.getInt("user_id", -1);

        adviceList = new ArrayList<>();
        adapter = new AdviceAdapter(this, adviceList);
        rvAdvice.setLayoutManager(new LinearLayoutManager(this));
        rvAdvice.setAdapter(adapter);

        loadAdvice();
    }

    private void loadAdvice() {
        adviceList.clear();
        Cursor c = null;
        try {
            c = db.getAdviceForUser(userId); // uses DBHelper.getAdviceForUser(userId)
            if (c != null && c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndexOrThrow("id"));
                    int uid = c.getInt(c.getColumnIndexOrThrow("user_id"));
                    int did = c.getInt(c.getColumnIndexOrThrow("doctor_id"));
                    String text = c.getString(c.getColumnIndexOrThrow("advice_text"));
                    String ts = c.getString(c.getColumnIndexOrThrow("timestamp"));

                    adviceList.add(new AdviceModel(id, uid, did, text, ts));
                } while (c.moveToNext());
            }
        } finally {
            if (c != null) c.close();
        }
        adapter.notifyDataSetChanged();
    }
}
