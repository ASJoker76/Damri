package com.app.template.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.template.connection.API;
import com.app.template.databinding.ActivityLoginBinding;
import com.app.template.model.req.ReqAuth;
import com.app.template.model.res.ResAuth;

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
                String username = binding.txtUsername.getText().toString();
                String password = binding.txtPassword.getText().toString();
                login(username,password);
            }
        });
    }

    private void login(String username, String password) {
        ReqAuth reqAuth = new ReqAuth();
        reqAuth.setUsername(username);
        reqAuth.setPassword(password);

        Call<ResAuth> callRegister = API.service().authRequest(reqAuth);
        callRegister.enqueue(new Callback<ResAuth>() {
            @Override
            public void onResponse(Call<ResAuth> call, Response<ResAuth> response) {
                Log.d("Log Respon", response.code() + "");
                ResAuth resAuth = response.body();
                Toast.makeText(LoginActivity.this, resAuth.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResAuth> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}