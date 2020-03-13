package com.annieshub.stores;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class logoutBean {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private Object msg;
    @SerializedName("model")
    @Expose
    private List<Object> model = null;

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

    public List<Object> getModel() {
        return model;
    }

    public void setModel(List<Object> model) {
        this.model = model;
    }

}
