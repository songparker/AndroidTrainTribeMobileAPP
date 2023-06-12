package com.example.railwayreservationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railwayreservationproject.database.railwayDbHelper;
import com.example.railwayreservationproject.database.railwayDbSchema;

public class BookingActivity extends AppCompatActivity {

    private TextView mTextTrainNum;
    private TextView mTextDeparture;
    private TextView mTextArrival;
    private TextView mTextDepartureDate;
    private TextView mTextDepartureTime;
    private TextView mTextDuration;
    private TextView mTextEcoPrice;
    private TextView mTextBusiPrice;
    private TextView mTextEcoAvailability;
    private TextView mTextBusiAvailability;
    private EditText mEditEcoTickets;
    private EditText mEditBusiTickets;
    private Button mButtonConfirm;
    private Button mButtonReturn;
    private railwayDbHelper dbHelper;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Get the email passed from the previous activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // Retrieve the client record for the current user
        dbHelper = new railwayDbHelper(this);
        Client client = dbHelper.getClientByEmail(email);

        // Get the data passed from the previous activity
        String email = getIntent().getStringExtra("email");
        String scheduleId = getIntent().getStringExtra("scheduleId");
        String departure = getIntent().getStringExtra("departure");
        String arrival = getIntent().getStringExtra("arrival");
        String departureDate = getIntent().getStringExtra("departureDate");
        String departureTime = getIntent().getStringExtra("departureTime");
        String duration = getIntent().getStringExtra("duration");
        double ecoPrice = getIntent().getDoubleExtra("ecoPrice", 0.0);
        double busiPrice = getIntent().getDoubleExtra("busiPrice", 0.0);
        int ecoAvailability = getIntent().getIntExtra("ecoAvailability", 0);
        int busiAvailability = getIntent().getIntExtra("busiAvailability", 0);

        // Query the schedule table to retrieve the train number for the selected schedule
        SQLiteDatabase db = new railwayDbHelper(this).getReadableDatabase();
        String[] projection = {railwayDbSchema.ScheduleTable.Cols.TRAIN_ID};
        String selection = railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID + "=?";
        String[] selectionArgs = {scheduleId};
        Cursor cursor = db.query(railwayDbSchema.ScheduleTable.NAME, projection, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        String trainNumber = cursor.getString(cursor.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.TRAIN_ID));
        cursor.close();
        db.close();

        // Initialize views
        mTextTrainNum = findViewById(R.id.text_train_id);
        mTextDeparture = findViewById(R.id.text_departure);
        mTextArrival = findViewById(R.id.text_arrival);
        mTextDepartureDate = findViewById(R.id.text_departure_date);
        mTextDepartureTime = findViewById(R.id.text_departure_time);
        mTextDuration = findViewById(R.id.text_duration);
        mTextEcoPrice = findViewById(R.id.text_eco_price);
        mTextBusiPrice = findViewById(R.id.text_busi_price);
        mTextEcoAvailability = findViewById(R.id.eco_availability);
        mTextBusiAvailability = findViewById(R.id.busi_availability);
        mEditEcoTickets = findViewById(R.id.edit_eco_tickets);
        mEditBusiTickets = findViewById(R.id.edit_busi_tickets);
        mButtonConfirm = findViewById(R.id.button_confirm_booking);
        mButtonReturn = findViewById(R.id.button_return);

        // Set the values for the TextViews
        mTextTrainNum.setText("Train Num: "+trainNumber);
        mTextDeparture.setText("Departure: "+departure);
        mTextArrival.setText("Arrival: "+arrival);
        mTextDepartureDate.setText("Departure Date: "+ departureDate);
        mTextDepartureTime.setText("Arrival Date: "+departureTime);
        mTextDuration.setText("Duration: "+duration);
        mTextEcoPrice.setText("Economy Price: "+String.valueOf(ecoPrice));
        mTextBusiPrice.setText("Business Price:  "+String.valueOf(busiPrice));
        mTextEcoAvailability.setText("Economy Availability: "+String.valueOf(ecoAvailability));
        mTextBusiAvailability.setText("Business Availability: "+String.valueOf(busiAvailability));

        // Set click listener for the confirm button
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user has selected at least one ticket
                String ecoTicketsStr = mEditEcoTickets.getText().toString();
                String busiTicketsStr = mEditBusiTickets.getText().toString();

