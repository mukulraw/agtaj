package com.agtajhotel.agtajhotel;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.agtajhotel.agtajhotel.addCartPOJO.addCartBean;
import com.agtajhotel.agtajhotel.cartDeletePOJO.cartRemoveBean;
import com.agtajhotel.agtajhotel.cartPOJO.cartBean;
import com.agtajhotel.agtajhotel.wishlistPOJO.Product;
import com.agtajhotel.agtajhotel.wishlistPOJO.wishlostbean;
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



    }


    @Override
    protected void onResume() {
        super.onResume();

        loadData();


    }


    public void loadData()
    {

        progress.setVisibility(View.VISIBLE);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(Wishlist.this), CookiePolicy.ACCEPT_ALL);

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

        Call<wishlostbean> call = cr.gedtWishlst();

        call.enqueue(new Callback<wishlostbean>() {
            @Override
            public void onResponse(Call<wishlostbean> call, Response<wishlostbean> response) {

                adapter.setGridData(response.body().getModel().getProducts());

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

                    CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);

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

                    Call<cartRemoveBean> call = cr.removeCart(item.getWishlistId());

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

                    CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);

                    CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(cookieJar);
                    OkHttpClient client = builder.build();


                    progress.setVisibility(View.VISIBLE);
                    final bean b1 = (bean) context.getApplicationContext();
                    final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b1.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                    final AllAPIs cr = retrofit.create(AllAPIs.class);


                    Call<addCartBean> call = cr.addToCart(item.getEntityId() , holder.quantity.getText().toString());

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
