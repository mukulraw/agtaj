package com.agtajhotel.agtajhotel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.agtajhotel.agtajhotel.billingPOJO.billingBean;
import com.agtajhotel.agtajhotel.shippingPOJO.shippingBean;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ShippingInfo extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    Spinner spinner;
    EditText first, last, company, address, city, state, zip, phone;
    CheckBox ship;
    Button next;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_info);

        price = getIntent().getStringExtra("price");

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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Shipping Info");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        next = findViewById(R.id.button10);
        progress = findViewById(R.id.progress);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String f = first.getText().toString();
                String l = last.getText().toString();
                String c = company.getText().toString();
                String a = address.getText().toString();
                String ci = city.getText().toString();
                String s = state.getText().toString();
                String z = zip.getText().toString();
                String p = phone.getText().toString();


                if (f.length() > 0) {
                    if (l.length() > 0) {
                        if (c.length() > 0) {

                            if (a.length() > 0) {

                                if (ci.length() > 0) {

                                    if (s.length() > 0) {

                                        if (z.length() > 0) {

                                            if (p.length() == 10) {


                                                progress.setVisibility(View.VISIBLE);

                                                CookieManager cookieManager = new CookieManager(new PersistentCookieStore(ShippingInfo.this), CookiePolicy.ACCEPT_ALL);

                                                CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
                                                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                                                builder.cookieJar(cookieJar);
                                                OkHttpClient client = builder.build();

                                                final bean b = (bean) getApplicationContext();
                                                final Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(b.BASE_URL)
                                                        .addConverterFactory(ScalarsConverterFactory.create())
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .client(client)
                                                        .build();
                                                final AllAPIs cr = retrofit.create(AllAPIs.class);

                                                Call<shippingBean> call = cr.setShipping(
                                                        "",
                                                        "",
                                                        f,
                                                        l,
                                                        c,
                                                        a,
                                                        "",
                                                        ci,
                                                        "",
                                                        s,
                                                        "IN",
                                                        p,
                                                        "",
                                                        "0"
                                                );

                                                call.enqueue(new Callback<shippingBean>() {
                                                    @Override
                                                    public void onResponse(Call<shippingBean> call, Response<shippingBean> response) {

                                                        if (response.body().getCode() == 0)
                                                        {
                                                            Intent intent = new Intent(ShippingInfo.this , ShippingMethod.class);
                                                            intent.putExtra("price" , price);
                                                            startActivity(intent);
                                                        }



                                                        progress.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<shippingBean> call, Throwable t) {
                                                        progress.setVisibility(View.GONE);
                                                    }
                                                });


                                            } else {
                                                phone.setError("Invalid Field");
                                                phone.requestFocus();
                                            }

                                        } else {
                                            zip.setError("Invalid Field");
                                            zip.requestFocus();
                                        }

                                    } else {
                                        state.requestFocus();
                                        state.setError("Invalid Field");
                                    }

                                } else {
                                    city.setError("Invalid Field");
                                    city.requestFocus();
                                }

                            } else {
                                address.setError("Invalid Field");
                                address.requestFocus();
                            }

                        } else {
                            company.setError("Invalid Field");
                            company.requestFocus();
                        }
                    } else {
                        last.requestFocus();
                        last.setError("Invalid Field");
                    }
                } else {
                    first.requestFocus();
                    first.setError("Invalid Field");
                }


            }
        });

    }
}
