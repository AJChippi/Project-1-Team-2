package com.example.project_1team_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{
    ArrayList<Locations> locations;
    Context context;

    public LocationAdapter(ArrayList<Locations> locations,Context context) {
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_layout_locations,parent,false);
        view.setClipToOutline(true);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Locations l = locations.get(position);
        holder.txtCityName.setText(l.city);
        holder.txtTemperature.setText(l.temperature+"°");
        holder.txtForcastInfo.setText(l.forecastInfo);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder{
        TextView txtCityName;
        TextView txtTemperature;
        TextView txtLocalTime;
        TextView txtForcastInfo;
        TextView txtTempHL;

        public LocationViewHolder(@NonNull View view) {
            super(view);
            txtCityName = view.findViewById(R.id.txtCityName);
            txtTemperature = view.findViewById(R.id.txtTemperature);
            txtLocalTime = view.findViewById(R.id.txtLocalTime);
            txtForcastInfo = view.findViewById(R.id.txtForcastInfo);
            txtTempHL = view.findViewById(R.id.txtTempHL);
        }
    }



}