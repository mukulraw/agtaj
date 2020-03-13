package com.annieshub.stores.shippingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("goto_section")
    @Expose
    private String gotoSection;
    @SerializedName("update_section")
    @Expose
    private UpdateSection updateSection;

    public String getGotoSection() {
        return gotoSection;
    }

    public void setGotoSection(String gotoSection) {
        this.gotoSection = gotoSection;
    }

    public UpdateSection getUpdateSection() {
        return updateSection;
    }

    public void setUpdateSection(UpdateSection updateSection) {
        this.updateSection = updateSection;
    }

}
