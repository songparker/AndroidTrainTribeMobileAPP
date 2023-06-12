package com.example.railwayreservationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.railwayreservationproject.database.railwayDbSchema;
import com.example.railwayreservationproject.database.railwayDbHelper;

public class EditAccountActivity extends AppCompatActivity {
    private EditText editFirstName, editLastName, editMobile;
    private TextView textEmail;
    private String email;
    private railwayDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        // Get the email passed from the previous activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        // Retrieve the client record for the current user
        dbHelper = new railwayDbHelper(this);
        Client client = dbHelper.getClientByEmail(email);

        // Set the default values for the EditText fields
        textEmail = findViewById(R.id.edit_text_email);
        textEmail.setText(email);

        editFirstName = findViewById(R.id.edit_first_name);
        editFirstName.setText(client.getFirstName());

        editLastName = findViewById(R.id.edit_last_name);
        editLastName.setText(client.getLastName());

        editMobile = findViewById(R.id.edit_mobile);
        editMobile.setText(client.getMobile());

        // Initialize the confirm button
        Button buttonConfirm = findViewById(R.id.edit_button_confirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new values from the EditText fields
                String firstName = editFirstName.getText().toString().trim();
                String lastName = editLastName.getText().toString().trim();
                String mobile = editMobile.getText().toString().trim();

                // Update the client record in the database
                Client updatedClient = new Client(client.getUserID(), client.getEmail(), client.getPassword(), firstName, lastName, mobile);
                boolean success = dbHelper.updateClient(updatedClient);

                // Show a message based on the update result
                if (success) {
                    Toast.makeText(EditAccountActivity.this, "Account updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditAccountActivity.this, "Failed to update account", Toast.LENGTH_SHORT).show();
                }

                // Return to the previous activity
                Intent returnIntent = new Intent();
                // For the successful account update
                setResult(Activity.RESULT_OK, returnIntent);
                // For the canceled account update
                setResult(Activity.RESULT_CANCELED, returnIntent);
            }
        });

        // Initialize the return button
        Button buttonReturn = findViewById(R.id.edit_button_return);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the previous activity without making any changes
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}