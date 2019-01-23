package com.corelib.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corelib.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ErrorAdapter extends RecyclerView.Adapter<ErrorAdapter.MyViewHolder>{

    private final ArrayList<String> list;
    private final LayoutInflater inflater;

    public ErrorAdapter(Context context, ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.slot_error, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvDetail.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        final TextView tvDetail;

        private MyViewHolder(View itemView) {
            super(itemView);
            tvDetail =  itemView.findViewById(R.id.tv_error_text);
        }
    }
}