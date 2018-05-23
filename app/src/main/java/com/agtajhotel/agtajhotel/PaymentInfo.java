package com.agtajhotel.agtajhotel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.SafeBrowsingResponse;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.agtajhotel.agtajhotel.shippingMethodPOJO.shippingMethodBean;

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

public class PaymentInfo extends AppCompatActivity {

    Toolbar toolbar;
    RadioButton radio;
    Button next;
    ProgressBar progress;

    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);

        toolbar = findViewById(R.id.toolbar5);
        radio = findViewById(R.id.radioButton);
        next = findViewById(R.id.button12);
        progress = findViewById(R.id.progressBar3);

        price = getIntent().getStringExtra("price");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Payment Method");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio.isChecked())
                {
                    progress.setVisibility(View.VISIBLE);

                    CookieManager cookieManager = new CookieManager(new PersistentCookieStore(PaymentInfo.this), CookiePolicy.ACCEPT_ALL);

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

                    Call<shippingMethodBean> call = cr.setPaymentMethod("ccavenues");

                    call.enqueue(new Callback<shippingMethodBean>() {
                        @Override
                        public void onResponse(Call<shippingMethodBean> call, Response<shippingMethodBean> response) {


                            if (response.body().getCode() == 0)
                            {


                                progress.setVisibility(View.VISIBLE);

                                CookieManager cookieManager = new CookieManager(new PersistentCookieStore(PaymentInfo.this), CookiePolicy.ACCEPT_ALL);

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


                                Call<formBean> call1 = cr.getFormKey();


                                call1.enqueue(new Callback<formBean>() {
                                    @Override
                                    public void onResponse(Call<formBean> call, Response<formBean> response) {


                                        /*Intent intent = new Intent(PaymentInfo.this , OrderSummary.class);
                                        startActivity(intent);*/

                                        if (response.body().getCode() == 0)
                                        {


                                            Intent intent = new Intent(PaymentInfo.this , WebViewActivity.class);
                                            intent.putExtra(AvenuesParams.ACCESS_CODE, "AVXO77FC10CE48OXEC");
                                            intent.putExtra(AvenuesParams.MERCHANT_ID, "171284");
                                            intent.putExtra(AvenuesParams.ORDER_ID, response.body().getModel());
                                            intent.putExtra(AvenuesParams.CURRENCY, "INR");
                                            intent.putExtra(AvenuesParams.AMOUNT, price);


                                            startActivity(intent);


                                        }



                                    }

                                    @Override
                                    public void onFailure(Call<formBean> call, Throwable t) {
                                        progress.setVisibility(View.GONE);
                                    }
                                });




                            }


                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<shippingMethodBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    Toast.makeText(PaymentInfo.this , "Please choose a method" , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
