package com.example.railwayreservationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.railwayreservationproject.database.railwayDbHelper;
import com.example.railwayreservationproject.database.railwayDbSchema.ClientTable;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPassEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mMobileEditText;
    private Button mConfirmButton;
    private Button mReturnButton;

    private railwayDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        mEmailEditText = findViewById(R.id.edit_text_email);
        mPasswordEditText = findViewById(R.id.edit_text_password);
        mConfirmPassEditText = findViewById(R.id.edit_text_confirm_pass);
        mFirstNameEditText = findViewById(R.id.edit_text_first_name);
        mLastNameEditText = findViewById(R.id.edit_text_last_name);
        mMobileEditText = findViewById(R.id.edit_text_mobile);
        mConfirmButton = findViewById(R.id.button_confirm);
        mReturnButton = findViewById(R.id.button_return);

        // Create database helper instance
        dbHelper = railwayDbHelper.getInstance(this);

        // Set click listener for confirm button
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle confirm button click event
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String confirmPass = mConfirmPassEditText.getText().toString();
                String firstName = mFirstNameEditText.getText().toString();
                String lastName = mLastNameEditText.getText().toString();
                String mobile = mMobileEditText.getText().toString();
                if (validateInput(email, password, confirmPass, firstName, lastName, mobile)) {
                    // Save user data to database
                    saveClientToDatabase(email, password, firstName, lastName, mobile);

                    // Display the toast message
                    Toast.makeText(RegisterActivity.this, R.string.registration_successful, Toast.LENGTH_SHORT).show();

                    // Create an intent to return to the MainActivity
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                    // Pass the email value as an extra
                    intent.putExtra("email", email);

                    // Start the MainActivity and finish the RegisterActivity
                    startActivity(intent);
                    finish();
                }
            }
        });

        // Set click listener for return button
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle return button click event
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method to validate user input
    private boolean validateInput(String email, String password, String confirmPass, String firstName, String lastName, String mobile) {
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

        if (TextUtils.isEmpty(confirmPass)) {
            mPasswordEditText.setError(getString(R.string.error_field_required));
            mPasswordEditText.requestFocus();
            return false;
        }

        // Check whether the password meets the requirements
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        if (!password.matches(passwordRegex)) {
            mPasswordEditText.setError(getString(R.string.error_invalid_password));
            mPasswordEditText.requestFocus();
            return false;
        }

        if (!password.equals(confirmPass)) {
            mConfirmPassEditText.setError(getString(R.string.error_password_mismatch));
            mConfirmPassEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(firstName)) {
            mFirstNameEditText.setError(getString(R.string.error_field_required));
            mFirstNameEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            mLastNameEditText.setError(getString(R.string.error_field_required));
            mLastNameEditText.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mobile)) {
            mMobileEditText.setError(getString(R.string.error_field_required));
            mMobileEditText.requestFocus();
            return false;
        }

        // Check if email already exists in database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ClientTable.Cols.EMAIL
        };
        String selection = ClientTable.Cols.EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                ClientTable.NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() > 0) {
            // Email already exists, display error message
            mEmailEditText.setError(getString(R.string.error_email_already_exists));
            mEmailEditText.requestFocus();
            return false;
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        return true;
    }

    // Method to save client data to database
    private void saveClientToDatabase(String email, String password, String firstName, String lastName, String mobile) {
        // Get writable database instance
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values to store client data
        ContentValues values = new ContentValues();
        values.put(ClientTable.Cols.EMAIL, email);
        values.put(ClientTable.Cols.PASSWORD, password);
        values.put(ClientTable.Cols.FIRSTNAME, firstName);
        values.put(ClientTable.Cols.LASTNAME, lastName);
        values.put(ClientTable.Cols.MOBILE, mobile);

        // Insert the new row into the "clients" table and get the generated ID
        long newRowId = db.insert(ClientTable.NAME, null, values);

        // Update the client ID with the generated ID
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(ClientTable.Cols.CLIENT_ID, newRowId);

        String selection = ClientTable.Cols.CLIENT_ID + " = ?";
        String[] selectionArgs = { Long.toString(newRowId) };

        int count = db.update(
                ClientTable.NAME,
                updatedValues,
                selection,
                selectionArgs
        );
    }

    // Method to close database connection
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}