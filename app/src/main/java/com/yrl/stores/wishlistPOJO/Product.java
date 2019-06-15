package com.yrl.stores.wishlistPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("wishlist_id")
    @Expose
    private String wishlistId;
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("news_from_date")
    @Expose
    private Object newsFromDate;
    @SerializedName("news_to_date")
    @Expose
    private Object newsToDate;
    @SerializedName("special_from_date")
    @Expose
    private Object specialFromDate;
    @SerializedName("special_to_date")
    @Expose
    private Object specialToDate;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("url_key")
    @Expose
    private String urlKey;
    @SerializedName("regular_price_with_tax")
    @Expose
    private String regularPriceWithTax;
    @SerializedName("final_price_with_tax")
    @Expose
    private String finalPriceWithTax;
    @SerializedName("symbol")
    @Expose
    private String symbol;

    public String getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(String wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNewsFromDate() {
        return newsFromDate;
    }

    public void setNewsFromDate(Object newsFromDate) {
        this.newsFromDate = newsFromDate;
    }

    public Object getNewsToDate() {
        return newsToDate;
    }

    public void setNewsToDate(Object newsToDate) {
        this.newsToDate = newsToDate;
    }

    public Object getSpecialFromDate() {
        return specialFromDate;
    }

    public void setSpecialFromDate(Object specialFromDate) {
        this.specialFromDate = specialFromDate;
    }

    public Object getSpecialToDate() {
        return specialToDate;
    }

    public void setSpecialToDate(Object specialToDate) {
        this.specialToDate = specialToDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getRegularPriceWithTax() {
        return regularPriceWithTax;
    }

    public void setRegularPriceWithTax(String regularPriceWithTax) {
        this.regularPriceWithTax = regularPriceWithTax;
    }

    public String getFinalPriceWithTax() {
        return finalPriceWithTax;
    }

    public void setFinalPriceWithTax(String finalPriceWithTax) {
        this.finalPriceWithTax = finalPriceWithTax;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
