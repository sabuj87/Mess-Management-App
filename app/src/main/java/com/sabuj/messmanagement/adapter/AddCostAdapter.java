package com.sabuj.messmanagement.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddCostAdapter extends RecyclerView.Adapter<AddCostAdapter.AddCostViewHolder> {
    @NonNull
    @Override
    public AddCostAdapter.AddCostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AddCostAdapter.AddCostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AddCostViewHolder extends RecyclerView.ViewHolder {
        public AddCostViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
