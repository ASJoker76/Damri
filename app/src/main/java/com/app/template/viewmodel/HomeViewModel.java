package com.app.template.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.template.connection.API;
import com.app.template.connection.Host;
import com.app.template.model.res.Data;
import com.app.template.model.res.ResAuth;
import com.app.template.model.res.ResDelete;
import com.app.template.model.res.ResInsert;
import com.app.template.model.res.ResListKontak;
import com.app.template.view.activity.LoginActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ResListKontak> kontakLiveData;
    private MutableLiveData<ResInsert> insertLiveData;
    private MutableLiveData<ResDelete> deleteLiveData;

    public HomeViewModel(){
        kontakLiveData = new MutableLiveData<>();
        insertLiveData = new MutableLiveData<>();
        deleteLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ResListKontak> getKontakLiveDataObserver(){
        return kontakLiveData;
    }

    public MutableLiveData<ResInsert> getInsertLiveData(){
        return insertLiveData;
    }

    public MutableLiveData<ResDelete> getDeleteLiveData(){
        return deleteLiveData;
    }

    public void panggilapilist(){
        Call<ResListKontak> callRegister = API.service().listRequest();
        callRegister.enqueue(new Callback<ResListKontak>() {
            @Override
            public void onResponse(Call<ResListKontak> call, Response<ResListKontak> response) {
                ResListKontak resListKontak = response.body();
                if(resListKontak != null){
                    kontakLiveData.postValue(resListKontak);
                }
            }

            @Override
            public void onFailure(Call<ResListKontak> call, Throwable t) {
                kontakLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }

    public void panggilapiinsert(String nama,String nomor){
        Call<ResInsert> callRegister = API.service().insertRequest(nama,nomor);
        callRegister.enqueue(new Callback<ResInsert>() {
            @Override
            public void onResponse(Call<ResInsert> call, Response<ResInsert> response) {
                ResInsert resInsert = response.body();
                insertLiveData.postValue(resInsert);
            }

            @Override
            public void onFailure(Call<ResInsert> call, Throwable t) {
                insertLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }

    public void panggilapiupdate(String id,String nama,String nomor){
        Call<ResInsert> callRegister = API.service().updateRequest(id,nama,nomor);
        callRegister.enqueue(new Callback<ResInsert>() {
            @Override
            public void onResponse(Call<ResInsert> call, Response<ResInsert> response) {
                ResInsert resInsert = response.body();
                insertLiveData.postValue(resInsert);
            }

            @Override
            public void onFailure(Call<ResInsert> call, Throwable t) {
                insertLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }

    public void panggilapidelete(int id){
        Call<ResDelete> callRegister = API.service().deleteRequest(id);
        callRegister.enqueue(new Callback<ResDelete>() {
            @Override
            public void onResponse(Call<ResDelete> call, Response<ResDelete> response) {
                ResDelete resInsert = response.body();
                deleteLiveData.postValue(resInsert);
            }

            @Override
            public void onFailure(Call<ResDelete> call, Throwable t) {
                deleteLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }
}
