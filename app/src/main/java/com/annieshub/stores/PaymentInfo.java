package com.annieshub.stores;

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

import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.annieshub.stores.shippingMethodPOJO.shippingMethodBean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInfo extends AppCompatActivity {

    Toolbar toolbar;
    RadioButton radio;
    Button next;
    ProgressBar progress;

    String price;

    RadioGroup group;

    String method = "";

    SharedPreferences pref;

    String e, p, n;


    String TAG = "asasdsad";

    String merchantkey = "Hu6bqW29", txnid, prodname = "YRL";
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        e = pref.getString("email", "");
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
                                    PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();


                                    String txnId = System.currentTimeMillis() + "";
                                    //String txnId = "TXNID720431525261327973";
                                    String udf1 = "";
                                    String udf2 = "";
                                    String udf3 = "";
                                    String udf4 = "";
                                    String udf5 = "";
                                    String udf6 = "";
                                    String udf7 = "";
                                    String udf8 = "";
                                    String udf9 = "";
                                    String udf10 = "";

                                    builder.setAmount(price)
                                            .setTxnId(txnId)
                                            .setPhone(p)
                                            .setProductName(prodname)
                                            .setFirstName(n)
                                            .setEmail(e)
                                            .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")
                                            .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")
                                            .setUdf1(udf1)
                                            .setUdf2(udf2)
                                            .setUdf3(udf3)
                                            .setUdf4(udf4)
                                            .setUdf5(udf5)
                                            .setUdf6(udf6)
                                            .setUdf7(udf7)
                                            .setUdf8(udf8)
                                            .setUdf9(udf9)
                                            .setUdf10(udf10)
                                            .setIsDebug(false)
                                            .setKey(merchantkey)
                                            .setMerchantId("6744028");

                                    try {
                                        mPaymentParams = builder.build();

                                        /*
                                         * Hash should always be generated from your server side.
                                         * */
                                        //    generateHashFromServer(mPaymentParams);

                                        /*            *//**
                                         * Do not use below code when going live
                                         * Below code is provided to generate hash from sdk.
                                         * It is recommended to generate hash from server side only.
                                         * */
                                        mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);


                                        PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentInfo.this, R.style.AppTheme_default, true);


                                    } catch (Exception e) {
                                        // some exception occurred
                                        Toast.makeText(PaymentInfo.this, e.getMessage(), Toast.LENGTH_LONG).show();

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

    /*public void getHashkey(){

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
    }*/

    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        stringBuilder.append("wh1W0gUEUO");

        String hash = hashCal(stringBuilder.toString());

        Log.d("server hash", hash);

        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);





        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                    intent.putExtra("transStatus", "success");
                    startActivity(intent);
                    Log.d("status", "success");

                } else {
                    Intent intent = new Intent(getApplicationContext(), StatusActivity.class);
                    intent.putExtra("transStatus", "declined");
                    startActivity(intent);
                    Log.d("status", "failure");
                    //Failure Transaction
                }
                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();
                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
                Log.e(TAG, "tran " + payuResponse + "---" + merchantResponse);
            } /* else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }*/
        }
    }


}
