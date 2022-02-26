package com.app.template.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.template.databinding.ListKontakBinding;
import com.app.template.model.res.Data;
import com.app.template.model.res.ResListKontak;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private static List<Data> dataPengiriman;
    private int lastPosition = -1;
    private ListKontakBinding viewBinding;
    private Context context;

    private OnKlikListener onKlikListener;

    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public DataAdapter(Context context,List<Data> dataPengiriman,OnKlikListener onKlikListener) {
        this.context = context;
        this.dataPengiriman = dataPengiriman;
        this.onKlikListener = onKlikListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewBinding = ListKontakBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(viewBinding,onKlikListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data resMeals = dataPengiriman.get(position);
        holder.viewBinding.tvNama.setText("Name      : " +resMeals.getNama());
        holder.viewBinding.tvNomor.setText("Phone     : " +resMeals.getNomor());
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition)
        {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            itemView.startAnimation(anim);
        }
    }

    @Override
    public int getItemCount() {
        return dataPengiriman.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ListKontakBinding viewBinding;
        OnKlikListener onKlikListener;

        public ViewHolder(@NonNull ListKontakBinding binding, OnKlikListener onKlikListener) {
            super(binding.getRoot());

            viewBinding = binding;
            this.onKlikListener = onKlikListener;
            viewBinding.llView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onKlikListener.onKlikClick(dataPengiriman,getAdapterPosition());
        }
    }

    public void setListAdapter(List<Data> mealsList){
        dataPengiriman = mealsList;
        notifyDataSetChanged();
    }

    public interface OnKlikListener{
        void onKlikClick(List<Data> dataPengiriman,int position);
    }
}
