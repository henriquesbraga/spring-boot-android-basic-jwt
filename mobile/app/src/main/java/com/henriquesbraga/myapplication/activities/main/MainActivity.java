package com.henriquesbraga.myapplication.activities.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.henriquesbraga.myapplication.R;
import com.henriquesbraga.myapplication.activities.login.LoginActivity;

public class MainActivity extends AppCompatActivity implements MainView{

  MainPresenter mainPresenter;
  MaterialToolbar toolbar;
  ProgressDialog progress;
  Button checkPublic;
  Button checkPrivate;
  String token;
  String message;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Status");
    toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_outline_menu));
    mainPresenter = new MainPresenter(this);
    checkPublic = (Button) findViewById(R.id.btn_checkPublic);
    checkPrivate = (Button) findViewById(R.id.btn_checkPrivate);


    checkPublic.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mainPresenter.getPublicData();
      }
    });

    checkPrivate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(token == null){
          token = " ";
        }
        mainPresenter.getPrivateData(token);
      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.options_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()){
      case R.id.item1:
        goToActivityForResult(LoginActivity.class);
      return true;

      case R.id.item2:
        Log.i("MENU_OPTION","Apertou a opção 2");
      return true;

      default:
      return super.onOptionsItemSelected(item);
    }
  }


  private void goToActivity(Class c){
    Intent intent = new Intent();
    intent.setClass(MainActivity.this, c);
    startActivity(intent);
    finishActivity(intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
  }

  private void goToActivityForResult(Class c){
    Intent intent = new Intent();
    intent.setClass(MainActivity.this, c);
    startActivityForResult(intent, 1);
    finishActivity(intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == 1){
      if(resultCode == RESULT_OK){
        token = "Bearer " + data.getStringExtra("tokenValue");
      }
    }
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
    message = data;
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onResultError(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}