package com.yrl.stores;

import android.annotation.SuppressLint;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.yrl.stores.customerPOJO.customerBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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


            progress.setVisibility(View.VISIBLE);
            final bean b = (bean) getApplicationContext();

            Call<customerBean> call = b.getRetrofit().login(e , p);

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
