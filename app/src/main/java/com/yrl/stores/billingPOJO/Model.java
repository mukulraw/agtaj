package com.yrl.stores.billingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {


    @SerializedName("goto_section")
    @Expose
    private String gotoSection;
    @SerializedName("allow_sections")
    @Expose
    private List<String> allowSections = null;
    @SerializedName("duplicateBillingInfo")
    @Expose
    private String duplicateBillingInfo;

    public String getGotoSection() {
        return gotoSection;
    }

    public void setGotoSection(String gotoSection) {
        this.gotoSection = gotoSection;
    }

    public List<String> getAllowSections() {
        return allowSections;
    }

    public void setAllowSections(List<String> allowSections) {
        this.allowSections = allowSections;
    }

    public String getDuplicateBillingInfo() {
        return duplicateBillingInfo;
    }

    public void setDuplicateBillingInfo(String duplicateBillingInfo) {
        this.duplicateBillingInfo = duplicateBillingInfo;
    }


}
