package com.asiatravel.atviewpager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asiatravel.atviewpager.R;


public class MyFragment extends Fragment {

    private int images[] = new int[]{
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        Bundle bundle = getArguments();
        int index = bundle.getInt("index");
        iv.setImageResource(images[index]);
        return view;
    }

}
