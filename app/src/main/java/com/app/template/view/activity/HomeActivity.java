package com.app.template.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.template.R;
import com.app.template.connection.API;
import com.app.template.connection.Host;
import com.app.template.databinding.ActivityHomeBinding;
import com.app.template.model.res.ResAuth;
import com.app.template.view.fragment.HomeFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadfragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.it_logout) {
            // do something
            panggilapilogout();
        } else {
            // do something
        }

        return false;
    }

    private void panggilapilogout() {
        Call<ResAuth> callRegister = API.service().logoutRequest();
        callRegister.enqueue(new Callback<ResAuth>() {
            @Override
            public void onResponse(Call<ResAuth> call, Response<ResAuth> response) {
                Log.d("Log Respon", response.code() + "");
                ResAuth resAuth = response.body();
                if(response.code()==502){
                    new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                    new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(resAuth.getStatus()+"")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    pindahkelogin();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResAuth> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(HomeActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pindahkelogin() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

    private void loadfragment() {
        HomeFragment frag = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fl_view,frag,"Test Fragment");
        transaction.commit();
    }
}