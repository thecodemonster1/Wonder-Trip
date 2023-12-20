package com.example.wonder_trip_project;

import static com.example.wonder_trip_project.Utils.showLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<JournalModel> journalsList;
    JournalModel journal;
    String userId;
    OnJournalClickListener listener;

    public void removeItem(int position) {
        journalsList.remove(position); // Remove from data list
        notifyItemRemoved(position); // Notify RecyclerView
    }


    public interface OnJournalClickListener {
        void onJournalClicked(String userId, String journalId);
    }


    public MyAdapter(Context context, ArrayList<JournalModel> journalsList, OnJournalClickListener listener, String userId) {
        this.context = context;
        this.journalsList = journalsList;
        this.listener = listener;
        this.userId = userId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_a_tile, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JournalModel journal = journalsList.get(position);
        // In MyAdapter's onBindViewHolder method, bind data to views:
        holder.title.setText(journal.getTitle());
        holder.date.setText(journal.getDate());
        holder.rate.setText(journal.getRate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            private String userId;

            @Override
            public void onClick(View view) {// Assuming you extract it earlier in ViewActivity
//                userId = this.userId;
                String journalId = journal.getJournalId(); // Ensure JournalModel has getter for journalId

                if (listener != null) { // Check if listener is set
                    listener.onJournalClicked(userId, journalId);
                    showLog("journalId: " + journalId);
                }
            }
        });

        String imgUri = journal.getSrcImageJournal();
        if (imgUri != null) {
            Glide.with(context)
                    .load(imgUri) // Load image from Firebase Storage
                    .into(holder.imageUri);
        } else {
            // Handle the case where there's no image
            holder.imageUri.setImageDrawable(null);
        }

// You can also bind other data from your JournalModel object to other views.

    }

    @Override
    public int getItemCount() {
        return journalsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, rate;
        ImageView imageUri;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            title = itemView.findViewById(R.id.titleText2);
//            date = itemView.findViewById(R.id.dateText2);
//            rate = itemView.findViewById(R.id.rateText2);

            title = itemView.findViewById(R.id.titleText);
            date = itemView.findViewById(R.id.dateText);
            rate = itemView.findViewById(R.id.rateText);
            imageUri = itemView.findViewById(R.id.imageView_of_a_tile);
        }
    }
}
