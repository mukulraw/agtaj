package com.agtajhotel.agtajhotel.cartPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("is_virtual")
    @Expose
    private Boolean isVirtual;
    @SerializedName("cart_items")
    @Expose
    private List<CartItem> cartItems = null;
    @SerializedName("cart_items_count")
    @Expose
    private Integer cartItemsCount;
    @SerializedName("payment_methods")
    @Expose
    private List<Object> paymentMethods = null;
    @SerializedName("allow_guest_checkout")
    @Expose
    private Boolean allowGuestCheckout;

    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Integer getCartItemsCount() {
        return cartItemsCount;
    }

    public void setCartItemsCount(Integer cartItemsCount) {
        this.cartItemsCount = cartItemsCount;
    }

    public List<Object> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<Object> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public Boolean getAllowGuestCheckout() {
        return allowGuestCheckout;
    }

    public void setAllowGuestCheckout(Boolean allowGuestCheckout) {
        this.allowGuestCheckout = allowGuestCheckout;
    }

}
