package com.yrl.stores;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.yrl.stores.addCartPOJO.addCartBean;
import com.yrl.stores.cartDeletePOJO.cartRemoveBean;
import com.yrl.stores.wishlistPOJO.Product;
import com.yrl.stores.wishlistPOJO.wishlostbean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

public class Wishlist extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;
    GridLayoutManager manager;
    ProgressBar progress;
    WishAdapter adapter;
    List<Product> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar2);
        grid = findViewById(R.id.recyclerView2);
        progress = findViewById(R.id.progressBar);

        manager = new GridLayoutManager(this , 1);
        adapter = new WishAdapter(this , list);

        grid.setLayoutManager(manager);
        grid.setAdapter(adapter);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Wishlist");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        loadData();


    }


    public void loadData()
    {

        progress.setVisibility(View.VISIBLE);


        final bean b = (bean) getApplicationContext();

        Call<wishlostbean> call = b.getRetrofit().gedtWishlst();

        call.enqueue(new Callback<wishlostbean>() {
            @Override
            public void onResponse(Call<wishlostbean> call, Response<wishlostbean> response) {

                if (response.body().getCode() == 0)
                {
                    adapter.setGridData(response.body().getModel().getProducts());
                }


                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<wishlostbean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder>
    {
        Context context;
        List<Product> list = new ArrayList<>();

        public WishAdapter(Context context , List<Product> list)
        {
            this.context = context;
            this.list = list;
        }


        public void setGridData(List<Product> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.wish_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            final Product item = list.get(position);

            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String qt = holder.quantity.getText().toString();
                    int q = Integer.parseInt(qt);

                    q++;

                    holder.quantity.setText(String.valueOf(q));

                }
            });


            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String qt = holder.quantity.getText().toString();
                    int q = Integer.parseInt(qt);

                    if (q > 1)
                    {
                        q--;
                    }
                    holder.quantity.setText(String.valueOf(q));

                }
            });


            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImageUrl() , holder.image , options);

            holder.title.setText(item.getName());

            holder.price.setText(String.valueOf(item.getRegularPriceWithTax()));

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress.setVisibility(View.VISIBLE);


                    final bean b = (bean) getApplicationContext();

                    Call<cartRemoveBean> call = b.getRetrofit().removeCart(item.getWishlistId());

                    call.enqueue(new Callback<cartRemoveBean>() {
                        @Override
                        public void onResponse(Call<cartRemoveBean> call, Response<cartRemoveBean> response) {

                            loadData();

                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<cartRemoveBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
            });



            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    progress.setVisibility(View.VISIBLE);
                    final bean b1 = (bean) context.getApplicationContext();


                    Call<addCartBean> call = b1.getRetrofit().addToCart(item.getEntityId() , holder.quantity.getText().toString());

                    call.enqueue(new Callback<addCartBean>() {
                        @Override
                        public void onResponse(Call<addCartBean> call, Response<addCartBean> response) {


                            if (response.body().getCode() == 0)
                            {

                                Toast.makeText(context , "Added successfully" , Toast.LENGTH_SHORT).show();

                                SharedPreferences pref = context.getSharedPreferences("pref" , Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = pref.edit();

                                edit.putString("count" , response.body().getModel().getItemsQty().toString());
                                edit.apply();



                            }
                            else
                            {

                                Toast.makeText(context , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();

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



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            ImageView image;
            TextView title , price , quantity;
            ImageButton plus , minus , delete;
            Button add;


            public ViewHolder(View itemView) {
                super(itemView);

                add = itemView.findViewById(R.id.button9);
                image = itemView.findViewById(R.id.imageView6);
                title = itemView.findViewById(R.id.textView17);
                price = itemView.findViewById(R.id.textView18);
                quantity = itemView.findViewById(R.id.editText9);
                plus = itemView.findViewById(R.id.imageButton3);
                minus = itemView.findViewById(R.id.imageButton4);
                delete = itemView.findViewById(R.id.imageButton2);

            }
        }

    }

}
