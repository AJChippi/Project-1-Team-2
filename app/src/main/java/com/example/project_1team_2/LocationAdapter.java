package com.example.project_1team_2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * The type Location adapter.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{
    /**
     * The Locations.
     */
    ArrayList<Locations> locations;
    /**
     * The Context.
     */
    Context context;
    /**
     * The Settings pref.
     */
    SharedPreferences settingsPref;
    /**
     * The Settings editor.
     */
    SharedPreferences.Editor settingsEditor;

    /**
     * Instantiates a new Location adapter.
     *
     * @param locations the locations
     * @param context   the context
     */
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

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    /**
     * The type Location view holder.
     */
    class LocationViewHolder extends RecyclerView.ViewHolder{
        /**
         * The Txt city name.
         */
        TextView txtCityName;
        /**
         * The Txt temperature.
         */
        TextView txtTemperature;
        /**
         * The Txt local time.
         */
        TextView txtLocalTime;
        /**
         * The Txt forcast info.
         */
        TextView txtForcastInfo;
        /**
         * The Txt high.
         */
        TextView txtHigh;
        /**
         * The Txt low.
         */
        TextView txtLow;


        /**
         * Instantiates a new Location view holder.
         *
         * @param view the view
         */
        public LocationViewHolder(@NonNull View view) {
            super(view);
            txtCityName = view.findViewById(R.id.txtCityName);
            txtTemperature = view.findViewById(R.id.txtTemperature);
            txtLocalTime = view.findViewById(R.id.txtLocalTime);
            txtForcastInfo = view.findViewById(R.id.txtForcastInfo);
            txtHigh = view.findViewById(R.id.txtHigh);
            txtLow = view.findViewById(R.id.txtLow);
            txtLocalTime = view.findViewById(R.id.txtLocalTime);


            view.setOnClickListener(view1 -> {
                Log.d("twefw","Clicked"+txtCityName.getText());
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("cityName",txtCityName.getText());
                context.startActivity(intent);
            });
        }
    }



}