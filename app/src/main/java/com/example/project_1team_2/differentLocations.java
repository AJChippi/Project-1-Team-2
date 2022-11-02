package com.example.project_1team_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;

public class differentLocations extends AppCompatActivity {
    RecyclerView locationList;
    ArrayList<Locations> locationsArrayList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_locations);
        locationsArrayList = new ArrayList<>();
        locationList = findViewById(R.id.locationList);
        searchView = findViewById(R.id.searchView2);



        //Adding dummy data to arraylist
        locationsArrayList.add(new Locations("Saginaw", "15:15","Sunny",78.5,69.8,75));
        locationsArrayList.add(new Locations("Troy", "24:15","Cloudy",71.5,79.8,65));
        locationsArrayList.add(new Locations("Detroit", "15:15","Mostly Clear",78.5,69.8,85));
        locationsArrayList.add(new Locations("New York", "15:15","Mostly Clear",78.5,69.8,60));
        locationsArrayList.add(new Locations("Tampa", "15:15","Mostly Clear",78.5,69.8,95));
        locationsArrayList.add(new Locations("Miami", "15:15","Mostly Clear",78.5,69.8,95));
        locationsArrayList.add(new Locations("Dhaka", "15:15","Mostly Clear",78.5,69.8,95));


        LocationAdapter adapter = new LocationAdapter(locationsArrayList,this);
        locationList.setAdapter(adapter);
        locationList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();

        /**
         * Swipe to remove item from location list
         */
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                locationsArrayList.remove(viewHolder.getLayoutPosition());
                adapter.notifyDataSetChanged();

            }
        });
        helper.attachToRecyclerView(locationList);



    }
}