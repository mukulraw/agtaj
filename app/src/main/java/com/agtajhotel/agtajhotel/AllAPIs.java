package com.agtajhotel.agtajhotel;

import com.agtajhotel.agtajhotel.categoryPOJO.categoryBean;
import com.agtajhotel.agtajhotel.customerPOJO.customerBean;
import com.agtajhotel.agtajhotel.productListPOJO.productListBean;
import com.agtajhotel.agtajhotel.singleProductPOJO.singleProductBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AllAPIs {

    @GET("mobileapi/customer/register")
    Call<customerBean> register(
            @Query("firstname") String firstname,
            @Query("lastname") String lastname,
            @Query("email") String email,
            @Query("default_mobile_number") String phone,
            @Query("pwd") String password
            );

    @GET("mobileapi/customer/login")
    Call<customerBean> login(
            @Query("username") String email,
            @Query("password") String password
    );

    @GET("mobileapi/index/index")
    Call<categoryBean> getCategory(
            @Query("cmd") String cmd
    );

    @GET("mobileapi/index/index")
    Call<productListBean> getProductsList(
            @Query("cmd") String cmd,
            @Query("page") String page,
            @Query("limit") String limit,
            @Query("order") String order,
            @Query("dir") String dir,
            @Query("category_id") String catid
    );

    @GET("mobileapi/products/getproductdetail")
    Call<singleProductBean> getProductData(
            @Query("product_id") String id
    );

}
