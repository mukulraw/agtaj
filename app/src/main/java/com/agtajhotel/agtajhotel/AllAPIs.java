package com.agtajhotel.agtajhotel;

import com.agtajhotel.agtajhotel.addCartPOJO.addCartBean;
import com.agtajhotel.agtajhotel.addWishlistPOJO.addWishlistbean;
import com.agtajhotel.agtajhotel.cartCountPOJO.cartCountBean;
import com.agtajhotel.agtajhotel.cartDeletePOJO.cartRemoveBean;
import com.agtajhotel.agtajhotel.cartPOJO.cartBean;
import com.agtajhotel.agtajhotel.categoryPOJO.categoryBean;
import com.agtajhotel.agtajhotel.customerPOJO.customerBean;
import com.agtajhotel.agtajhotel.productListPOJO.productListBean;
import com.agtajhotel.agtajhotel.singleProductPOJO.singleProductBean;
import com.agtajhotel.agtajhotel.wishlistPOJO.wishlostbean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("mobileapi/cart/add")
    Call<addCartBean> addToCart(
            @Query("product_id") String id,
            @Query("qty") String quantity
    );

    @GET("mobileapi/cart/getQty")
    Call<cartCountBean> getCartCount();

    @GET("mobileapi/cart/getCartInfo")
    Call<cartBean> getCart();


    @GET("mobileapi/cart/update")
    Call<cartBean> updateCart(
            @Query("cart") String id,
            @Query("qty") String quantity
    );

    @GET("mobileapi/cart/removeCart")
    Call<cartRemoveBean> removeCart(
            @Query("cart_item_id") String id
    );

    @GET("mobileapi/wishlist/add")
    Call<addWishlistbean> addWishlist(
            @Query("product_id") String id
    );

    @GET("mobileapi/wishlist/getWishlist")
    Call<wishlostbean> gedtWishlst();

}
