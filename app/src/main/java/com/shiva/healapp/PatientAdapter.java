package com.shiva.healapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    Context context;
    ArrayList<PatientModel> list;

    public PatientAdapter(Context context, ArrayList<PatientModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder h, int pos) {
        PatientModel m = list.get(pos);

        h.name.setText(m.name);
        h.email.setText(m.email);
        h.lastLog.setText("Last Log: " + (m.lastLog == null ? "--" : m.lastLog));

        h.btnViewLogs.setOnClickListener(v -> {
            Intent it = new Intent(context, ViewPatientLogsActivity.class);
            it.putExtra("user_id", m.id);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, lastLog;
        Button btnViewLogs;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtPatientName);
            email = itemView.findViewById(R.id.txtPatientEmail);
            lastLog = itemView.findViewById(R.id.txtPatientLastLog);
            btnViewLogs = itemView.findViewById(R.id.btnViewLogs);
        }
    }
}
