package com.example.wonder_trip_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<JournalModel> journalsList;

//    public MyAdapter() {
//    }

    public MyAdapter(Context context, ArrayList<JournalModel> journalsList) {
        this.context = context;
        this.journalsList = journalsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_a_tile, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JournalModel journal = journalsList.get(position);
        // In MyAdapter's onBindViewHolder method, bind data to views:

        holder.title.setText(journal.getTitle());
        holder.date.setText(journal.getDate());
        holder.rate.setText(journal.getRate());

// You can also bind other data from your JournalModel object to other views.

    }

    @Override
    public int getItemCount() {
        return journalsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, rate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            title = itemView.findViewById(R.id.titleText2);
//            date = itemView.findViewById(R.id.dateText2);
//            rate = itemView.findViewById(R.id.rateText2);

            title = itemView.findViewById(R.id.titleText);
            date = itemView.findViewById(R.id.dateText);
            rate = itemView.findViewById(R.id.rateText);
        }
    }
}
