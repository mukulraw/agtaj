package com.annieshub.stores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.annieshub.stores.addCartPOJO.addCartBean;
import com.annieshub.stores.addWishlistPOJO.addWishlistbean;
import com.annieshub.stores.singleProductPOJO.singleProductBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Call<singleProductBean> call = b1.getRetrofit().getProductData(id);

        call.enqueue(new Callback<singleProductBean>() {
            @Override
            public void onResponse(Call<singleProductBean> call, Response<singleProductBean> response) {


                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                try {
                    loader.displayImage(response.body().getModel().getMediaGallery().get(0) , image , options);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                price.setText(response.body().getModel().getSymbol() + " " + response.body().getModel().getPrice());

                shareLink = response.body().getModel().getUrlKey();
                title.setText(response.body().getModel().getName());
                code.setText(response.body().getModel().getSku());
                availability.setText(String.valueOf(response.body().getModel().getStockLevel()));
                desc.setText(Html.fromHtml(response.body().getModel().getDescription()));


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



                progress.setVisibility(View.VISIBLE);
                final bean b1 = (bean) getContext().getApplicationContext();


                Call<addWishlistbean> call = b1.getRetrofit().addWishlist(id);

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



                progress.setVisibility(View.VISIBLE);
                final bean b1 = (bean) getContext().getApplicationContext();


                Call<addCartBean> call = b1.getRetrofit().addToCart(id , quantity.getText().toString());

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
