package com.henriquesbraga.myapplication.activities.login;

import com.henriquesbraga.myapplication.entities.User;

public interface LoginView {
  void showLoading();
  void hideLoading();
  void onGetResult(String data);
  void onResultError(String message);
}
