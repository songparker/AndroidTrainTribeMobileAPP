package com.example.railwayreservationproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railwayreservationproject.database.railwayDbHelper;
import com.example.railwayreservationproject.database.railwayDbSchema;

public class AccountActivity extends AppCompatActivity {
    private TextView mWelcomeTextView;
    private TextView mUserNameTextView;
    private Button mEditFileButton;
    //private Button mManageBookingsButton;
    private Button mPlanNewTripButton;
    private Button mLogOutButton;

    private railwayDbHelper dbHelper;

    private static final int REQUEST_CODE_EDIT_ACCOUNT = 1;
    private ActivityResultLauncher<Intent> editAccountResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize views
        mWelcomeTextView = findViewById(R.id.text_view_welcome_message);
        mUserNameTextView = findViewById(R.id.text_view_user_name);
        mEditFileButton = findViewById(R.id.button_edit_file);
        //mManageBookingsButton = findViewById(R.id.button_manage_bookings);
        mPlanNewTripButton = findViewById(R.id.button_plan_new_trip);
        mLogOutButton = findViewById(R.id.button_log_out);

        // Get the email value from the intent extra
        String email = getIntent().getStringExtra("email");

        // Create database helper instance
        dbHelper = railwayDbHelper.getInstance(this);

        // Retrieve user details from the database
        String firstName = "";
        String lastName = "";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                railwayDbSchema.ClientTable.Cols.FIRSTNAME,
                railwayDbSchema.ClientTable.Cols.LASTNAME
        };
        String selection = railwayDbSchema.ClientTable.Cols.EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                railwayDbSchema.ClientTable.NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndex(railwayDbSchema.ClientTable.Cols.FIRSTNAME);
            int lastNameIndex = cursor.getColumnIndex(railwayDbSchema.ClientTable.Cols.LASTNAME);
            if (firstNameIndex != -1 && lastNameIndex != -1) {
                firstName = cursor.getString(firstNameIndex);
                lastName = cursor.getString(lastNameIndex);
            } else {
                Toast.makeText(this, "Invalid column name in projection", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
            finish();
        }

        cursor.close();
        dbHelper.close();

        // Set the user's name in the welcome message and user name TextViews
        mWelcomeTextView.setText("Welcome to Train Tribe");
        mUserNameTextView.setText(firstName + " " + lastName);

        editAccountResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Account was updated successfully
                        // You can update the UI here if needed
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // Account update was canceled
                    }
                });

        // Set click listeners for buttons
        mEditFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, EditAccountActivity.class);
                intent.putExtra("email", email);
                editAccountResultLauncher.launch(intent);
            }
        });

        /*
        mManageBookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Manage Bookings button click event
            }
        });
         */

        mPlanNewTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Plan New Trip button click event
                Intent intent = new Intent(AccountActivity.this, SearchActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle Log Out button click event
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_ACCOUNT) {
            if (resultCode == Activity.RESULT_OK) {
                // Account was updated successfully
                // You can update the UI here if needed
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Account update was canceled
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the email value from the intent extra
        String email = getIntent().getStringExtra("email");

        updateUserName(email);
    }

    private void updateUserName(String email) {
        // Retrieve user details from the database
        String firstName = "";
        String lastName = "";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                railwayDbSchema.ClientTable.Cols.FIRSTNAME,
                railwayDbSchema.ClientTable.Cols.LASTNAME
        };
        String selection = railwayDbSchema.ClientTable.Cols.EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                railwayDbSchema.ClientTable.NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndex(railwayDbSchema.ClientTable.Cols.FIRSTNAME);
            int lastNameIndex = cursor.getColumnIndex(railwayDbSchema.ClientTable.Cols.LASTNAME);
            if (firstNameIndex != -1 && lastNameIndex != -1) {
                firstName = cursor.getString(firstNameIndex);
                lastName = cursor.getString(lastNameIndex);
            } else {
                Toast.makeText(this, "Invalid column name in projection", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
            finish();
        }

        cursor.close();
        dbHelper.close();

        // Set the user's name in the welcome message and user name TextViews
        mUserNameTextView.setText(firstName + " " + lastName);
    }
}
