package com.example.railwayreservationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.railwayreservationproject.database.DatabaseManager;
import com.example.railwayreservationproject.database.railwayDbHelper;
import com.example.railwayreservationproject.database.railwayDbSchema;
import com.example.railwayreservationproject.database.railwayDbSchema.ClientTable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mRegisterButton;
    private ImageView mGif;

    private int currentIndex = 0;

    private railwayDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an array of train objects
        Train[] trains = new Train[]{
                new Train("123",  "Toronto", "Montreal", 50.0, 100.0),
                new Train("456",  "Vancouver", "Calgary", 75.0, 150.0),
                new Train("789",  "Toronto", "Ottawa", 80.0, 160.0)
        };

        // Create an array of schedule objects
        Schedule[] schedules = new Schedule[]{
                new Schedule("123", "2023-05-06", "08:00", "2023-05-06", "16:00", 100, 50),
                new Schedule("123", "2023-05-07", "08:00", "2023-05-07", "16:00", 50, 20),
                new Schedule("456", "2023-05-07", "10:00", "2023-05-07", "15:00", 80, 30),
                new Schedule("789", "2023-05-08", "09:00", "2023-05-08", "12:00", 60, 40),
                new Schedule("789", "2023-05-09", "09:00", "2023-05-09", "12:00", 40, 20)
        };

        // Get an instance of the database manager
        DatabaseManager dbManager = DatabaseManager.getInstance(this);

        if (dbManager.isTableEmpty(railwayDbSchema.TrainTable.NAME)) {
        // Insert the trains into the database
            for (Train train : trains) {
                ContentValues values = new ContentValues();
                values.put(railwayDbSchema.TrainTable.Cols.TRAIN_ID, train.getTrainNumber());
                values.put(railwayDbSchema.TrainTable.Cols.ORIGIN, train.getSourceStation());
                values.put(railwayDbSchema.TrainTable.Cols.DESTINATION, train.getDestinationStation());
                values.put(railwayDbSchema.TrainTable.Cols.ECONOMY_PRICE, train.getEconomyPrice());
                values.put(railwayDbSchema.TrainTable.Cols.BUSINESS_PRICE, train.getBusinessPrice());
                dbManager.getDatabase().insertWithOnConflict(railwayDbSchema.TrainTable.NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
        }

        if (dbManager.isTableEmpty(railwayDbSchema.ScheduleTable.NAME)) {
            // Insert the schedules into the database
            for (Schedule schedule : schedules) {
                ContentValues values = new ContentValues();
                values.put(railwayDbSchema.ScheduleTable.Cols.SCHEDULE_ID, schedule.getScheduleID());
                values.put(railwayDbSchema.ScheduleTable.Cols.TRAIN_ID, schedule.getTrainNum());
                values.put(railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE, schedule.getDepartureDate());
                values.put(railwayDbSchema.ScheduleTable.Cols.DEPARTURE_TIME, schedule.getDepartureTime());
                values.put(railwayDbSchema.ScheduleTable.Cols.ARRIVAL_DATE, schedule.getArrivalDate());
                values.put(railwayDbSchema.ScheduleTable.Cols.ARRIVAL_TIME, schedule.getArrivalTime());
                values.put(railwayDbSchema.ScheduleTable.Cols.ECO_AVAILABILITY, schedule.getEcoAvailability());
                values.put(railwayDbSchema.ScheduleTable.Cols.BUSI_AVAILABILITY, schedule.getBusiAvailability());
                values.put(railwayDbSchema.ScheduleTable.Cols.DURATION, schedule.getDuration());
                dbManager.getDatabase().insert(railwayDbSchema.ScheduleTable.NAME, null, values);
            }
        }

        // Initialize views
        mEmailEditText = findViewById(R.id.edit_text_email);
        mPasswordEditText = findViewById(R.id.edit_text_password);
        mLoginButton = findViewById(R.id.button_login);
        mRegisterButton = findViewById(R.id.button_register);
        mGif = findViewById(R.id.image_view_gif);
        Glide.with(this).asGif().load("https://i.pinimg.com/originals/d8/be/22/d8be227ccfb580612c557d49dc9614df.gif").into(mGif);

        // Create database helper instance
        dbHelper = railwayDbHelper.getInstance(this);

        // Get the email value from the intent extra
        String email = getIntent().getStringExtra("email");

        // Set the email value to the mEmailEditText field
        mEmailEditText.setText(email);

        // Set click listeners for buttons
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle login button click event
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if (validateInput(email, password)) {
                    if (checkLoginCredentials(email, password)) {
                        // Navigate to the account activity if login credentials are valid
                        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    } else {
                        // Display error message if login credentials are invalid
                        mPasswordEditText.setError(getString(R.string.error_invalid_credentials));
                        mPasswordEditText.requestFocus();
                    }
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle register button click event
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to validate user input
    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError(getString(R.string.error_field_required));
            mEmailEditText.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailEditText.setError(getString(R.string.error_invalid_email));
            mEmailEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError(getString(R.string.error_field_required));
            mPasswordEditText.requestFocus();
            return false;
        }

        return true;
    }

    // Method to check login credentials
    private boolean checkLoginCredentials(String email, String password) {
        // Get readable database instance
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // will be used in the query
        String[] projection = {
                railwayDbSchema.ClientTable.Cols.CLIENT_ID
        };
        // Filter results WHERE "email" = 'email' AND "password" = 'password'
        String selection = railwayDbSchema.ClientTable.Cols.EMAIL + " = ? AND "
                + railwayDbSchema.ClientTable.Cols.PASSWORD + " = ?";
        String[] selectionArgs = { email, password };

        // Execute the query
        Cursor cursor = db.query(
                railwayDbSchema.ClientTable.NAME,   // The table to query
                projection,                         // The columns to return
                selection,                          // The columns for the WHERE clause
                selectionArgs,                      // The values for the WHERE clause
                null,                               // Don't group the rows
                null,                               // Don't filter by row groups
                null                                // The sort order
        );

        int count = cursor.getCount();

        // Close the cursor and database connection
        cursor.close();
        dbHelper.close();

        // Return true if a row with the given email and password was found,
        // otherwise return false
        return count > 0;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState)
    {
        super.onSaveInstanceState(saveInstanceState);
        Log.d(TAG, "onSaveInstanceState() called");
        saveInstanceState.putInt(KEY_INDEX,currentIndex);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}