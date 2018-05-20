package com.agtajhotel.agtajhotel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agtajhotel.agtajhotel.cartPOJO.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    Toolbar toolbar;
    ProgressBar progress;
    RecyclerView grid;
    Button checkout;
    TextView total;
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.toolbar);
        progress = findViewById(R.id.progress);
        grid = findViewById(R.id.recyclerView);
        checkout = findViewById(R.id.button8);
        total = findViewById(R.id.textView15);

        manager = new GridLayoutManager(this , 1);





    }

    class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
    {
        Context context;
        List<Model> list = new ArrayList<>();


        public CartAdapter(Context context , List<Model> list)
        {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
