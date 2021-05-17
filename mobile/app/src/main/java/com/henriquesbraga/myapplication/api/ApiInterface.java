package com.henriquesbraga.myapplication.api;

import com.google.gson.JsonObject;
import com.henriquesbraga.myapplication.entities.User;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
  @GET("usuarios/test")
  Call<String> getPublicData();

  @GET("usuarios/privatetest")
  Call<String> getPrivateData(@Header("Authorization") String authToken);

  @POST("login")
  Call<User> login (@Body RequestBody params);


}
