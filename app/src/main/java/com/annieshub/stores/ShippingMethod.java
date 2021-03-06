package com.annieshub.stores;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.annieshub.stores.shippingMethodListPOJO.shippingMethodListBean;
import com.annieshub.stores.shippingMethodPOJO.shippingMethodBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        toolbar.setTitle("Free Delivery");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        progress.setVisibility(View.VISIBLE);


        final bean b = (bean) getApplicationContext();

        final Call<shippingMethodListBean> call = b.getRetrofit().getShippingMethods();

        call.enqueue(new Callback<shippingMethodListBean>() {
            @Override
            public void onResponse(Call<shippingMethodListBean> call, Response<shippingMethodListBean> response) {

                for (int i = 0 ; i < response.body().getModel().getFlatRate().size() ; i++)
                {
                    RadioButton rb = new RadioButton(ShippingMethod.this);
                    rb.setText(response.body().getModel().getFlatRate().get(i).getMethodTitle() + " \u20b9 " + response.body().getModel().getFlatRate().get(i).getPrice());
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


                    final bean b = (bean) getApplicationContext();

                    Call<shippingMethodBean> call1 = b.getRetrofit().setShippingMethod("flatrate_flatrate");

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
