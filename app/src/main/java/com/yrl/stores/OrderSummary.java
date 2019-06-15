package com.yrl.stores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

public class OrderSummary extends AppCompatActivity {


    Toolbar toolbar;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);





    }
}
