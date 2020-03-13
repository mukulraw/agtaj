package com.annieshub.stores;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class Web extends AppCompatActivity {

    Toolbar toolbar;
    WebView web;

    String title;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        toolbar = findViewById(R.id.toolbar7);
        web = findViewById(R.id.web);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        toolbar.setTitle(title);


        web.loadUrl(url);

    }
}
