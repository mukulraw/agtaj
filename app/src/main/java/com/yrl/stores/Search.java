package com.yrl.stores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yrl.stores.searchPOJO.Item;
import com.yrl.stores.searchPOJO.searchbean;

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

public class Search extends AppCompatActivity {

    EditText search;
    RecyclerView grid;
    ProgressBar progress;
    List<Item> list;
    GridLayoutManager manager;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.editText14);
        grid = findViewById(R.id.recyclerView3);
        progress = findViewById(R.id.progressBar7);

        list = new ArrayList<>();
        manager = new GridLayoutManager(this , 1);
        adapter = new SearchAdapter(this , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length() > 0)
                {

                    progress.setVisibility(View.VISIBLE);

                    final bean b = (bean) getApplicationContext();

                    Call<searchbean> call = b.getRetrofit().search(s.toString() , "1" , "20");

                    call.enqueue(new Callback<searchbean>() {
                        @Override
                        public void onResponse(Call<searchbean> call, Response<searchbean> response) {

                            if (response.body().getCode() == 0)
                            {
                                adapter.setGridData(response.body().getModel().getItems());
                            }


                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<searchbean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    List<Item> ll = new ArrayList<>();
                    adapter.setGridData(ll);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("id", "");
        resultIntent.putExtra("name", "");
        setResult(Activity.RESULT_CANCELED, resultIntent);
        finish();

    }

    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>
    {

        Context context;
        List<Item> list = new ArrayList<>();

        public SearchAdapter(Context context , List<Item> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setGridData(List<Item> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.search_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Item item = list.get(position);

            holder.sear.setText(item.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Search s = (Search)context;

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("id", item.getEntityId());
                    resultIntent.putExtra("name", item.getName());
                    s.setResult(Activity.RESULT_OK, resultIntent);
                    s.finish();


                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView sear;

            public ViewHolder(View itemView) {
                super(itemView);
                sear = itemView.findViewById(R.id.textView29);
            }
        }
    }

}
