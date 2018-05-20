package com.agtajhotel.agtajhotel;

import android.annotation.SuppressLint;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.agtajhotel.agtajhotel.customerPOJO.customerBean;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Timer;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Splash extends AppCompatActivity {

    //Timer t;


    Button signin , signup;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    ProgressBar progress;


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        signin = findViewById(R.id.button);
        signup = findViewById(R.id.button2);
        progress = findViewById(R.id.progress);


        final String e = pref.getString("email" , "");
        final String p = pref.getString("pass" , "");


        if (e.length() > 0 && p.length() > 0)
        {

            CookieManager cookieManager = new CookieManager(new PersistentCookieStore(Splash.this), CookiePolicy.ACCEPT_ALL);

            CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(cookieJar);
            OkHttpClient client = builder.build();

            progress.setVisibility(View.VISIBLE);
            final bean b = (bean) getApplicationContext();
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            final AllAPIs cr = retrofit.create(AllAPIs.class);

            Call<customerBean> call = cr.login(e , p);

            call.enqueue(new Callback<customerBean>() {
                @Override
                public void onResponse(Call<customerBean> call, Response<customerBean> response) {

                    if (response.body().getCode() == 0)
                    {
                        edit.putString("email" , e);
                        edit.putString("pass" , p);
                        edit.putString("id" , response.body().getModel().getEntityId());
                        edit.putString("name" , response.body().getModel().getName());
                        edit.putString("cookie" , response.body().getModel().getSession());
                        edit.apply();

                        Toast.makeText(Splash.this , "Welcome " + response.body().getModel().getName() , Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Splash.this , MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(Splash.this , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();
                    }





                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<customerBean> call, Throwable t) {

                    progress.setVisibility(View.GONE);

                }
            });

        }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Splash.this , REgister.class);
                startActivity(intent);
                //finish();

            }
        });




        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Splash.this , Login.class);
                startActivity(intent);
                //finish();

            }
        });




    }







}
