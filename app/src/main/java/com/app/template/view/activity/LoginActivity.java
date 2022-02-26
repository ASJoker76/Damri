package com.app.template.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.template.connection.API;
import com.app.template.connection.Host;
import com.app.template.databinding.ActivityLoginBinding;
import com.app.template.model.res.ResAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.txtEmail.getText().toString();
                String password = binding.txtPassword.getText().toString();
                login(username,password);
            }
        });
    }

    private void login(String username, String password) {
        Call<ResAuth> callRegister = API.service().authRequest(username,password,Host.getToken());
        callRegister.enqueue(new Callback<ResAuth>() {
            @Override
            public void onResponse(Call<ResAuth> call, Response<ResAuth> response) {
                Log.d("Log Respon", response.code() + "");
                ResAuth resAuth = response.body();
                if(response.code()==502){
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Fail")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
                else if(response.code()==200){
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(resAuth.getStatus()+"")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkehome();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResAuth> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pindahkehome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }
}