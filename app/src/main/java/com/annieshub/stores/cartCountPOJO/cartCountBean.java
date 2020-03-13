package com.annieshub.stores.cartCountPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class cartCountBean {

    @SerializedName("ode")
    @Expose
    private Integer ode;
    @SerializedName("msg")
    @Expose
    private Object msg;
    @SerializedName("model")
    @Expose
    private Model model;

    public Integer getOde() {
        return ode;
    }

    public void setOde(Integer ode) {
        this.ode = ode;
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
