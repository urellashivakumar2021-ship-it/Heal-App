package com.shiva.healapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserLogAdapter extends RecyclerView.Adapter<UserLogAdapter.LogViewHolder> {

    Context context;
    ArrayList<UserLogModel> list;

    public UserLogAdapter(Context context, ArrayList<UserLogModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_user_log, parent, false);
        return new LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder h, int pos) {
        UserLogModel m = list.get(pos);

        h.txtTimestamp.setText(m.timestamp);
        h.txtSleep.setText("Sleep: " + m.sleep + " hrs");
        h.txtWater.setText("Water: " + m.water + " L");
        h.txtStress.setText("Stress: " + m.stress);
        h.txtDiet.setText("Diet: " + m.diet);
        h.txtActivity.setText("Activity: " + m.activity);
        h.txtMood.setText("Mood: " + m.mood);
        h.txtHeart.setText("Heart Rate: " + m.heart);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {
        TextView txtTimestamp, txtSleep, txtWater, txtStress, txtDiet, txtActivity, txtMood, txtHeart;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTimestamp = itemView.findViewById(R.id.txtTimestamp);
            txtSleep = itemView.findViewById(R.id.txtSleep);
            txtWater = itemView.findViewById(R.id.txtWater);
            txtStress = itemView.findViewById(R.id.txtStress);
            txtDiet = itemView.findViewById(R.id.txtDiet);
            txtActivity = itemView.findViewById(R.id.txtActivity);
            txtMood = itemView.findViewById(R.id.txtMood);
            txtHeart = itemView.findViewById(R.id.txtHeart);
        }
    }
}
