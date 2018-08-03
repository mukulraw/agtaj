package com.agtajhotel.agtajhotel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agtajhotel.agtajhotel.successPOJO.successBean;

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

public class StatusActivity extends AppCompatActivity {

    String transStatus;
    ImageView image;
    TextView text;
    Button ok;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        transStatus = getIntent().getStringExtra("transStatus");
        image = findViewById(R.id.imageView7);
        text = findViewById(R.id.textView19);
        ok = findViewById(R.id.button13);
        progress = findViewById(R.id.progressBar4);


        switch (transStatus) {
            case "success":


                progress.setVisibility(View.VISIBLE);

                CookieManager cookieManager = new CookieManager(new PersistentCookieStore(StatusActivity.this), CookiePolicy.ACCEPT_ALL);

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

                Call<String> call = cr.success();

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        image.setImageResource(R.drawable.success);
                        text.setText("Order successfully placed.");

                        //Toast.makeText(StatusActivity.this , response.body() , Toast.LENGTH_SHORT).show();

                        Log.d("response" , response.body());

                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Log.d("failure", t.toString());
                    }
                });


                break;
            case "declined":
                text.setText("Payment has been declined by your bank");
                image.setImageResource(R.drawable.failure);
                break;
            default:
                text.setText("Transaction has been cancelled");
                image.setImageResource(R.drawable.failure);
                break;
        }




        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });



    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatusActivity.this , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
