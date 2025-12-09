package com.shiva.healapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class ViewUsersActivity extends AppCompatActivity {

    RecyclerView rvUsers;
    ArrayList<UserModel> list = new ArrayList<>();
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        rvUsers = findViewById(R.id.rvUsers);
        db = new DBHelper(this);

        loadUsers();

        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(new UserAdapter(this, list));
    }

    private void loadUsers() {
        list.clear();
        Cursor c = db.getAllUsers();

        while (c.moveToNext()) {
            list.add(new UserModel(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("name")),
                    c.getString(c.getColumnIndexOrThrow("email"))
            ));
        }
    }
}
