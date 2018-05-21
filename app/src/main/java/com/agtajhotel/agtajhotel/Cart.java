package com.agtajhotel.agtajhotel;

import android.content.Context;
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

import com.agtajhotel.agtajhotel.cartDeletePOJO.cartRemoveBean;
import com.agtajhotel.agtajhotel.cartPOJO.CartItem;
import com.agtajhotel.agtajhotel.cartPOJO.Model;
import com.agtajhotel.agtajhotel.cartPOJO.cartBean;
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

public class Cart extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    RecyclerView grid;
    Button checkout;
    TextView total;
    GridLayoutManager manager;
    List<CartItem> list;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        grid = findViewById(R.id.recyclerView);
        checkout = findViewById(R.id.button8);
        total = findViewById(R.id.textView15);

        manager = new GridLayoutManager(this , 1);
        adapter = new CartAdapter(this , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);




    }

    @Override
    protected void onResume() {
        super.onResume();


        loadData();


    }

    public void loadData()
    {
        progress.setVisibility(View.VISIBLE);

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(Cart.this), CookiePolicy.ACCEPT_ALL);

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

        Call<cartBean> call = cr.getCart();

        call.enqueue(new Callback<cartBean>() {
            @Override
            public void onResponse(Call<cartBean> call, Response<cartBean> response) {

                if (response.body().getCode() == 0)
                {
                    adapter.setGridData(response.body().getModel().getCartItems());
                }
                else
                {
                    adapter.setGridData(response.body().getModel().getCartItems());
                    Toast.makeText(Cart.this , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();
                }





                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<cartBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        total.setText(String.valueOf(adapter.getCount()));
    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
    {
        Context context;
        List<CartItem> list = new ArrayList<>();
        float count = 0;


        public CartAdapter(Context context , List<CartItem> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<CartItem> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        public float getCount()
        {
            return count;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cart_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final CartItem item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getThumbnailPicUrl() , holder.image , options);

            holder.title.setText(item.getItemTitle());

            holder.price.setText(String.valueOf(item.getItemPrice()));

            holder.quantity.setText(String.valueOf(item.getQty()));

            count = count + item.getItemPrice();

            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress.setVisibility(View.VISIBLE);

                    CookieManager cookieManager = new CookieManager(new PersistentCookieStore(Cart.this), CookiePolicy.ACCEPT_ALL);

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

                    Call<cartBean> call = cr.updateCart(item.getCartItemId() , String.valueOf(item.getQty() + 1));

                    call.enqueue(new Callback<cartBean>() {
                        @Override
                        public void onResponse(Call<cartBean> call, Response<cartBean> response) {


                            loadData();
                            progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<cartBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });


                }
            });


            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (item.getQty() > 1)
                    {
                        progress.setVisibility(View.VISIBLE);

                        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(Cart.this), CookiePolicy.ACCEPT_ALL);

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

                        Call<cartBean> call = cr.updateCart(item.getCartItemId() , String.valueOf(item.getQty() - 1));

                        call.enqueue(new Callback<cartBean>() {
                            @Override
                            public void onResponse(Call<cartBean> call, Response<cartBean> response) {


                                loadData();
                                progress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<cartBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);
                            }
                        });
                    }
                    else
                    {

                    }




                }
            });


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress.setVisibility(View.VISIBLE);

                    CookieManager cookieManager = new CookieManager(new PersistentCookieStore(Cart.this), CookiePolicy.ACCEPT_ALL);

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

                    Call<cartRemoveBean> call = cr.removeCart(item.getCartItemId());

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


            public ViewHolder(View itemView) {
                super(itemView);

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