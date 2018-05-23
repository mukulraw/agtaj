package com.agtajhotel.agtajhotel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.agtajhotel.agtajhotel.cartCountPOJO.cartCountBean;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import im.delight.android.webview.AdvancedWebView;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity{



    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Toolbar toolbar;
    LinearLayout replace;
    TextView userame;

    String name;
    DrawerLayout drawer;
    String id;

    TextView logout;

    TextView home , caart , wishlist , orders;

    ImageButton search;
    ImageView cart;

    TextView count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();


        toolbar = findViewById(R.id.toolbar);
        replace = findViewById(R.id.replace);
        logout = findViewById(R.id.logout);

        home = findViewById(R.id.home);
        caart = findViewById(R.id.cart);
        wishlist = findViewById(R.id.wishlist);

        count = findViewById(R.id.textView4);
        search = findViewById(R.id.imageButton);
        cart = findViewById(R.id.imageView5);
        orders = findViewById(R.id.orders);

        userame = findViewById(R.id.textView3);


        id = pref.getString("id" , "");


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Category frag1 = new Category();
        ft.replace(R.id.replace , frag1);
        //ft.addToBackStack(null);
        ft.commit();


        toolbar.setTitle("Category");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                edit.remove("email");
                edit.remove("pass");
                edit.remove("id");
                edit.remove("name");
                edit.apply();

                Intent intent = new Intent(MainActivity.this , Splash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Orders.class);
                startActivity(intent);
            }
        });

        caart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Cart.class);
                startActivity(intent);
            }
        });


        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , Wishlist.class);
                startActivity(intent);

            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getSupportFragmentManager();

                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }

                FragmentTransaction ft = fm.beginTransaction();
                Category frag1 = new Category();
                ft.replace(R.id.replace , frag1);
                //ft.addToBackStack(null);
                ft.commit();

            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , Cart.class);
                startActivity(intent);

            }
        });

        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        l1.add("Dhanbad");


        l2.add("Hirapur");
        l2.add("Jharia");
        l2.add("Bank More");
        l2.add("Steel Gate");
        l2.add("Govindpur");

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.main_popup);
        dialog.show();


        Spinner spinner1 = dialog.findViewById(R.id.spinner2);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, l1);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);

        Spinner spinner2 = dialog.findViewById(R.id.spinner3);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, l2);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(dataAdapter1);

        Button submit = dialog.findViewById(R.id.button14);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        name = pref.getString("name" , "");
        userame.setText("Hi, " + name);


        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(MainActivity.this), CookiePolicy.ACCEPT_ALL);

        CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(cookieJar);
        OkHttpClient client = builder.build();


        //progress.setVisibility(View.VISIBLE);
        final bean b1 = (bean) getApplicationContext();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b1.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        final AllAPIs cr = retrofit.create(AllAPIs.class);


        Call<cartCountBean> call = cr.getCartCount();

        call.enqueue(new Callback<cartCountBean>() {
            @Override
            public void onResponse(Call<cartCountBean> call, Response<cartCountBean> response) {

                edit.putString("count" , response.body().getModel().getNum().toString());
                edit.apply();

                if (count != null)
                {
                    setCount();
                }
            }

            @Override
            public void onFailure(Call<cartCountBean> call, Throwable t) {

            }
        });





    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.cart);
        MenuItemCompat.setActionView(item, R.layout.cart_count);
        View view = MenuItemCompat.getActionView(item);
        counn = view.findViewById(R.id.textView4);

        setCount();

        return true;
    }*/


    public void setCount()
    {
        count.setText(pref.getString("count" , "0"));
    }



    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.cart:
                Toast.makeText(getApplicationContext(),"Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


}
