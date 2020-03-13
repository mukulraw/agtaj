package com.annieshub.stores;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
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