                int ecoTickets = 0;
                int busiTickets = 0;

                if (!ecoTicketsStr.isEmpty()) {
                    try {
                        ecoTickets = Integer.parseInt(ecoTicketsStr);
                    } catch (NumberFormatException e) {
                        mEditEcoTickets.setError("Please enter a valid number");
                        return;
                    }
                }

                if (!busiTicketsStr.isEmpty()) {
                    try {
                        busiTickets = Integer.parseInt(busiTicketsStr);
                    } catch (NumberFormatException e) {
                        mEditBusiTickets.setError("Please enter a valid number");
                        return;
                    }
                }

                if (ecoTickets == 0 && busiTickets == 0) {
                    mEditEcoTickets.setError("Sorry you need to book at least 1 ticket to make an order");
                    return;
                }



                // Check if the number of selected tickets is available
                if (ecoTickets > ecoAvailability) {
                    mEditEcoTickets.setError("Sorry, only " + ecoAvailability + " economy tickets are available.");
                    mEditEcoTickets.requestFocus();
                    return;
                }

                if (busiTickets > busiAvailability) {
                    mEditBusiTickets.setError("Sorry, only " + busiAvailability + " business tickets are available.");
                    mEditBusiTickets.requestFocus();
                    return;
                }

                // Calculate the total price
                double totalPrice = ecoTickets * ecoPrice + busiTickets * busiPrice;

                // Create the order record
                ContentValues values = new ContentValues();
                values.put(railwayDbSchema.OrderTable.Cols.CLIENT_ID, email);
                values.put(railwayDbSchema.OrderTable.Cols.TRAIN_ID, trainNumber);
                values.put(railwayDbSchema.OrderTable.Cols.SCHEDULE_ID, scheduleId);
                values.put(railwayDbSchema.OrderTable.Cols.ECO_NUMBER, ecoTickets);
                values.put(railwayDbSchema.OrderTable.Cols.BUSI_NUMBER, busiTickets);
                values.put(railwayDbSchema.OrderTable.Cols.TOTAL_PRICE, totalPrice);

                // Insert the order record into the database
                // Create an instance of your database helper class
                // Get a writable database
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.insert(railwayDbSchema.OrderTable.NAME, null, values);

                // Update the availability of eco seats and business seats in the Schedule table
                int newEcoAvailability = ecoAvailability - ecoTickets;
                int newBusiAvailability = busiAvailability - busiTickets;
                ContentValues newValues = new ContentValues();
                newValues.put(railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY, newEcoAvailability);
                newValues.put(railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY, newBusiAvailability);
                String whereClause = railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID + " = ?";
                String[] whereArgs = {scheduleId};
                db.update(railwayDbSchema.ScheduleTable.NAME, newValues, whereClause, whereArgs);

                // Set the text of the EditText fields with the confirmation message and billing information
                mEditEcoTickets.setText("Order confirmed, you have ordered " + ecoTickets + " eco tickets and " + busiTickets + " busi tickets.");
                mEditBusiTickets.setText("Total billing: $" + totalPrice);

                // Get the updated availability from the database
                String[] projection = {
                        railwayDbSchema.ScheduleTable.Cols.TRAIN_ID,
                        railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY,
                        railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY
                };
                Cursor cursorUpdate = db.query(railwayDbSchema.ScheduleTable.NAME, projection, selection, selectionArgs, null, null, null);
                cursorUpdate.moveToFirst();
                int updatedEcoAvailability = cursorUpdate.getInt(cursorUpdate.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY));
                int updatedBusiAvailability = cursorUpdate.getInt(cursorUpdate.getColumnIndexOrThrow(railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY));
                cursorUpdate.close();

                // Update the TextViews with the new availability data
                mTextEcoAvailability.setText("Economy Availability: " + String.valueOf(updatedEcoAvailability));
                mTextBusiAvailability.setText("Business Availability: " + String.valueOf(updatedBusiAvailability));

                // Add a Toast message
                Toast.makeText(BookingActivity.this, "Booking confirmed", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for the return button
        mButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the search activity
                Intent intent = new Intent(BookingActivity.this, SearchResultActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
