package com.yrl.stores.singleProductPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("custom_options")
    @Expose
    private Object customOptions;
    @SerializedName("stock_level")
    @Expose
    private Integer stockLevel;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("in_wishlist")
    @Expose
    private Boolean inWishlist;
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("rating_summary")
    @Expose
    private Object ratingSummary;
    @SerializedName("reviews_count")
    @Expose
    private Object reviewsCount;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("news_from_date")
    @Expose
    private String newsFromDate;
    @SerializedName("news_to_date")
    @Expose
    private Object newsToDate;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("special_from_date")
    @Expose
    private String specialFromDate;
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
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("additional")
    @Expose
    private List<List<String>> additional = null;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("options")
    @Expose
    private List<Object> options = null;
    @SerializedName("mediaGallery")
    @Expose
    private List<String> mediaGallery = null;

    public Object getCustomOptions() {
        return customOptions;
    }

    public void setCustomOptions(Object customOptions) {
        this.customOptions = customOptions;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getInWishlist() {
        return inWishlist;
    }

    public void setInWishlist(Boolean inWishlist) {
        this.inWishlist = inWishlist;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Object getRatingSummary() {
        return ratingSummary;
    }

    public void setRatingSummary(Object ratingSummary) {
        this.ratingSummary = ratingSummary;
    }

    public Object getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(Object reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewsFromDate() {
        return newsFromDate;
    }

    public void setNewsFromDate(String newsFromDate) {
        this.newsFromDate = newsFromDate;
    }

    public Object getNewsToDate() {
        return newsToDate;
    }

    public void setNewsToDate(Object newsToDate) {
        this.newsToDate = newsToDate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSpecialFromDate() {
        return specialFromDate;
    }

    public void setSpecialFromDate(String specialFromDate) {
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<List<String>> getAdditional() {
        return additional;
    }

    public void setAdditional(List<List<String>> additional) {
        this.additional = additional;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

    public List<String> getMediaGallery() {
        return mediaGallery;
    }

    public void setMediaGallery(List<String> mediaGallery) {
        this.mediaGallery = mediaGallery;
    }


}
