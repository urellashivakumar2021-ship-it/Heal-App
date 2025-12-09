package com.shiva.healapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Context ctx;
    ArrayList<UserModel> list;

    public UserAdapter(Context ctx, ArrayList<UserModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder h, int pos) {
        UserModel m = list.get(pos);

        h.txtUserName.setText(m.name);
        h.txtUserEmail.setText(m.email);

        h.btnDeleteUser.setOnClickListener(v -> {
            DBHelper db = new DBHelper(ctx);
            db.deleteUser(m.id);
            list.remove(pos);
            notifyDataSetChanged();
        });

        h.btnViewUserLogs.setOnClickListener(v -> {
            Intent i = new Intent(ctx, ViewUserLogsActivity.class);
            i.putExtra("user_id", m.id);
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserName, txtUserEmail;
        Button btnDeleteUser, btnViewUserLogs;

        public UserViewHolder(View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtUserEmail = itemView.findViewById(R.id.txtUserEmail);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
            btnViewUserLogs = itemView.findViewById(R.id.btnViewUserLogs);
        }
    }
}
