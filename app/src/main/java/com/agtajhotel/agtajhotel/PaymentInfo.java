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
import android.widget.RadioGroup;
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

    RadioGroup group;

    String method = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);

        toolbar = findViewById(R.id.toolbar5);
        radio = findViewById(R.id.radioButton);
        next = findViewById(R.id.button12);
        progress = findViewById(R.id.progressBar3);

        group = findViewById(R.id.group);

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

                int iidd = group.getCheckedRadioButtonId();

                if (iidd > -1) {
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

                    Call<shippingMethodBean> call;

                    if (iidd == R.id.radioButton) {
                        call = cr.setPaymentMethod("ccavenues");
                        method = "online";
                    } else {
                        call = cr.setPaymentMethod("cashondelivery");
                        method = "cod";
                    }


                    call.enqueue(new Callback<shippingMethodBean>() {
                        @Override
                        public void onResponse(Call<shippingMethodBean> call, Response<shippingMethodBean> response) {



                            //Toast.makeText(PaymentInfo.this, method , Toast.LENGTH_SHORT).show();

                            if (response.body().getCode() == 0) {


                                if (method.equals("online")) {
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

                                            if (response.body().getCode() == 0) {


                                                if (method.equals("online")) {
                                                    Intent intent = new Intent(PaymentInfo.this, WebViewActivity.class);
                                                    intent.putExtra(AvenuesParams.ACCESS_CODE, "AVXO77FC10CE48OXEC");
                                                    intent.putExtra(AvenuesParams.MERCHANT_ID, "171284");
                                                    intent.putExtra(AvenuesParams.ORDER_ID, response.body().getModel());
                                                    intent.putExtra(AvenuesParams.CURRENCY, "INR");
                                                    intent.putExtra(AvenuesParams.AMOUNT, price);

                                                    intent.putExtra(AvenuesParams.REDIRECT_URL, "http://agtajhotel.com/Restaurent/api/ccavResponseHandler.php");
                                                    intent.putExtra(AvenuesParams.CANCEL_URL, "http://agtajhotel.com/Restaurent/api/ccavResponseHandler.php");
                                                    intent.putExtra(AvenuesParams.RSA_KEY_URL, "http://agtajhotel.com/Restaurent/api/GetRSA.php");

                                                    startActivity(intent);
                                                } else {
                                                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                                                    intent.putExtra("transStatus", "success");
                                                    startActivity(intent);
                                                }


                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<formBean> call, Throwable t) {
                                            progress.setVisibility(View.GONE);
                                        }
                                    });


                                } else {
                                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                                    intent.putExtra("transStatus", "success");
                                    startActivity(intent);
                                }
                            }


                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<shippingMethodBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            t.printStackTrace();
                        }
                    });

                } else {
                    Toast.makeText(PaymentInfo.this, "Please choose a method", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
