package com.yrl.stores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.yrl.stores.shippingMethodPOJO.shippingMethodBean;

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

    SharedPreferences pref;

    String e , p , n;

    PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
    //declare paymentParam object
    PayUmoneySdkInitializer.PaymentParam paymentParam = null;

    String TAG = "asasdsad";

    String merchantkey = "Hu6bqW29", txnid, prodname = "YRL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);

        e = pref.getString("email" , "");
        p = SharePreferenceUtils.getInstance().getString("phone");
        n = SharePreferenceUtils.getInstance().getString("name");

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


                    final bean b = (bean) getApplicationContext();

                    Call<shippingMethodBean> call;

                    if (iidd == R.id.radioButton) {
                        call = b.getRetrofit().setPaymentMethod("pumcp");
                        method = "online";
                    } else {
                        //call = cr.setPaymentMethod("cashondelivery");
                        call = b.getRetrofit().setPaymentMethod("cashondelivery");
                        method = "cod";
                    }


                    call.enqueue(new Callback<shippingMethodBean>() {
                        @Override
                        public void onResponse(Call<shippingMethodBean> call, Response<shippingMethodBean> response) {



                            if (response.body().getCode() == 0) {





                                if (method.equals("online")) {

                                    //txnid = String.valueOf(System.currentTimeMillis());
                                    txnid = "2";

                                    builder.setAmount(price)                          // Payment amount
                                            .setTxnId(txnid)                     // Transaction ID
                                            .setPhone(p)                   // User Phone number
                                            .setProductName(prodname)                   // Product Name or description
                                            .setFirstName(n)                              // User First name
                                            .setEmail(e)              // User Email ID
                                            .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")     // Success URL (surl)
                                            .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")     //Failure URL (furl)
                                            .setUdf1("")
                                            .setUdf2("")
                                            .setUdf3("")
                                            .setUdf4("")
                                            .setUdf5("")
                                            .setUdf6("")
                                            .setUdf7("")
                                            .setUdf8("")
                                            .setUdf9("")
                                            .setUdf10("")
                                            .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                                            .setKey(merchantkey)                        // Merchant key
                                            .setMerchantId("6744028");
                                    try {
                                        paymentParam = builder.build();
                                        // generateHashFromServer(paymentParam );
                                        getHashkey();
                                    } catch (Exception e) {

                                        Log.e(TAG, " error s "+e.toString());
                                    }

                                } else {


                                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                                    intent.putExtra("transStatus", "success");
                                    startActivity(intent);


                                }













                            }




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

    public void getHashkey(){

        progress.setVisibility(View.VISIBLE);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://yrlstores.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AllAPIs cr = retrofit.create(AllAPIs.class);

        Call<String> call = cr.hash(merchantkey, txnid, price, prodname,
                n, e);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "hash res "+response.body());
                String merchantHash= response.body();
                if (merchantHash.isEmpty() || merchantHash.equals("")) {
                    Toast.makeText(PaymentInfo.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "hash empty");
                } else {
                    // mPaymentParams.setMerchantHash(merchantHash);
                    paymentParam.setMerchantHash(merchantHash);
                    // Invoke the following function to open the checkout page.
                    // PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, StartPaymentActivity.this,-1, true);
                    PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, PaymentInfo.this, R.style.AppTheme_default, false);
                }

                progress.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "hash error "+ t.toString());
                progress.setVisibility(View.GONE);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("StartPaymentActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
                    //Success Transaction
                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                    intent.putExtra("transStatus", "success");
                    startActivity(intent);
                    Log.d("status" , "success");

                } else{
                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                    intent.putExtra("transStatus", "declined");
                    startActivity(intent);
                    Log.d("status" , "failure");
                    //Failure Transaction
                }
                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();
                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
                Log.e(TAG, "tran "+payuResponse+"---"+ merchantResponse);
            } /* else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }*/
        }
    }


}
