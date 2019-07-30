package com.yrl.stores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.yrl.stores.cartPOJO.cartBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    TextView cart;

    TextView count , price;

    View bottom;

    TextView about , terms , lachcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();


        toolbar = findViewById(R.id.toolbar);
        replace = findViewById(R.id.replace);
        logout = findViewById(R.id.logout);

        bottom = findViewById(R.id.include);

        home = findViewById(R.id.home);
        caart = findViewById(R.id.cart);
        wishlist = findViewById(R.id.wishlist);

        count = findViewById(R.id.textView4);
        price = findViewById(R.id.textView31);
        search = findViewById(R.id.imageButton);
        cart = findViewById(R.id.textView32);
        orders = findViewById(R.id.orders);

        userame = findViewById(R.id.textView3);

        about = findViewById(R.id.about);
        terms = findViewById(R.id.terms);
        lachcha = findViewById(R.id.lachcha);

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


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , Search.class);
                startActivityForResult(intent , 12);

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                //progress.setVisibility(View.VISIBLE);


                final bean b = (bean) getApplicationContext();

                Call<logoutBean> call = b.getRetrofit().logout();

                call.enqueue(new Callback<logoutBean>() {
                    @Override
                    public void onResponse(Call<logoutBean> call, Response<logoutBean> response) {

                        if (response.body().getCode() == 0)
                        {
                            edit.remove("email");
                            edit.remove("pass");
                            edit.remove("id");
                            edit.remove("name");
                            edit.apply();

                            Intent intent = new Intent(MainActivity.this , Splash.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(MainActivity.this , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<logoutBean> call, Throwable t) {

                    }
                });







            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Orders.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        caart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Cart.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , Wishlist.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

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
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this , Cart.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();

        l1.add("Dhanbad");

        l2.add("Bank More");
        l2.add("Digwadih");
        l2.add("ISM");
        l2.add("Hirapur");
        l2.add("Jharia");
        l2.add("Jamadoba");

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.main_popup);
        //dialog.show();


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

        lachcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawer(GravityCompat.START);

                String url = "http://agtajlachcha.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this , Web.class);
                intent.putExtra("title" , "About Us");
                intent.putExtra("url" , "https://www.yrlstores.com/index.php/about-us/");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Web.class);
                intent.putExtra("title" , "Terms & Conditions");
                intent.putExtra("url" , "https://www.yrlstores.com/index.php/terms-conditions/");
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK)
        {

            String id = data.getStringExtra("id");
            String name = data.getStringExtra("name");

            FragmentManager fm = getSupportFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();

            SingleProduct frag = new SingleProduct();

            Bundle b = new Bundle();
            b.putString("id" , id);
            b.putString("name" , name);

            frag.setArguments(b);

            ft.replace(R.id.replace , frag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.addToBackStack(null);
            ft.commit();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        name = pref.getString("name" , "");
        userame.setText("Hi, " + name);


        /*CookieManager cookieManager = new CookieManager(new PersistentCookieStore(MainActivity.this), CookiePolicy.ACCEPT_ALL);

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
        });*/



        setCount();



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



        final bean b = (bean) getApplicationContext();

        Call<cartBean> call = b.getRetrofit().getCart();

        call.enqueue(new Callback<cartBean>() {
            @Override
            public void onResponse(Call<cartBean> call, Response<cartBean> response) {

                if (response.body().getCode() == 0)
                {
                    if (response.body().getModel().getCartItems().size() > 0)
                    {

                        float coun = 0;

                        for (int i = 0 ; i < response.body().getModel().getCartItems().size() ; i++)
                        {
                            coun = coun + response.body().getModel().getCartItems().get(i).getItemPrice() * response.body().getModel().getCartItems().get(i).getQty();
                        }

                        float ta = (0 * coun) / 100;

                        price.setText("Rs. " + String.valueOf(coun + ta));

                        bottom.setVisibility(View.VISIBLE);

                        count.setText(String.valueOf(response.body().getModel().getCartItems().size()));

                        //adapter.setGridData(response.body().getModel().getCartItems());
                    }
                    else
                    {
                        bottom.setVisibility(View.GONE);
                        //adapter.setGridData(response.body().getModel().getCartItems());
                        price.setText("Rs. 0.00");
                        count.setText("0");
                    }


                }
                else
                {
                    //adapter.setGridData(response.body().getModel().getCartItems());
                    //Toast.makeText(MainActivity.this , response.body().getMsg().toString() , Toast.LENGTH_SHORT).show();
                }





                //progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<cartBean> call, Throwable t) {
                //progress.setVisibility(View.GONE);
            }
        });

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
