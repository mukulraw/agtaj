package com.agtajhotel.agtajhotel.shippingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class shippingBean {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("model")
    @Expose
    private Model model;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
