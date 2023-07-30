package com.example.deltaproject2;

import com.google.gson.annotations.SerializedName;

public class CharReq {
    @SerializedName("type")
    private String type;

    public CharReq(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
