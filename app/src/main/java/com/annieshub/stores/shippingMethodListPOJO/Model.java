package com.annieshub.stores.shippingMethodListPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("Flat Rate")
    @Expose
    private List<FlatRate> flatRate = null;
    @SerializedName("Free Shipping")
    @Expose
    private List<FreeShipping> freeShipping = null;
    @SerializedName("United Parcel Service")
    @Expose
    private List<UnitedParcelService> unitedParcelService = null;

    public List<FlatRate> getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(List<FlatRate> flatRate) {
        this.flatRate = flatRate;
    }

    public List<FreeShipping> getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(List<FreeShipping> freeShipping) {
        this.freeShipping = freeShipping;
    }

    public List<UnitedParcelService> getUnitedParcelService() {
        return unitedParcelService;
    }

    public void setUnitedParcelService(List<UnitedParcelService> unitedParcelService) {
        this.unitedParcelService = unitedParcelService;
    }

}
