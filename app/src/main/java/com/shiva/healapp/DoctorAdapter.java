package com.shiva.healapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DocVH> {

    private final Context ctx;
    private final List<DoctorModel> list;

    public DoctorAdapter(Context ctx, List<DoctorModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public DocVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_doctor, parent, false);
        return new DocVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DocVH h, int pos) {
        DoctorModel d = list.get(pos);

        h.txtName.setText(d.getName());
        h.txtSpec.setText(d.getSpecialization());
        h.txtEmail.setText(d.getEmail());

        h.btnDelete.setOnClickListener(v ->
                new AlertDialog.Builder(ctx)
                        .setTitle("Delete Doctor")
                        .setMessage("Are you sure you want to delete " + d.getName() + "?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            DBHelper db = new DBHelper(ctx);
                            db.deleteDoctor(d.getId());
                            list.remove(pos);
                            notifyItemRemoved(pos);
                        })
                        .setNegativeButton("Cancel", null)
                        .show()
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class DocVH extends RecyclerView.ViewHolder {
        TextView txtName, txtSpec, txtEmail;
        Button btnDelete;

        public DocVH(@NonNull View v) {
            super(v);
            txtName = v.findViewById(R.id.txtDocName);
            txtSpec = v.findViewById(R.id.txtDocSpec);
            txtEmail = v.findViewById(R.id.txtDocEmail);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}
