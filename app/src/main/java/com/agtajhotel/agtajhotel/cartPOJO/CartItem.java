package com.agtajhotel.agtajhotel.cartPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartItem {

    @SerializedName("cart_item_id")
    @Expose
    private String cartItemId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_title")
    @Expose
    private String itemTitle;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("thumbnail_pic_url")
    @Expose
    private String thumbnailPicUrl;
    @SerializedName("custom_option")
    @Expose
    private List<CustomOption> customOption = null;
    @SerializedName("item_price")
    @Expose
    private Integer itemPrice;
    @SerializedName("bundle_option")
    @Expose
    private List<BundleOption> bundleOption = null;

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getThumbnailPicUrl() {
        return thumbnailPicUrl;
    }

    public void setThumbnailPicUrl(String thumbnailPicUrl) {
        this.thumbnailPicUrl = thumbnailPicUrl;
    }

    public List<CustomOption> getCustomOption() {
        return customOption;
    }

    public void setCustomOption(List<CustomOption> customOption) {
        this.customOption = customOption;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public List<BundleOption> getBundleOption() {
        return bundleOption;
    }

    public void setBundleOption(List<BundleOption> bundleOption) {
        this.bundleOption = bundleOption;
    }


}
