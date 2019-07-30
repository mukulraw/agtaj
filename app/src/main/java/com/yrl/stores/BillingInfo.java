package com.yrl.stores;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.yrl.stores.addressListPOJO.Model;
import com.yrl.stores.addressListPOJO.addressListBean;
import com.yrl.stores.billingPOJO.billingBean;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BillingInfo extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    Spinner spinner;
    EditText first, last, address, city, state, zip, phone;
    CheckBox ship;
    Button next;
    String price;

    List<Model> list;
    List<String> ll;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_info);

        price = getIntent().getStringExtra("price");

        toolbar = findViewById(R.id.toolbar3);
        spinner = findViewById(R.id.spinner);
        first = findViewById(R.id.editText3);
        last = findViewById(R.id.editText4);

        address = findViewById(R.id.editText6);
        city = findViewById(R.id.editText7);
        state = findViewById(R.id.editText8);
        zip = findViewById(R.id.editText10);
        phone = findViewById(R.id.editText12);
        ship = findViewById(R.id.checkBox);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Billing Info");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        next = findViewById(R.id.button10);
        progress = findViewById(R.id.progress);


        list = new ArrayList<>();
        ll = new ArrayList<>();




        progress.setVisibility(View.VISIBLE);

        final bean b = (bean) getApplicationContext();


        Call<addressListBean> call = b.getRetrofit().getAddressList();


        call.enqueue(new Callback<addressListBean>() {
            @Override
            public void onResponse(Call<addressListBean> call, Response<addressListBean> response) {

                list = response.body().getModel();


                ll.add("New Address");


                for (int i = 0 ; i < response.body().getModel().size() ; i++)
                {
                    ll.add(response.body().getModel().get(i).getName());
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BillingInfo.this , R.layout.spinner_item_layout, ll);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(dataAdapter);

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<addressListBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0)
                {
                    count = position;

                    Model item = list.get(position - 1);

                    first.setText(item.getFirstname());
                    last.setText(item.getLastname());

                    address.setText(item.getStreet().get(0));
                    city.setText(item.getCity());
                    state.setText(item.getRegion());
                    zip.setText(item.getPostcode());
                    phone.setText(item.getTelephone());

                }
                else
                {
                    count = position;

                    first.setText("");
                    last.setText("");

                    address.setText("");
                    city.setText("");
                    state.setText("");
                    zip.setText("");
                    phone.setText("");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String f = first.getText().toString();
                final String l = last.getText().toString();

                final String a = address.getText().toString();
                final String ci = city.getText().toString();
                final String s = state.getText().toString();
                final String z = zip.getText().toString();
                final String p = phone.getText().toString();


                if (f.length() > 0) {
                    if (l.length() > 0) {


                        if (a.length() > 0) {

                            if (ci.length() > 0) {

                                if (s.length() > 0) {

                                    if (z.length() > 0) {

                                        if (p.length() == 10) {


                                            SharePreferenceUtils.getInstance().saveString("phone" , p);
                                            SharePreferenceUtils.getInstance().saveString("name" , f);


                                            if (ship.isChecked()) {


                                                progress.setVisibility(View.VISIBLE);


                                                final bean b = (bean) getApplicationContext();

                                                Call<billingBean> call = b.getRetrofit().setBilling(
                                                        "",
                                                        "",
                                                        f,
                                                        l,
                                                        "",
                                                        a,
                                                        "",
                                                        ci,
                                                        "",
                                                        s,
                                                        "IN",
                                                        p,
                                                        "",
                                                        "1",
                                                        z
                                                );

                                                call.enqueue(new Callback<billingBean>() {
                                                    @Override
                                                    public void onResponse(Call<billingBean> call, Response<billingBean> response) {


                                                        if (response.body().getCode() == 0) {


                                                            if (count > 0)
                                                            {
                                                                Intent intent = new Intent(BillingInfo.this, ShippingMethod.class);
                                                                intent.putExtra("price", price);
                                                                startActivity(intent);
                                                            }
                                                            else
                                                            {
                                                                Call<createAddressbean> call1 = b.getRetrofit().createAddress(
                                                                        "billing,shipping",
                                                                        l,
                                                                        f,
                                                                        p,
                                                                        "",
                                                                        "",
                                                                        z,
                                                                        ci,
                                                                        a,
                                                                        "",
                                                                        "IN",
                                                                        s
                                                                );

                                                                call1.enqueue(new Callback<createAddressbean>() {
                                                                    @Override
                                                                    public void onResponse(Call<createAddressbean> call, Response<createAddressbean> response) {

                                                                        if (response.body().getCode() == 0)
                                                                        {
                                                                            Intent intent = new Intent(BillingInfo.this, ShippingMethod.class);
                                                                            intent.putExtra("price", price);
                                                                            startActivity(intent);
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<createAddressbean> call, Throwable t) {

                                                                    }
                                                                });
                                                            }




                                                        }


                                                        progress.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<billingBean> call, Throwable t) {
                                                        progress.setVisibility(View.GONE);
                                                    }
                                                });


                                            } else {


                                                progress.setVisibility(View.VISIBLE);


                                                final bean b = (bean) getApplicationContext();

                                                Call<billingBean> call = b.getRetrofit().setBilling(
                                                        "",
                                                        "",
                                                        f,
                                                        l,
                                                        "",
                                                        a,
                                                        "",
                                                        ci,
                                                        "",
                                                        s,
                                                        "IN",
                                                        p,
                                                        "",
                                                        "0",
                                                        z
                                                );

                                                call.enqueue(new Callback<billingBean>() {
                                                    @Override
                                                    public void onResponse(Call<billingBean> call, Response<billingBean> response) {

                                                        if (response.body().getCode() == 0) {


                                                            if (count > 0)
                                                            {

                                                                Intent intent = new Intent(BillingInfo.this, ShippingInfo.class);
                                                                intent.putExtra("price", price);
                                                                startActivity(intent);

                                                            }
                                                            else
                                                            {
                                                                Call<createAddressbean> call1 = b.getRetrofit().createAddress(
                                                                        "billing,shipping",
                                                                        l,
                                                                        f,
                                                                        p,
                                                                        "",
                                                                        "",
                                                                        z,
                                                                        ci,
                                                                        a,
                                                                        "",
                                                                        "IN",
                                                                        s
                                                                );

                                                                call1.enqueue(new Callback<createAddressbean>() {
                                                                    @Override
                                                                    public void onResponse(Call<createAddressbean> call, Response<createAddressbean> response) {

                                                                        if (response.body().getCode() == 0)
                                                                        {
                                                                            Intent intent = new Intent(BillingInfo.this, ShippingInfo.class);
                                                                            intent.putExtra("price", price);
                                                                            startActivity(intent);
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<createAddressbean> call, Throwable t) {

                                                                    }
                                                                });
                                                            }






                                                        }


                                                        progress.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<billingBean> call, Throwable t) {
                                                        progress.setVisibility(View.GONE);
                                                    }
                                                });


                                            }


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
