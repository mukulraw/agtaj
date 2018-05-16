package com.agtajhotel.agtajhotel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agtajhotel.agtajhotel.categoryPOJO.Model;
import com.agtajhotel.agtajhotel.categoryPOJO.categoryBean;
import com.agtajhotel.agtajhotel.customerPOJO.customerBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Category extends Fragment {

    ProgressBar progress;
    ViewPager pager;
    CircleIndicator indicator;
    RecyclerView grid;
    GridLayoutManager manager;
    List<Model> list;
    CategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_layout, container, false);

        list = new ArrayList<>();

        progress = view.findViewById(R.id.progress);
        pager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.circle);
        grid = view.findViewById(R.id.grid);
        manager = new GridLayoutManager(getContext() , 2);

        adapter = new CategoryAdapter(getContext() , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        progress.setVisibility(View.VISIBLE);
        final bean b = (bean) getContext().getApplicationContext();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final AllAPIs cr = retrofit.create(AllAPIs.class);

        Call<categoryBean> call = cr.getCategory("menu");

        call.enqueue(new Callback<categoryBean>() {
            @Override
            public void onResponse(Call<categoryBean> call, Response<categoryBean> response) {

                adapter.setGridData(response.body().getModel());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<categoryBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
    {

        Context context;
        List<Model> list = new ArrayList<>();


        public CategoryAdapter(Context context , List<Model> list)
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.category_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Model item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImageUrl().toString() , holder.image , options);

            holder.title.setText(item.getName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            RoundedImageView image;
            TextView title;

            public ViewHolder(View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.view);
                title = itemView.findViewById(R.id.textView2);

            }
        }
    }

}
