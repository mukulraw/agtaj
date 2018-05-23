package com.agtajhotel.agtajhotel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.agtajhotel.agtajhotel.shippingMethodListPOJO.shippingMethodListBean;
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

public class ShippingMethod extends AppCompatActivity {

    Toolbar toolbar;
    RadioGroup group;
    Button next;
    ProgressBar progress;

    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_method);

        price = getIntent().getStringExtra("price");

        toolbar = findViewById(R.id.toolbar4);
        group = findViewById(R.id.radioGroup);
        next = findViewById(R.id.button11);
        progress = findViewById(R.id.progressBar2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Shipping Methods");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        progress.setVisibility(View.VISIBLE);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(ShippingMethod.this), CookiePolicy.ACCEPT_ALL);

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

        final Call<shippingMethodListBean> call = cr.getShippingMethods();

        call.enqueue(new Callback<shippingMethodListBean>() {
            @Override
            public void onResponse(Call<shippingMethodListBean> call, Response<shippingMethodListBean> response) {

                for (int i = 0 ; i < response.body().getModel().getFlatRate().size() ; i++)
                {
                    RadioButton rb = new RadioButton(ShippingMethod.this);
                    rb.setText(response.body().getModel().getFlatRate().get(i).getMethodTitle() + " " + response.body().getModel().getFlatRate().get(i).getPrice());
                    group.addView(rb);
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<shippingMethodListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = group.getCheckedRadioButtonId();

                if (id != -1)
                {


                    progress.setVisibility(View.VISIBLE);

                    CookieManager cookieManager = new CookieManager(new PersistentCookieStore(ShippingMethod.this), CookiePolicy.ACCEPT_ALL);

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

                    Call<shippingMethodBean> call1 = cr.setShippingMethod("flatrate_flatrate");

                    call1.enqueue(new Callback<shippingMethodBean>() {
                        @Override
                        public void onResponse(Call<shippingMethodBean> call, Response<shippingMethodBean> response) {


                            Intent intent = new Intent(ShippingMethod.this , PaymentInfo.class);
                            intent.putExtra("price" , price);
                            startActivity(intent);


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
                    Toast.makeText(ShippingMethod.this , "Please choose a shipping method" , Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
