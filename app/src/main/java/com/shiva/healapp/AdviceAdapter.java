package com.shiva.healapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.AdviceVH> {

    private final Context ctx;
    private final List<AdviceModel> items;

    public AdviceAdapter(Context ctx, List<AdviceModel> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @NonNull
    @Override
    public AdviceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_advice, parent, false);
        return new AdviceVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdviceVH holder, int position) {
        AdviceModel a = items.get(position);
        holder.txtTimestamp.setText(a.getTimestamp() != null ? a.getTimestamp() : "");
        holder.txtAdvice.setText(a.getAdviceText() != null ? a.getAdviceText() : "");
        // Show doctor id — replace with name/join if you want doctor name instead
        holder.txtDoctor.setText("Dr. (ID#" + a.getDoctorId() + ")");
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class AdviceVH extends RecyclerView.ViewHolder {
        final TextView txtTimestamp;
        final TextView txtAdvice;
        final TextView txtDoctor;

        AdviceVH(@NonNull View itemView) {
            super(itemView);
            txtTimestamp = itemView.findViewById(R.id.txtAdviceTimestamp);
            txtAdvice = itemView.findViewById(R.id.txtAdviceText);
            txtDoctor = itemView.findViewById(R.id.txtAdviceDoctor);
        }
    }
}
