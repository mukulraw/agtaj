package com.agtajhotel.agtajhotel.categoryPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("position ")
    @Expose
    private String position;
    @SerializedName("level ")
    @Expose
    private String level;
    @SerializedName("url_key")
    @Expose
    private String urlKey;
    @SerializedName("thumbnail_url")
    @Expose
    private Object thumbnailUrl;
    @SerializedName("image_url")
    @Expose
    private Boolean imageUrl;
    @SerializedName("children")
    @Expose
    private List<String> children = null;
    @SerializedName("child")
    @Expose
    private List<String> child = null;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public Object getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(Object thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Boolean imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public List<String> getChild() {
        return child;
    }

    public void setChild(List<String> child) {
        this.child = child;
    }

}
