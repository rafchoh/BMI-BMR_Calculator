package com.example.bbcalculator.bmi.recyclerviewbmi;

import android.annotation.SuppressLint;
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
import com.example.bbcalculator.bmi.UDbmi;
import com.example.bbcalculator.databinding.SingleItembmiBinding;

import java.util.ArrayList;

public class RecyclerBMI extends RecyclerView.Adapter<RecyclerBMI.ViewHolder> {
    SQLiteDatabase db;
    Context context;
    ArrayList<BMIData> iData = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public BMIData bmiData;
        SingleItembmiBinding b;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            b = SingleItembmiBinding.bind(itemView);
        }

        public void submitItem(BMIData bmiData) {
            this.bmiData = bmiData;

            b.rowId.setText(String.valueOf(bmiData.getId()));
            b.rowName.setText(bmiData.getName());
            b.rowHeight.setText(String.valueOf(bmiData.getHeight()));
            b.rowWeight.setText(String.valueOf(bmiData.getWeight()));
            b.rowBMI.setText(bmiData.getBMI());
        }
    }

    public RecyclerBMI(Context context, int single_itembmi, ArrayList<BMIData> iData, SQLiteDatabase db) {
        this.context = context;
        this.iData = iData;
        this.db = db;
    }

    @NonNull
    @Override
    public RecyclerBMI.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_itembmi, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBMI.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        final BMIData bmiData = iData.get(position);
        viewHolder.submitItem(bmiData);

        viewHolder.b.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(context, UDbmi.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable("bmidata", bmiData);
            intent.putExtras(bundle);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return iData.size();
    }
}