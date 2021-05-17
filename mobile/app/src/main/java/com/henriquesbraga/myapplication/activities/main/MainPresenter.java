package com.henriquesbraga.myapplication.activities.main;

import android.util.Log;

import com.henriquesbraga.myapplication.api.ApiClient;
import com.henriquesbraga.myapplication.api.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

  private MainView mainView;

  public MainPresenter(MainView view){
    this.mainView = view;
  }

  void getPublicData(){
    mainView.showLoading();
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    Call<String> call = apiInterface.getPublicData();
    call.enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        if(response.body() != null){
          mainView.onGetResult(response.body());
        }
        mainView.hideLoading();
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {
        Log.i("token", t.getLocalizedMessage());
      }
    });
  }

  void getPrivateData(String token){
    mainView.showLoading();
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    Call<String> call = apiInterface.getPrivateData(token);
    call.enqueue(new Callback<String>() {
      @Override
      public void onResponse(Call<String> call, Response<String> response) {
        if(response.body() != null){
          mainView.onGetResult(response.body());
          mainView.hideLoading();
        }
        else{
          switch (response.code()){
            case 403:
              try {
                JSONObject object = new JSONObject(response.errorBody().string());
                String msg = object.get("message").toString();
                mainView.onResultError(msg);
                mainView.hideLoading();
              } catch (IOException | JSONException e) {
                e.printStackTrace();
              }
              mainView.hideLoading();
          }
        }
      }

      @Override
      public void onFailure(Call<String> call, Throwable t) {
        Log.i("token", t.getLocalizedMessage());
      }
    });

  }




}
