package com.agtajhotel.agtajhotel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agtajhotel.agtajhotel.addCartPOJO.addCartBean;
import com.agtajhotel.agtajhotel.addWishlistPOJO.addWishlistbean;
import com.agtajhotel.agtajhotel.singleProductPOJO.singleProductBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

public class SingleProduct extends Fragment {

    TextView price;
    Button add , addWishlist , share;
    ImageButton plus , minus;
    TextView quantity;
    ImageView image;
    TextView title , code , availability , desc;
    ProgressBar progress;
    String id;
    String tit;
    Toolbar toolbar;

    String shareLink = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_product_layout , container , false);

        Bundle b = getArguments();

        id = b.getString("id");
        tit = b.getString("name");

        toolbar = ((MainActivity)getActivity()).findViewById(R.id.toolbar);

        toolbar.setTitle(tit);

        price = view.findViewById(R.id.textView7);
        add = view.findViewById(R.id.button5);
        addWishlist = view.findViewById(R.id.button6);
        share = view.findViewById(R.id.button7);
        plus = view.findViewById(R.id.imageButton3);
        minus = view.findViewById(R.id.imageButton4);
        quantity = view.findViewById(R.id.editText9);
        image = view.findViewById(R.id.view2);
        title = view.findViewById(R.id.textView6);
        code = view.findViewById(R.id.textView10);
        availability = view.findViewById(R.id.textView12);
        desc = view.findViewById(R.id.textView14);
        progress = view.findViewById(R.id.progress);



        progress.setVisibility(View.VISIBLE);
        final bean b1 = (bean) getContext().getApplicationContext();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b1.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllAPIs cr = retrofit.create(AllAPIs.class);

        Call<singleProductBean> call = cr.getProductData(id);

        call.enqueue(new Callback<singleProductBean>() {
            @Override
            public void onResponse(Call<singleProductBean> call, Response<singleProductBean> response) {


                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(response.body().getModel().getImageUrl() , image , options);
                price.setText(response.body().getModel().getSymbol() + " " + response.body().getModel().getPrice());

                shareLink = response.body().getModel().getUrlKey();
                title.setText(response.body().getModel().getName());
                code.setText(response.body().getModel().getSku());
                availability.setText(String.valueOf(response.body().getModel().getStockLevel()));
                desc.setText(response.body().getModel().getDescription());


                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<singleProductBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qt = quantity.getText().toString();
                int q = Integer.parseInt(qt);

                q++;

                quantity.setText(String.valueOf(q));

            }
        });


        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qt = quantity.getText().toString();
                int q = Integer.parseInt(qt);

                if (q > 1)
                {
                    q--;
                }
                quantity.setText(String.valueOf(q));

            }
        });



        addWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CookieManager cookieManager = new CookieManager(new PersistentCookieStore(getContext()), CookiePolicy.ACCEPT_ALL);

                CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.cookieJar(cookieJar);
                OkHttpClient client = builder.build();


                progress.setVisibility(View.VISIBLE);
                final bean b1 = (bean) getContext().getApplicationContext();
                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b1.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                final AllAPIs cr = retrofit.create(AllAPIs.class);


                Call<addWishlistbean> call = cr.addWishlist(id);

                call.enqueue(new Callback<addWishlistbean>() {
                    @Override
                    public void onResponse(Call<addWishlistbean> call, Response<addWishlistbean> response) {


                        if (response.body().getCode() == 0)
                        {

                            Toast.makeText(getContext() , response.body().getMsg() , Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                            Toast.makeText(getContext() , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();

                        }


                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<addWishlistbean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CookieManager cookieManager = new CookieManager(new PersistentCookieStore(getContext()), CookiePolicy.ACCEPT_ALL);

                CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.cookieJar(cookieJar);
                OkHttpClient client = builder.build();


                progress.setVisibility(View.VISIBLE);
                final bean b1 = (bean) getContext().getApplicationContext();
                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b1.BASE_URL)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
                final AllAPIs cr = retrofit.create(AllAPIs.class);


                Call<addCartBean> call = cr.addToCart(id , quantity.getText().toString());

                call.enqueue(new Callback<addCartBean>() {
                    @Override
                    public void onResponse(Call<addCartBean> call, Response<addCartBean> response) {


                        if (response.body().getCode() == 0)
                        {

                            Toast.makeText(getContext() , "Added successfully" , Toast.LENGTH_SHORT).show();

                            SharedPreferences pref = getActivity().getSharedPreferences("pref" , Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();

                            edit.putString("count" , response.body().getModel().getItemsQty().toString());
                            edit.apply();

                            MainActivity act = ((MainActivity)getActivity());

                            act.setCount();

                        }
                        else
                        {

                            Toast.makeText(getContext() , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();

                        }


                        progress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onFailure(Call<addCartBean> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                    }
                });

            }
        });

        return view;
    }



}
