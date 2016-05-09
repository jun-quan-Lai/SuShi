package com.ljq.sushi.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljq.sushi.R;


public class BaiKe_tab4_Frag extends Fragment {

    public static BaiKe_tab4_Frag newInstance(){
        BaiKe_tab4_Frag fragment = new BaiKe_tab4_Frag();
        return fragment;
    }

    public BaiKe_tab4_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_baike_tab4,container,false);
    }

}
