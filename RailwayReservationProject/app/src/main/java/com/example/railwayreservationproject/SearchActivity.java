package com.example.railwayreservationproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.railwayreservationproject.database.railwayDbSchema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText leavingFromEditText;
    private EditText goingToEditText;
    private EditText departureDateEditText;

    private Button searchButton;
    private Button returnButton;

    private Calendar departureDate;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        leavingFromEditText = findViewById(R.id.edit_text_leaving_from);
        goingToEditText = findViewById(R.id.edit_text_going_to);
        departureDateEditText = findViewById(R.id.edit_text_departure_date);

        searchButton = findViewById(R.id.button_search);
        searchButton.setOnClickListener(this);

        returnButton = findViewById(R.id.button_backToAccount);

        departureDateEditText.setOnClickListener(this);

        // Set the default departure date as today
        departureDate = Calendar.getInstance();
        departureDateEditText.setText(dateFormatter.format(departureDate.getTime()));

        // Set click listener for return button
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle return button click event
                Intent intent = new Intent(SearchActivity.this, AccountActivity.class);
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == searchButton) {
            String from = leavingFromEditText.getText().toString();
            String to = goingToEditText.getText().toString();
            String departureDate = departureDateEditText.getText().toString();

            String selection = null;
            ArrayList<String> selectionArgsList = new ArrayList<>();

            // Add conditions based on user input
            if (!TextUtils.isEmpty(from)) {
                selection = "LOWER(" + railwayDbSchema.TrainTable.Cols.ORIGIN + ")=LOWER(?)";
                selectionArgsList.add(from);
            }

            if (!TextUtils.isEmpty(to)) {
                if (selection != null) {
                    selection += " AND ";
                } else {
                    selection = "";
                }
                selection += "LOWER(" + railwayDbSchema.TrainTable.Cols.DESTINATION + ")=LOWER(?)";
                selectionArgsList.add(to);
            }

            if (!TextUtils.isEmpty(departureDate)) {
                if (selection != null) {
                    selection += " AND ";
                } else {
                    selection = "";
                }
                selection += railwayDbSchema.ScheduleTable.Cols.DEPARTURE_DATE + "=?";
                selectionArgsList.add(departureDate);
            }

            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("email", getIntent().getStringExtra("email"));
            intent.putExtra("selection", selection);
            intent.putExtra("selectionArgs", selectionArgsList.toArray(new String[0]));
            startActivity(intent);
        } else if (v == departureDateEditText) {
            // Show the date picker when the departure date EditText is clicked
            showDatePicker();
        }
    }


    private void showDatePicker() {
        // Get the current date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Create a new DatePickerDialog instance and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Format the selected date and set it to the departureDateEditText
                        String date = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        departureDateEditText.setText(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}