package com.obnoxious.ecatering.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.obnoxious.ecatering.R;

/**
 * Created by Bleeding Rain on 4/17/2019.
 */

public class OrderFragment extends Fragment {

    Context c;
    TextView txt_toolbar_title;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);

        c = getActivity();

        Typeface face = Typeface.createFromAsset(c.getAssets(), "font/CaviarDreams.ttf");

        txt_toolbar_title = view.findViewById(R.id.toolbar_title);
        txt_toolbar_title.setText("My Orders");
        txt_toolbar_title.setTypeface(face);

        return view;
    }
}
