package com.agtajhotel.agtajhotel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import im.delight.android.webview.AdvancedWebView;





public class MainActivity extends AppCompatActivity{



    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Toolbar toolbar;
    LinearLayout replace;
    TextView userame;

    String name;
    DrawerLayout drawer;
    String id;

    TextView logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();


        toolbar = findViewById(R.id.toolbar);
        replace = findViewById(R.id.replace);
        logout = findViewById(R.id.logout);

        userame = findViewById(R.id.textView3);


        id = pref.getString("id" , "");


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Category frag1 = new Category();
        ft.replace(R.id.replace , frag1);
        //ft.addToBackStack(null);
        ft.commit();


        toolbar.setTitle("Category");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                edit.remove("email");
                edit.remove("pass");
                edit.remove("id");
                edit.remove("name");
                edit.apply();

                Intent intent = new Intent(MainActivity.this , Splash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        name = pref.getString("name" , "");
        userame.setText("Hi, " + name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.cart:
                Toast.makeText(getApplicationContext(),"Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
