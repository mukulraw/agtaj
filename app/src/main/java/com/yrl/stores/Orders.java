package com.yrl.stores;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yrl.stores.ordersPOJO.Model;
import com.yrl.stores.ordersPOJO.ordersBean;

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

public class Orders extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;
    ProgressBar progress;
    GridLayoutManager manager;
    List<Model> list;
    OrdersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar6);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progressBar5);
        manager = new GridLayoutManager(this , 1);
        adapter = new OrdersAdapter(this , list);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("My Orders");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });


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

        final bean b = (bean) getApplicationContext();


        Call<ordersBean> call = b.getRetrofit().getOrders();

        call.enqueue(new Callback<ordersBean>() {
            @Override
            public void onResponse(Call<ordersBean> call, Response<ordersBean> response) {

                if (response.body().getCode() == 0)
                {
                    adapter.setGridData(response.body().getModel());
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ordersBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>
    {
        List<Model> list = new ArrayList<>();
        Context context;

        public OrdersAdapter(Context context , List<Model> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<Model> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.order_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Model item = list.get(position);

            holder.orderid.setText(item.getOrderId());
            holder.date.setText(item.getCreatedAt());
            holder.name.setText(item.getShippingAddressName());
            holder.total.setText("Rs. " + item.getGrandTotal());
            holder.status.setText(item.getStatus());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView orderid , date , name , total , status;

            public ViewHolder(View itemView) {
                super(itemView);

                orderid = itemView.findViewById(R.id.textView20);
                date = itemView.findViewById(R.id.textView21);
                name = itemView.findViewById(R.id.textView23);
                total = itemView.findViewById(R.id.textView25);
                status = itemView.findViewById(R.id.textView26);

            }
        }
    }

}
