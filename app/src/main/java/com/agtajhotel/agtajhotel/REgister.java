package com.agtajhotel.agtajhotel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class REgister extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    EditText firstname , lastName , email , phone , pass , retPass;
    Button register;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        firstname = findViewById(R.id.editText3);
        lastName = findViewById(R.id.editText4);
        email = findViewById(R.id.editText5);
        phone = findViewById(R.id.editText6);
        pass = findViewById(R.id.editText7);
        retPass = findViewById(R.id.editText8);
        register = findViewById(R.id.button4);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setTitle("Sign Up");
        toolbar.setNavigationIcon(R.drawable.back_black);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fn = firstname.getText().toString();
                String ln = lastName.getText().toString();
                final String e = email.getText().toString();
                String ph = phone.getText().toString();
                final String pa = pass.getText().toString();
                String rp = retPass.getText().toString();


                if (fn.length() > 0)
                {

                    if (ln.length() > 0)
                    {
                        if (e.length() > 0)
                        {
                            if (ph.length() > 0)
                            {
                                if (pa.length() > 0)
                                {
                                    if (rp.equals(pa))
                                    {

                                        progress.setVisibility(View.VISIBLE);
                                        final bean b = (bean) getApplicationContext();
                                        final Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.BASE_URL)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        final AllAPIs cr = retrofit.create(AllAPIs.class);

                                        Call<customerBean> call = cr.register(fn , ln , e , ph , pa);

                                        call.enqueue(new Callback<customerBean>() {
                                            @Override
                                            public void onResponse(Call<customerBean> call, Response<customerBean> response) {

                                                if (response.body().getCode() == 0)
                                                {
                                                    edit.putString("email" , e);
                                                    edit.putString("pass" , pa);
                                                    edit.putString("id" , response.body().getModel().getEntityId());
                                                    edit.putString("name" , response.body().getModel().getName());
                                                    edit.apply();

                                                    Toast.makeText(REgister.this , "Welcome " + response.body().getModel().getName() , Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(REgister.this , MainActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                                else
                                                {
                                                    Toast.makeText(REgister.this , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();
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
                                        //rp error
                                    }
                                }
                                else
                                {
                                    //pass error
                                }
                            }
                            else
                            {
                                //phone error
                            }
                        }
                        else
                        {
                            //email error
                        }
                    }
                    else
                    {
                        //ln error
                    }


                }
                else
                {
                    //fn error
                }


            }
        });


    }
}
