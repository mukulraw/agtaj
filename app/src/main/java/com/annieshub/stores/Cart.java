package com.annieshub.stores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.annieshub.stores.cartDeletePOJO.cartRemoveBean;
import com.annieshub.stores.cartPOJO.CartItem;
import com.annieshub.stores.cartPOJO.cartBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    RecyclerView grid;
    Button checkout;
    TextView total;
    GridLayoutManager manager;
    List<CartItem> list;
    CartAdapter adapter;
    float count = 0;
    TextView totalAmount , tax;

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
        totalAmount = findViewById(R.id.textView37);
        tax = findViewById(R.id.textView38);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Cart");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


        manager = new GridLayoutManager(this , 1);
        adapter = new CartAdapter(this , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float q = Float.parseFloat(total.getText().toString());

                if (q > 0)
                {


                    Intent intent = new Intent(Cart.this , BillingInfo.class);
                    intent.putExtra("price" , total.getText().toString());
                    startActivity(intent);


                }
                else
                {
                    Toast.makeText(Cart.this , "Invalid amount" , Toast.LENGTH_SHORT).show();
                }

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

        count = 0;

        progress.setVisibility(View.VISIBLE);


        final bean b = (bean) getApplicationContext();

        Call<cartBean> call = b.getRetrofit().getCart();

        call.enqueue(new Callback<cartBean>() {
            @Override
            public void onResponse(Call<cartBean> call, Response<cartBean> response) {

                if (response.body().getCode() == 0)
                {
                    if (response.body().getModel().getCartItems().size() > 0)
                    {
                        adapter.setGridData(response.body().getModel().getCartItems());
                    }
                    else
                    {
                        adapter.setGridData(response.body().getModel().getCartItems());
                        total.setText("0.00");
                    }


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

        //total.setText(String.valueOf(adapter.getCount()));
    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
    {
        Context context;
        List<CartItem> list = new ArrayList<>();
        Cart car;


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



        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            car = (Cart)context;
            View view = inflater.inflate(R.layout.cart_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            final CartItem item = list.get(position);


            car.count = car.count + (item.getItemPrice() * item.getQty());


            car.totalAmount.setText("Sub Total Rs. " + String.valueOf(car.count));


            float tt = car.count;

            float ta = (0 * tt) / 100;

            Log.d("tax" , String.valueOf(tt));

            car.tax.setText("GST Rs. " + String.valueOf(ta));

            car.total.setText(String.valueOf(car.count + ta));


            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getThumbnailPicUrl() , holder.image , options);

            holder.title.setText(item.getItemTitle());

            holder.price.setText(String.valueOf(item.getItemPrice()));

            holder.quantity.setText(String.valueOf(item.getQty()));



            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress.setVisibility(View.VISIBLE);


                    final bean b = (bean) getApplicationContext();

                    int f = Integer.parseInt(holder.quantity.getText().toString());

                    Call<cartBean> call = b.getRetrofit().updateCart(item.getCartItemId() , String.valueOf(f + 1));

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


                        final bean b = (bean) getApplicationContext();

                        int f = Integer.parseInt(holder.quantity.getText().toString());

                        Call<cartBean> call = b.getRetrofit().updateCart(item.getCartItemId() , String.valueOf(f - 1));

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

                    final bean b = (bean) getApplicationContext();

                    Call<cartRemoveBean> call = b.getRetrofit().removeCart(item.getCartItemId());

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
