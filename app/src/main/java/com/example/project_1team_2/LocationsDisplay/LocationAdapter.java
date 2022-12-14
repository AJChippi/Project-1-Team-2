package com.example.project_1team_2.LocationsDisplay;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1team_2.MainActivity;
import com.example.project_1team_2.R;

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
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Locations l = locations.get(position);
        holder.txtCityName.setText(l.city);
        holder.txtTemperature.setText(l.temperature);
        holder.txtForcastInfo.setText(l.forecastInfo);
        holder.txtHigh.setText("H:"+l.highestTemp+"°");
        holder.txtLow.setText("L:"+l.lowestTemp+"°");
        holder.txtLocalTime.setText(l.localTime);
        holder.txtCountry.setText(l.countryName);

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder{
        TextView txtCityName;
        TextView txtTemperature;
        TextView txtLocalTime;
        TextView txtCountry;
        TextView txtForcastInfo;
        TextView txtHigh;
        TextView txtLow;


        public LocationViewHolder(@NonNull View view) {
            super(view);
            txtCityName = view.findViewById(R.id.txtCityName);
            txtTemperature = view.findViewById(R.id.txtTemperature);
            txtLocalTime = view.findViewById(R.id.txtLocalTime);
            txtForcastInfo = view.findViewById(R.id.txtForcastInfo);
            txtHigh = view.findViewById(R.id.txtHigh);
            txtLow = view.findViewById(R.id.txtLow);
            txtLocalTime = view.findViewById(R.id.txtLocalTime);
            txtCountry = view.findViewById(R.id.txtCountry);


            view.setOnClickListener(view1 -> {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("cityName",txtCityName.getText());
                context.startActivity(intent);
            });
        }
    }



}