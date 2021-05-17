package com.henriquesbraga.myapplication.activities.login;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import android.app.ProgressDialog;
import com.henriquesbraga.myapplication.R;
import com.henriquesbraga.myapplication.entities.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements LoginView{

  MaterialToolbar toolbar;

  TextInputLayout inputEmail;
  TextInputLayout inputSenha;
  LoginPresenter loginPresenter;
  Button button;
  ProgressDialog progress;
  String token;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Login");

    inputEmail = (TextInputLayout) findViewById(R.id.inpt_email);
    inputSenha = (TextInputLayout) findViewById(R.id.inpt_senha);
    button = (Button) findViewById(R.id.btn_login);

    loginPresenter = new LoginPresenter(this);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        User user = new User(inputEmail.getEditText().getText().toString(), inputSenha.getEditText().getText().toString());
        loginPresenter.getData(user);
      }
    });
  }

  @Override
  public void showLoading() {
    progress = new ProgressDialog(this);
    progress.setTitle("Buscando dados");
    progress.setMessage("Favor aguardar...");
    progress.setCancelable(false);
    progress.show();
  }

  @Override
  public void hideLoading() {
    progress.dismiss();
  }

  @Override
  public void onGetResult(String data) {
    token = data;
    Intent intent = new Intent();
    intent.putExtra("tokenValue", token);
    setResult(RESULT_OK, intent);
    finish();
  }

  @Override
  public void onResultError(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}