package com.app.template.view.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.app.template.R;
import com.app.template.adapter.DataAdapter;
import com.app.template.databinding.DialogInsertKontakBinding;
import com.app.template.databinding.FragmentHomeBinding;
import com.app.template.model.res.Data;
import com.app.template.model.res.ResAuth;
import com.app.template.model.res.ResDelete;
import com.app.template.model.res.ResInsert;
import com.app.template.model.res.ResListKontak;
import com.app.template.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class HomeFragment extends Fragment implements DataAdapter.OnKlikListener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private DataAdapter dataAdapter;
    private List<Data> kontakArraylist;
    //private ArrayList<Data> kontaklist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadrecyclerview();
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        viewModel.panggilapilist();
        viewModel.getKontakLiveDataObserver().observe(getActivity(), new Observer<ResListKontak>() {
            @Override
            public void onChanged(ResListKontak resListKontak) {
                if(resListKontak != null){
                    //kontakArraylist.addAll(resListKontak.getData());
                    dataAdapter.setListAdapter(resListKontak.getData());
                    Log.e("dapet data",resListKontak.getData().toString());
                }
                else {
                    dataAdapter.setListAdapter(null);
                    Log.e("dapet data",null);
                }
            }
        });
        callactionbutton();

        return root;
    }

    private void callactionbutton() {
        binding.flView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mAlertDialog = new AlertDialog.Builder(v.getContext()).create();
                DialogInsertKontakBinding bindingAlert = DialogInsertKontakBinding.inflate(LayoutInflater.from(v.getContext()));
                mAlertDialog.setView(bindingAlert.getRoot());
                mAlertDialog.setCancelable(true);
                mAlertDialog.getWindow().setLayout(700, 600);
                mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mAlertDialog.show();

                bindingAlert.btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String getNama = bindingAlert.txtNama.getText().toString();
                        String getNoTelp = bindingAlert.txtNomor.getText().toString();

                        if (getNama.equals("") || getNama.length() == 0
                                || getNoTelp.equals("") || getNoTelp.length() == 0) {

                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Data Belum Dilengkapi")
                                    .show();
                        }
                        else {
                            insertProcess(getNama,getNoTelp);
                            mAlertDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void insertProcess(String getNama, String getNoTelp) {
        viewModel.panggilapiinsert(getNama,getNoTelp);
        viewModel.getInsertLiveData().observe(getActivity(), new Observer<ResInsert>() {
            @Override
            public void onChanged(ResInsert resInsert) {
                if(resInsert != null){
                    if(resInsert.getKode()==200){
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Data Berhasil Tersimpan")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        viewModel.panggilapilist();
                                    }
                                })
                                .show();
                    }
                    else{
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Data Gagal Tersimpan")
                                .show();
                    }
                }
                else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Data Gagal Tersimpan")
                            .show();
                }
            }
        });
    }

    private void loadrecyclerview() {
        kontakArraylist = new ArrayList<>();
        dataAdapter = new DataAdapter(getActivity(),kontakArraylist,this);
        binding.rvView.setAdapter(new AlphaInAnimationAdapter(dataAdapter));
        binding.rvView.setLayoutManager(new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL, false));
        LinearLayoutManager recyclerManager = ((LinearLayoutManager)binding.rvView.getLayoutManager());

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter (dataAdapter);
        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        alphaInAnimationAdapter.setFirstOnly(false);
    }

    @Override
    public void onKlikClick(List<Data> kontakArraylist, int position) {

        String id_kontak = kontakArraylist.get(position).getId();
        String nama = kontakArraylist.get(position).getNama();
        String nomor = kontakArraylist.get(position).getNomor();

        Log.e("test",id_kontak+","+nama+","+nomor);

        AlertDialog mAlertDialog = new AlertDialog.Builder(getActivity()).create();
        DialogInsertKontakBinding bindingAlert = DialogInsertKontakBinding.inflate(LayoutInflater.from(getActivity()));
        mAlertDialog.setView(bindingAlert.getRoot());
        mAlertDialog.setCancelable(true);
        mAlertDialog.getWindow().setLayout(700, 600);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.show();

        bindingAlert.txtNama.setText(nama);
        bindingAlert.txtNomor.setText(nomor);

        bindingAlert.btnSimpan.setVisibility(View.GONE);
        bindingAlert.lyUpdateDelate.setVisibility(View.VISIBLE);
        bindingAlert.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getid = id_kontak;
                String getNama = bindingAlert.txtNama.getText().toString();
                String getNoTelp = bindingAlert.txtNomor.getText().toString();

                if (getNama.equals("") || getNama.length() == 0
                        || getNoTelp.equals("") || getNoTelp.length() == 0) {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Data Belum Dilengkapi")
                            .show();
                }
                else {
                    updateProcess(getid,getNama,getNoTelp);
                    mAlertDialog.dismiss();
                }
            }
        });
        bindingAlert.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getid = Integer.valueOf(id_kontak);

                deleteProcess(getid);
                mAlertDialog.dismiss();
            }
        });
    }

    private void deleteProcess(int getid) {
        viewModel.panggilapidelete(getid);
        viewModel.getDeleteLiveData().observe(getActivity(), new Observer<ResDelete>() {
            @Override
            public void onChanged(ResDelete resDelete) {
                if(resDelete != null){
                    if(resDelete.getKode()==200){
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Data Berhasil Terhapus")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        viewModel.panggilapilist();
                                    }
                                })
                                .show();
                    }
                    else{
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Data Gagal Terhapus")
                                .show();
                    }
                }
                else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Data Gagal Terhapus")
                            .show();
                }
            }
        });
    }

    private void updateProcess(String getid, String getNama, String getNoTelp) {
        viewModel.panggilapiupdate(getid,getNama,getNoTelp);
        viewModel.getInsertLiveData().observe(getActivity(), new Observer<ResInsert>() {
            @Override
            public void onChanged(ResInsert resInsert) {
                if(resInsert != null){
                    if(resInsert.getKode()==200){
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Data Berhasil Terupdate")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        viewModel.panggilapilist();
                                    }
                                })
                                .show();
                    }
                    else{
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Data Gagal Terupdate")
                                .show();
                    }
                }
                else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Data Gagal Terupdate")
                            .show();
                }
            }
        });
    }
}