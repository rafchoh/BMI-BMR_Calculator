package com.example.bbcalculator.bmr.recyclerviewbmr;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbcalculator.R;
import com.example.bbcalculator.bmr.UDbmr;
import com.example.bbcalculator.databinding.SingleItembmrBinding;

import java.util.ArrayList;

public class RecyclerBMR extends RecyclerView.Adapter<RecyclerBMR.ViewHolder> {
    SQLiteDatabase db;
    Context context;
    ArrayList<BMRData> rData = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public BMRData bmrData;
        SingleItembmrBinding b;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b = SingleItembmrBinding.bind(itemView);
        }

        public void submitItem(BMRData bmrData) {
            this.bmrData = bmrData;

            b.rowId1.setText(String.valueOf(bmrData.getId()));
            b.rowName1.setText(bmrData.getName());
            b.rowSex1.setText(String.valueOf(bmrData.getSex()));
            b.rowAge1.setText(String.valueOf(bmrData.getAge()));
            b.rowHeight1.setText(String.valueOf(bmrData.getHeight()));
            b.rowWeight1.setText(String.valueOf(bmrData.getWeight()));
            b.rowBMR1.setText(bmrData.getBMR());
        }

    }

    public RecyclerBMR(Context context, int single_itembmr, ArrayList<BMRData> rData, SQLiteDatabase db) {
        this.context = context;
        this.rData = rData;
        this.db = db;
    }

    @NonNull
    @Override
    public RecyclerBMR.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_itembmr, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBMR.ViewHolder viewHolder, int position) {
        final BMRData bmrData = rData.get(position);
        viewHolder.submitItem(bmrData);

        viewHolder.b.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, UDbmr.class);
            Bundle bundle1 = new Bundle();

            bundle1.putSerializable("bmrdata", bmrData);
            intent.putExtras(bundle1);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rData.size();
    }
}
