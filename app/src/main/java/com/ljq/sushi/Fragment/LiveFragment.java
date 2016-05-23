package com.ljq.sushi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ljq.sushi.R;

import java.lang.reflect.Field;

import krelve.view.Kanner;


public class LiveFragment extends Fragment {

    private Kanner adsView;

    public LiveFragment() {
        // Required empty public constructor
    }


    public static LiveFragment newInstance() {
        LiveFragment fragment = new LiveFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adsView = (Kanner) getView().findViewById(R.id.ads);
        int[] imagesRes = {R.mipmap.ads1, R.mipmap.ads2, R.mipmap.ads3,
                R.mipmap.ads4, R.mipmap.ads5};
        adsView.setImagesRes(imagesRes);

        adsView.setAdsViewClickListenr(new Kanner.AdsViewClickListener() {
            @Override
            public void onItemClick(int item) {
                Toast.makeText(getActivity(),"you click"+item, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        adsView.removeCallbacksAndMessages();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
