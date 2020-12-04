
package com.softxpert.application.Model.car;
import com.google.gson.annotations.SerializedName;



public class MainResponse<T> {

    @SerializedName("data")
    private T mData;
    @SerializedName("status")
    private Long mStatus;

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
