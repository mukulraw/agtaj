package com.agtajhotel.agtajhotel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agtajhotel.agtajhotel.customerPOJO.customerBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {


    Toolbar toolbar;
    ProgressBar progress;
    EditText email , pass;
    Button signin;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        email = findViewById(R.id.editText);
        pass = findViewById(R.id.editText2);
        signin = findViewById(R.id.button3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Sign In");
        toolbar.setNavigationIcon(R.drawable.back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String e = email.getText().toString();
                final String p = pass.getText().toString();

                if (e.length() > 0)
                {
                    if (p.length() > 0)
                    {

                        progress.setVisibility(View.VISIBLE);
                        final bean b = (bean) getApplicationContext();
                        final Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
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
                                    edit.apply();

                                    Toast.makeText(Login.this , "Welcome " + response.body().getModel().getName() , Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this , MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else
                                {
                                    Toast.makeText(Login.this , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();
                                }





                                progress.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<customerBean> call, Throwable t) {

                                progress.setVisibility(View.GONE);

                            }
                        });

                    }
                    else
                    {
                        //password error
                    }
                }
                else
                {
                    //Email error
                }


            }
        });

    }
}
