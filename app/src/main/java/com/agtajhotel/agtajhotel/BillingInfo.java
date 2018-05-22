package com.agtajhotel.agtajhotel;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class BillingInfo extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    Spinner spinner;
    EditText first , last , company , address , city , state , zip , phone;
    CheckBox ship;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_info);

        toolbar = findViewById(R.id.toolbar3);
        spinner = findViewById(R.id.spinner);
        first = findViewById(R.id.editText3);
        last = findViewById(R.id.editText4);
        company = findViewById(R.id.editText5);
        address = findViewById(R.id.editText6);
        city = findViewById(R.id.editText7);
        state = findViewById(R.id.editText8);
        zip = findViewById(R.id.editText10);
        phone = findViewById(R.id.editText12);
        ship = findViewById(R.id.checkBox);

        next = findViewById(R.id.button10);
        progress = findViewById(R.id.progress);







    }
}
