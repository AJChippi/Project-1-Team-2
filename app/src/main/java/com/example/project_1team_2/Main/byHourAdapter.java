package com.example.project_1team_2.Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_1team_2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class byHourAdapter extends BaseAdapter {
    ArrayList<byHour> byHourForeCast;
    Context context;

    public byHourAdapter(ArrayList<byHour> byHourForeCast,Context context) {
        this.byHourForeCast = byHourForeCast;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return byHourForeCast.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       if(view != null)
           view = LayoutInflater.from(context).inflate(R.layout.by_hour,viewGroup,false);

        TextView txtTime = view.findViewById(R.id.txtTime);
        TextView txtTemp = view.findViewById(R.id.txtTemp);
        ImageView ivWeather = view.findViewById(R.id.ivIcon);

        byHour byHourItem = (byHour) getItem(i);

        txtTime.setText(byHourItem.getTime());
        txtTemp.setText(byHourItem.getTemp()+ "°");

        String iconURL = "https://developer.accuweather.com/sites/default/files/"+byHourItem.getWeatherIcon()+"-s.png";
        Picasso.get().load(iconURL).into(ivWeather);
        return view;
    }
}
