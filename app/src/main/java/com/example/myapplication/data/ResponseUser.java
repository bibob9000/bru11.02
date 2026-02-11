package com.example.myapplication.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResponseUser implements Serializable {

    @SerializedName("access_token")
    @Expose
    public String accessToken;

    @SerializedName("token_type")
    @Expose
    public String tokenType;

    @SerializedName("expires_in")
    @Expose
    public Integer expiresIn;

    @SerializedName("expires_at")
    @Expose
    public Integer expiresAt;

    @SerializedName("refresh_token")
    @Expose
    public String refreshToken;

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Integer getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Integer expiresIn) { this.expiresIn = expiresIn; }

    public Integer getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Integer expiresAt) { this.expiresAt = expiresAt; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}