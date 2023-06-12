package com.example.railwayreservationproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.railwayreservationproject.database.DatabaseManager;
import com.example.railwayreservationproject.database.ScheduleListAdapter;
import com.example.railwayreservationproject.database.railwayDbHelper;
import com.example.railwayreservationproject.database.railwayDbSchema;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<TrainSchedule> mScheduleList;

    private railwayDbHelper dbHelper;
    private Button sReturnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Initialize your dbHelper and db objects here
        dbHelper = new railwayDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                railwayDbSchema.TrainTable.NAME + "." + railwayDbSchema.TrainTable.Cols.TRAIN_ID + " AS train_id",
                railwayDbSchema.TrainTable.Cols.ORIGIN,
                railwayDbSchema.TrainTable.Cols.DESTINATION,
                railwayDbSchema.TrainTable.Cols.ECONOMY_PRICE,
                railwayDbSchema.TrainTable.Cols.BUSINESS_PRICE,
                railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID,
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE,
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_TIME,
                railwayDbSchema.ScheduleTable.Cols.ARRIVAL_DATE,
                railwayDbSchema.ScheduleTable.Cols.ARRIVAL_TIME,
                railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY,
                railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY,
                railwayDbSchema.ScheduleTable.Cols.DURATION
        };

        // Get the search criteria, selection, and selectionArgs from the intent extras
        String selection = getIntent().getStringExtra("selection");
        String[] selectionArgs = getIntent().getStringArrayExtra("selectionArgs");

        // Query the database to get the schedules that match the search criteria
        db = new DatabaseManager(this).getDatabase();

        String rawQuery = "SELECT " +
                railwayDbSchema.TrainTable.NAME + "." + railwayDbSchema.TrainTable.Cols.TRAIN_ID + " AS train_id, " +
                railwayDbSchema.TrainTable.Cols.ORIGIN + ", " +
                railwayDbSchema.TrainTable.Cols.DESTINATION + ", " +
                railwayDbSchema.TrainTable.Cols.ECONOMY_PRICE + ", " +
                railwayDbSchema.TrainTable.Cols.BUSINESS_PRICE + ", " +
                railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID+ ", " +
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE + ", " +
                railwayDbSchema.ScheduleTable.Cols.DEPARTURE_TIME + ", " +
                railwayDbSchema.ScheduleTable.Cols.ARRIVAL_DATE + ", " +
                railwayDbSchema.ScheduleTable.Cols.ARRIVAL_TIME + ", " +
                railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY + ", " +
                railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY + ", " +
                railwayDbSchema.ScheduleTable.Cols.DURATION +
                " FROM " + railwayDbSchema.ScheduleTable.NAME + " INNER JOIN " + railwayDbSchema.TrainTable.NAME +
                " ON " + railwayDbSchema.ScheduleTable.NAME + "." + railwayDbSchema.ScheduleTable.Cols.TRAIN_ID + " = " +
                railwayDbSchema.TrainTable.NAME + "." + railwayDbSchema.TrainTable.Cols.TRAIN_ID +
                " WHERE " + (selection != null ? selection : "1") +
                " ORDER BY " + railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE + " ASC";

        Cursor cursor = db.rawQuery(rawQuery, selectionArgs);

        // Convert the query results to a list of Schedule objects
        mScheduleList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String trainScheduleId = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID));
            String trainNumber = cursor.getString(cursor.getColumnIndexOrThrow("train_id"));
            String origin = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.TrainTable.Cols.ORIGIN));
            String destination = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.TrainTable.Cols.DESTINATION));
            String departureDateStr = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE));
            String departureTime = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.DEPARTURE_TIME));
            String arrivalDateStr = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.ARRIVAL_DATE));
            String arrivalTime = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.ARRIVAL_TIME));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.DURATION));
            double eco_price = cursor.getDouble(cursor.getColumnIndexOrThrow(railwayDbSchema.TrainTable.Cols.ECONOMY_PRICE));
            double busi_price = cursor.getDouble(cursor.getColumnIndexOrThrow(railwayDbSchema.TrainTable.Cols.BUSINESS_PRICE));
            int ecoAvailability = cursor.getInt(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY));
            int busiAvailability = cursor.getInt(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY));
            mScheduleList.add(new TrainSchedule(trainScheduleId, trainNumber, origin, destination, departureDateStr, departureTime, arrivalDateStr, arrivalTime, duration, eco_price, busi_price, ecoAvailability, busiAvailability));
        }

        //get and print test data
        Cursor trainCountCursor = db.rawQuery("SELECT COUNT(*) FROM " + railwayDbSchema.TrainTable.NAME, null);
        trainCountCursor.moveToFirst();
        int trainCount = trainCountCursor.getInt(0);
        trainCountCursor.close();
        Log.d("SearchResultActivity", "Number of rows in Train table: " + trainCount);

        Cursor scheduleCountCursor = db.rawQuery("SELECT COUNT(*) FROM " + railwayDbSchema.ScheduleTable.NAME, null);
        scheduleCountCursor.moveToFirst();
        int scheduleCount = scheduleCountCursor.getInt(0);
        scheduleCountCursor.close();
        Log.d("SearchResultActivity", "Number of rows in Schedule table: " + scheduleCount);
        Log.d("SearchResultActivity", "Number of schedules retrieved: " + mScheduleList.size());


        cursor.close();

        // Get a reference to the ListView
        mListView = findViewById(R.id.list_search_results);

        // Create an adapter to convert the list of schedules to a ListView
        ScheduleListAdapter adapter = new ScheduleListAdapter(this, mScheduleList);

        // Set the adapter for the ListView
        mListView.setAdapter(adapter);

        // Set an item click listener for the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected schedule
                TrainSchedule selectedSchedule = mScheduleList.get(position);

                // Create an intent to launch the BookingActivity
                Intent intent = new Intent(SearchResultActivity.this, BookingActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("scheduleId", selectedSchedule.getTrainScheduleID());
                intent.putExtra("departure", selectedSchedule.getOrigin());
                intent.putExtra("arrival", selectedSchedule.getDestination());
                intent.putExtra("departureDate", selectedSchedule.getDepartureDate());
                intent.putExtra("departureTime", selectedSchedule.getDepartureTime());
                intent.putExtra("duration", selectedSchedule.getDuration());
                intent.putExtra("ecoPrice", selectedSchedule.getEconomyPrice());
                intent.putExtra("busiPrice", selectedSchedule.getBusinessPrice());
                intent.putExtra("ecoAvailability", selectedSchedule.getEconomySeatsAvailable());
                intent.putExtra("busiAvailability", selectedSchedule.getBusinessSeatsAvailable());
                startActivity(intent);
            }
        });

        sReturnButton = findViewById(R.id.return_button);
        // Set click listener for return button
        sReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultActivity.this, SearchActivity.class);
                intent.putExtra("from", getIntent().getStringExtra("from"));
                intent.putExtra("to", getIntent().getStringExtra("to"));
                intent.putExtra("departureDate", getIntent().getStringExtra("departureDate"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
            }
        });
    }
}