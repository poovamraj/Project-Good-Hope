package com.goodhopes.poovam.projectgoodhopes.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.BaseApplicationClass;
import com.goodhopes.poovam.projectgoodhopes.common.CurrentView;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.common.Utilities;
import com.goodhopes.poovam.projectgoodhopes.reader.ReaderActivity;
import com.goodhopes.poovam.projectgoodhopes.reader.cardview.CardViewFragment;
import com.goodhopes.poovam.projectgoodhopes.reader.listview.ListViewFragment;
import com.goodhopes.poovam.projectgoodhopes.shelffragment.ShelfAdapter;

import java.util.ArrayList;

/**
 * Created by poovam on 11/12/16.
 */

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.ShelfViewHolder> {
    private Subscription subscription;
    private ArrayList<String> name;
    private ArrayList<Integer> icon;
    AppCompatActivity activity;
    private boolean showTitle;
    class ShelfViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;
        ShelfViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.company_name);
            thumbnail = (ImageView) view.findViewById(R.id.company_logo);
        }
    }
    public HomeAdapter(ArrayList<String> name, ArrayList<Integer> icon, boolean showTitle, Activity activity){
        this.name = name;
        this.icon = icon;
        this.showTitle = showTitle;
        this.activity =(AppCompatActivity) activity;
    }
    @Override
    public HomeAdapter.ShelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shelf_cell, parent, false);
        final HomeAdapter.ShelfViewHolder holder = new ShelfViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Subscription subscription:Subscription.values()){
                    if(subscription.name.equals(name.get(holder.getAdapterPosition()))){
                        BaseApplicationClass base =(BaseApplicationClass) view.getContext().getApplicationContext();
                        base.settingsInfo.setHomePage(subscription);
                        base.updateSettings();
                        new Utilities().refreshHomePage(activity,true);
                        break;
                    }
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ShelfViewHolder holder, int position) {
        holder.title.setVisibility(View.GONE);
        if(showTitle) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(name.get(position));
        }
        holder.thumbnail.setImageDrawable(ContextCompat.getDrawable(holder.thumbnail.getContext(),
                icon.get(position)));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

}