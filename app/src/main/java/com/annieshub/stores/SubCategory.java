package com.annieshub.stores;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
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

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.annieshub.stores.addCartPOJO.addCartBean;
import com.annieshub.stores.productListPOJO.Model;
import com.annieshub.stores.productListPOJO.productListBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategory extends Fragment {

    TabLayout tabs;
    ViewPager pager;

    List<String> ids = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    Toolbar toolbar;

    String title;

    public void setData(List<String> ids , List<String> titles , String title)
    {
        this.ids = ids;
        this.titles = titles;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sub_cat_layout , container , false);

        toolbar = ((MainActivity)getActivity()).findViewById(R.id.toolbar);
        tabs = view.findViewById(R.id.tabLayout);
        pager = view.findViewById(R.id.pager);

        for (int i = 0 ; i < ids.size() ; i++)
        {
            tabs.addTab(tabs.newTab().setText(titles.get(i)));
        }

        TabAdapter adapter = new TabAdapter(getChildFragmentManager() , ids);
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        for (int i = 0 ; i < ids.size() ; i++)
        {
            tabs.getTabAt(i).setText(titles.get(i));
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar.setTitle(title);



    }

    class TabAdapter extends FragmentStatePagerAdapter
    {

        List<String> ids = new ArrayList<>();

        public TabAdapter(FragmentManager fm , List<String> ids) {
            super(fm);
            this.ids = ids;
        }

        @Override
        public Fragment getItem(int position) {

            page frag = new page();
            Bundle b = new Bundle();
            b.putString("id" , ids.get(position));
            frag.setArguments(b);
            return frag;

        }

        @Override
        public int getCount() {
            return ids.size();
        }
    }

    public static class page extends Fragment
    {
        String id;
        RecyclerView grid;
        ProgressBar progress;
        ProductAdapter adapter;
        List<Model> list;
        GridLayoutManager manager;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.sub_cat_page , container, false);

            list = new ArrayList<>();
            Bundle b = getArguments();
            id = b.getString("id");

            Log.d("id" , id);

            grid = view.findViewById(R.id.grid);
            progress = view.findViewById(R.id.progress);

            manager = new GridLayoutManager(getContext() , 1);

            adapter = new ProductAdapter(getContext() , list);

            grid.setAdapter(adapter);
            grid.setLayoutManager(manager);



            progress.setVisibility(View.VISIBLE);
            final bean b1 = (bean) getContext().getApplicationContext();

            Call<productListBean> call = b1.getRetrofit().getProductsList("catalog" , "1" , "200" , "entity_id" , "asc" , id);

            call.enqueue(new Callback<productListBean>() {
                @Override
                public void onResponse(Call<productListBean> call, Response<productListBean> response) {

                    adapter.setGridData(response.body().getModel());

                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<productListBean> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                }
            });





            return view;
        }


        class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
        {

            Context context;
            List<Model> list = new ArrayList<>();

            public ProductAdapter(Context context , List<Model> list)
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
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.product_list_model , parent , false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

                holder.setIsRecyclable(false);

                final Model item = list.get(position);

                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(item.getImageUrl() , holder.image , options);

                holder.title.setText(item.getName());
                holder.price.setText(item.getSymbol() + " " + item.getPrice());



                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String qt = holder.quantity.getText().toString();
                        int q = Integer.parseInt(qt);

                        q++;

                        holder.quantity.setText(String.valueOf(q));

                    }
                });


                holder.remove.setOnClickListener(new View.OnClickListener() {
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



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentManager fm = ((MainActivity)context).getSupportFragmentManager();

                        FragmentTransaction ft = fm.beginTransaction();

                        SingleProduct frag = new SingleProduct();

                        Bundle b = new Bundle();
                        b.putString("id" , item.getEntityId());
                        b.putString("name" , item.getName());

                        frag.setArguments(b);

                        ft.replace(R.id.replace , frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        ft.addToBackStack(null);
                        ft.commit();

                    }
                });



                holder.addCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d("id" , item.getEntityId());






                        progress.setVisibility(View.VISIBLE);
                        final bean b1 = (bean) getContext().getApplicationContext();


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

                                    MainActivity act = ((MainActivity)context);

                                    act.setCount();

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

                RoundedImageView image;
                TextView price , title , quantity;
                ImageButton add , remove;
                Button addCart;

                public ViewHolder(View itemView) {
                    super(itemView);

                    image = itemView.findViewById(R.id.image);
                    price = itemView.findViewById(R.id.textView);
                    add = itemView.findViewById(R.id.imageButton3);
                    remove = itemView.findViewById(R.id.imageButton4);
                    title = itemView.findViewById(R.id.textView5);
                    quantity = itemView.findViewById(R.id.editText9);
                    addCart = itemView.findViewById(R.id.button5);

                }
            }

        }


    }

}
