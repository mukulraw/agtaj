package com.yrl.stores;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                final bean b = (bean) getApplicationContext();
                progress.setVisibility(View.VISIBLE);







                Call<String> call2 = b.getRetrofit().createOrder();

                call2.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        Log.d("asdasdsad" , response.body());
                        //Toast.makeText(PaymentInfo.this , response.body() , Toast.LENGTH_SHORT).show();


                        progress.setVisibility(View.VISIBLE);



                        Call<formBean> call1 = b.getRetrofit().getFormKey();


                        call1.enqueue(new Callback<formBean>() {
                            @Override
                            public void onResponse(Call<formBean> call, Response<formBean> response) {


                                        /*Intent intent = new Intent(PaymentInfo.this , OrderSummary.class);
                                        startActivity(intent);*/

                                if (response.body().getCode() == 0) {

                                    Call<String> call2 = b.getRetrofit().success();

                                    call2.enqueue(new Callback<String>() {
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

                                }



                            }

                            @Override
                            public void onFailure(Call<formBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progress.setVisibility(View.GONE);
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
