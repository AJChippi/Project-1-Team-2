package com.example.project_1team_2.byDayDisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.project_1team_2.R;

import java.util.ArrayList;

public class ByDayAdapter extends BaseAdapter {

    ArrayList<ByDay> byDay;
    Context context;

    public ByDayAdapter(ArrayList<ByDay> byDay, Context context) {
        this.byDay = byDay;
        this.context = context;
    }

    @Override
    public int getCount() {
        return byDay.size();
    }

    @Override
    public Object getItem(int i) {
        return byDay.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null )
            view = LayoutInflater.from(this.context).inflate(R.layout.by_day, viewGroup, false);

        ByDay byDay = (ByDay) getItem(i);

        TextView txtDay = view.findViewById(R.id.txtDay);
        TextView txtProgress = view.findViewById(R.id.txtProgess);
        ProgressBar progressBarPrecipitation = view.findViewById(R.id.progressBarPrecipitation);
        TextView txtPhrase = view.findViewById(R.id.txtPhrase);
        TextView txtHighLow = view.findViewById(R.id.txtHighLow);

        txtDay.setText(byDay.day);
        progressBarPrecipitation.setProgress(byDay.percentPrecipitation);
        txtPhrase.setText(byDay.phase);
        int progress = byDay.percentPrecipitation;
        txtProgress.setText(progress + "%");
        txtHighLow.setText(byDay.high + "°/" + byDay.low + "°");
        return view;
    }
}