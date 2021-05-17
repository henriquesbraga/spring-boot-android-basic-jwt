package com.henriquesbraga.myapplication.activities.login;

import android.util.ArrayMap;

import com.henriquesbraga.myapplication.api.ApiClient;
import com.henriquesbraga.myapplication.api.ApiInterface;
import com.henriquesbraga.myapplication.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

  private LoginView loginView;

  public LoginPresenter(LoginView view){
    this.loginView = view;
  }

  void getData(User user) {
    loginView.showLoading();
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    Map<String, Object> jsonParams = new ArrayMap<>();
    jsonParams.put("email", user.getEmail());
    jsonParams.put("senha", user.getSenha());

    RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
    Call<User> call = apiInterface.login(requestBody);
    call.enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()) {
          loginView.onGetResult(response.body().getToken());
          loginView.hideLoading();
        }
        else{
          switch (response.code()){
            case 401:
              try {
                JSONObject object = new JSONObject(response.errorBody().string());
                String msg = object.get("message").toString();
                loginView.onResultError(msg);
              } catch (IOException | JSONException e) {
                e.printStackTrace();
              }
            loginView.hideLoading();
          }
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        loginView.onResultError(t.getLocalizedMessage());
        loginView.hideLoading();
      }
    });


  }
}
