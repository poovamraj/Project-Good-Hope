package com.goodhopes.poovam.projectgoodhopes.aboutus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodhopes.poovam.projectgoodhopes.R;

/**
 * Created by poovam on 3/12/16.
 */

public class AboutUsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.about_us_framgent,container,false);
        return root;
    }
}