package com.annieshub.stores;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.annieshub.stores.categoryPOJO.Model;
import com.annieshub.stores.categoryPOJO.categoryBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category extends Fragment {

    ProgressBar progress;
    ViewPager pager;
    CircleIndicator indicator;
    RecyclerView grid;
    GridLayoutManager manager;
    List<Model> list;
    CategoryAdapter adapter;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_layout, container, false);

        list = new ArrayList<>();

        toolbar = ((MainActivity)getActivity()).findViewById(R.id.toolbar);

        progress = view.findViewById(R.id.progress);
        pager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.circle);
        grid = view.findViewById(R.id.grid);
        manager = new GridLayoutManager(getContext() , 1);

        adapter = new CategoryAdapter(getContext() , list);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        BannerAdapter adap = new BannerAdapter(getChildFragmentManager());
        pager.setAdapter(adap);
        indicator.setViewPager(pager);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar.setTitle("Category");

        progress.setVisibility(View.VISIBLE);
        final bean b = (bean) getContext().getApplicationContext();

        Call<categoryBean> call = b.getRetrofit().getCategory("menu");

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

            final Model item = list.get(position);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImageUrl().toString() , holder.image , options);

            Log.d("image" , item.getImageUrl().toString());

            holder.title.setText(item.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();

                    FragmentTransaction ft = fm.beginTransaction();

                    SubCategory frag = new SubCategory();

                    frag.setData(item.getChildren() , item.getChild() , item.getName());

                    ft.replace(R.id.replace , frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    ft.addToBackStack(null);
                    ft.commit();


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
            TextView title;

            public ViewHolder(View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.view);
                title = itemView.findViewById(R.id.textView2);

            }
        }
    }

    class BannerAdapter extends FragmentStatePagerAdapter
    {

        int bannersp[] = new int[]{
                R.drawable.slider2_1,
                R.drawable.slider3_1
        };

        public BannerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            page frag = new page();
            Bundle b = new Bundle();
            b.putInt("id" , bannersp[position]);
            frag.setArguments(b);
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class page extends Fragment
    {

        int id;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.page_layout , container , false);

            Bundle b = getArguments();
            id = b.getInt("id");

            ImageView image = view.findViewById(R.id.image);

            image.setImageResource(id);

            return view;

        }
    }

}
