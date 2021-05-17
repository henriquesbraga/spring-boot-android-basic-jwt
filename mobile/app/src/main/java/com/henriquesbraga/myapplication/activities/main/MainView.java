package com.henriquesbraga.myapplication.activities.main;

public interface MainView {
  void showLoading();
  void hideLoading();
  void onGetResult(String data);
  void onResultError(String message);
}
