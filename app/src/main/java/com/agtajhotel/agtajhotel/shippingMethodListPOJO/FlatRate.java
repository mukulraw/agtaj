package com.agtajhotel.agtajhotel.shippingMethodListPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlatRate {

    @SerializedName("carrier")
    @Expose
    private String carrier;
    @SerializedName("carrier_title")
    @Expose
    private String carrierTitle;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("method_description")
    @Expose
    private Object methodDescription;

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getCarrierTitle() {
        return carrierTitle;
    }

    public void setCarrierTitle(String carrierTitle) {
        this.carrierTitle = carrierTitle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethodTitle() {
        return methodTitle;
    }

    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(Object methodDescription) {
        this.methodDescription = methodDescription;
    }

}
