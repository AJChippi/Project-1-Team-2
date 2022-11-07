package com.example.project_1team_2.byHourDisplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1team_2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The type By hour adapter.
 */
public class byHourAdapter extends RecyclerView.Adapter {
    /**
     * The By hour fore cast.
     */
    ArrayList<byHour> byHourForeCast;
    /**
     * The Context.
     */
    Context context;

    /**
     * Instantiates a new By hour adapter.
     *
     * @param byHourForeCast the by hour fore cast
     * @param context        the context
     */
    public byHourAdapter(ArrayList<byHour> byHourForeCast,Context context) {
        this.byHourForeCast = byHourForeCast;
        this.context = context;
    }

    /**
     * The type My view holder.
     */
    class MyViewHolder extends RecyclerView.ViewHolder{
        /**
         * The Txt time.
         */
        TextView txtTime;
        /**
         * The Txt temp.
         */
        TextView txtTemp;
        /**
         * The Iv weather.
         */
        ImageView ivWeather;

        /**
         * Instantiates a new My view holder.
         *
         * @param itemView the item view
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtTemp = itemView.findViewById(R.id.txtTemp);
            ivWeather = itemView.findViewById(R.id.ivIcon);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.byhour_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       byHour byHourItem = byHourForeCast.get(position);
       MyViewHolder itemViewHolder = (MyViewHolder) holder;
        itemViewHolder.txtTime.setText(byHourItem.getTime());
        itemViewHolder.txtTemp.setText(byHourItem.getTemp()+ "°");

        String iconURL = "https://developer.accuweather.com/sites/default/files/"+byHourItem.getWeatherIcon()+"-s.png";
        Picasso.get().load(iconURL).into(itemViewHolder.ivWeather);
    }


    @Override
    public int getItemCount() {
       return byHourForeCast.size();
    }

}