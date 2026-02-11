package com.example.myapplication.controller;

import com.example.myapplication.data.ChangePasswordToken;
import com.example.myapplication.data.Email;
import com.example.myapplication.data.ResponseUser;
import com.example.myapplication.data.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface API {


    @POST("signup")
    Call<ResponseUser> signUpByEmailAndPswrd(@Header("apiKey") String apiKey, @Body User user);

    @POST("token")
    Call<ResponseUser> login(@Query ("grant_type") String grant_type, @Header("apiKey") String apiKey, @Body User user);

    @POST("recover")
    Call<Void> sendCode(@Header("apiKey") String apiKey, @Body Email email);

    @POST("verify")
    Call<ResponseUser> verifyCode(@Header("apiKey") String apiKey, @Body ChangePasswordToken changePasswordToken);

    @PUT("user")
    Call<Void> updatePassword(@Header("apiKey") String apiKey, @Header("Authorization") String token, @Body User user);
}