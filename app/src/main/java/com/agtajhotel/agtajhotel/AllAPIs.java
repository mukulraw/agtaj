package com.agtajhotel.agtajhotel;

import com.agtajhotel.agtajhotel.addCartPOJO.addCartBean;
import com.agtajhotel.agtajhotel.addWishlistPOJO.addWishlistbean;
import com.agtajhotel.agtajhotel.billingPOJO.billingBean;
import com.agtajhotel.agtajhotel.cartCountPOJO.cartCountBean;
import com.agtajhotel.agtajhotel.cartDeletePOJO.cartRemoveBean;
import com.agtajhotel.agtajhotel.cartPOJO.cartBean;
import com.agtajhotel.agtajhotel.categoryPOJO.categoryBean;
import com.agtajhotel.agtajhotel.customerPOJO.customerBean;
import com.agtajhotel.agtajhotel.ordersPOJO.ordersBean;
import com.agtajhotel.agtajhotel.productListPOJO.productListBean;
import com.agtajhotel.agtajhotel.searchPOJO.searchbean;
import com.agtajhotel.agtajhotel.shippingMethodListPOJO.shippingMethodListBean;
import com.agtajhotel.agtajhotel.shippingMethodPOJO.shippingMethodBean;
import com.agtajhotel.agtajhotel.shippingPOJO.shippingBean;
import com.agtajhotel.agtajhotel.singleProductPOJO.singleProductBean;
import com.agtajhotel.agtajhotel.successPOJO.successBean;
import com.agtajhotel.agtajhotel.wishlistPOJO.wishlostbean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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


    @Multipart
    @POST("mobileapi/checkout/setBilling")
    Call<billingBean> setBilling(
            @Part("billing_address_id") String baddressid,
            @Part("billing[address_id]") String addressid,
            @Part("billing[firstname]") String first,
            @Part("billing[lastname]") String last,
            @Part("billing[company]") String company,
            @Part("billing[street][]") String street1,
            @Part("billing[street][]") String street2,
            @Part("billing[city]") String city,
            @Part("billing[region_id]") String regionId,
            @Part("billing[region]") String region,
            @Part("billing[country_id]") String country,
            @Part("billing[telephone]") String phone,
            @Part("billing[fax]") String fax,
            @Part("billing[use_for_shipping]") String isshipping
    );


    @Multipart
    @POST("mobileapi/checkout/setShipping")
    Call<shippingBean> setShipping(
            @Part("shipping_address_id") String saddressid,
            @Part("shipping[address_id]") String addressid,
            @Part("shipping[firstname]") String first,
            @Part("shipping[lastname]") String last,
            @Part("shipping[company]") String company,
            @Part("shipping[street][]") String street1,
            @Part("shipping[street][]") String street2,
            @Part("shipping[city]") String city,
            @Part("shipping[region_id]") String regionId,
            @Part("shipping[region]") String region,
            @Part("shipping[country_id]") String country,
            @Part("shipping[telephone]") String phone,
            @Part("shipping[fax]") String fax,
            @Part("shipping[same_as_billing]") String isbilling
    );

    @GET("mobileapi/checkout/getShippingMethodsList")
    Call<shippingMethodListBean> getShippingMethods();


    @Multipart
    @POST("mobileapi/checkout/setShippingMethod")
    Call<shippingMethodBean> setShippingMethod(
            @Part("shipping_method") String method
    );

    @Multipart
    @POST("mobileapi/checkout/setPayMethod")
    Call<shippingMethodBean> setPaymentMethod(
            @Part("payment[method]") String method
    );

    @GET("mobileapi/checkout/getFormKey")
    Call<formBean> getFormKey();

    @GET("mobileapi/checkout/success")
    Call<String> success();


    @GET("mobileapi/order/getorderlist")
    Call<ordersBean> getOrders();

    @GET("mobileapi/customer/logout")
    Call<logoutBean> logout();

    @GET("mobileapi/customer/forgotpwd")
    Call<forgotBean> forgotPassword(
            @Query("email") String email
    );

    @GET("mobileapi/search/index")
    Call<searchbean> search(
            @Query("q") String query,
            @Query("page") String page,
            @Query("limit") String limit
    );

}
