package com.yrl.stores.customerPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class customerBean {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private Object msg;
    @SerializedName("model")
    @Expose
    private Model model;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
