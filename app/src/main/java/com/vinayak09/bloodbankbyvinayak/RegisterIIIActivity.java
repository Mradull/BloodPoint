package com.vinayak09.bloodbankbyvinayak;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterIIIActivity extends AppCompatActivity {

    AutoCompleteTextView bloodgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_i_i_i);

        initializeComponents();

        String[] bloodGroups = getResources().getStringArray(R.array.blood_groups);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bloodGroups);

        bloodgrp.setAdapter(adapter);

        // Directly call the method to add data to the database
        addToDatabase();
    }

    private void initializeComponents() {
        bloodgrp = findViewById(R.id.bloodGrpDropDown);
    }

    private void addToDatabase() {
        // Create a HashMap with the required data
        HashMap<String, Object> values = new HashMap<>();
        values.put("Step", "Done");
        values.put("BloodGroup", bloodgrp.getText().toString());
        values.put("Visible", "True");

        // Update the data in the database
        FirebaseDatabase.getInstance().getReference("Donors/" + FirebaseAuth.getInstance().getUid())
                .updateChildren(values)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // If successful, navigate to the DisplayRequestsActivity
                            startActivity(new Intent(RegisterIIIActivity.this, DispalyRequestsActivity.class));
                            // Finish this activity to prevent going back to it
                            finish();
                        } else {
                            // If unsuccessful, display an error message
                            Toast.makeText(RegisterIIIActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
