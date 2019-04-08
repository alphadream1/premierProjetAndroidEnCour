package com.example.premierprojetcourandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.premierprojetcourandroid.model.beans.CityBean;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private ArrayList<CityBean> data;

    public CityAdapter(ArrayList<CityBean> data) {
        this.data = data;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1, tv2;
        ImageView iv;


        public ViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            iv = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int i) {
        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.row_city, vg, false);
        return new CityAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CityBean city = data.get(position);
        viewHolder.tv1.setText(city.getCp());
        viewHolder.tv2.setText(city.getVille());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
