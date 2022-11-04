package com.example.project_1team_2.byDayDisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1team_2.R;

import java.util.ArrayList;

public class ByDayAdapter extends RecyclerView.Adapter {

    ArrayList<ByDay> byDay;
    Context context;

    public ByDayAdapter(ArrayList<ByDay> byDay, Context context) {
        this.byDay = byDay;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtDay;
        TextView txtProgress;
        ProgressBar progressBarPrecipitation;
        TextView txtPhrase;
        TextView txtHighLow;
        TextView txtPrecipitationIntensity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtProgress = itemView.findViewById(R.id.txtProgress);
            progressBarPrecipitation = itemView.findViewById(R.id.progressBarPrecipitation);
            txtPhrase = itemView.findViewById(R.id.txtPhrase);
            txtHighLow = itemView.findViewById(R.id.txtHighLow);
            txtPrecipitationIntensity = itemView.findViewById(R.id.txtprecipitationIntensity);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.by_day,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ByDay byDayItem = byDay.get(position);
        MyViewHolder itemViewHolder = (ByDayAdapter.MyViewHolder) holder;
        itemViewHolder.txtDay.setText(byDayItem.day);
        itemViewHolder.txtHighLow.setText(byDayItem.low + "°/" + byDayItem.high + "°");
        itemViewHolder.txtPhrase.setText(byDayItem.phrase);
        itemViewHolder.progressBarPrecipitation.setProgress(byDayItem.percentPrecipitation);
        itemViewHolder.txtProgress.setText(byDayItem.percentPrecipitation + "%");
        itemViewHolder.txtPrecipitationIntensity.setText(byDayItem.PrecipitationIntensity);

    }

    @Override
    public int getItemCount() {
        return byDay.size();
    }

}