package com.ljq.sushi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljq.sushi.Adapter.RestaurantFragAdapter;
import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.R;
import com.ljq.sushi.UI.DividerItemDecoration;
import com.ljq.sushi.citylist.LetterSortActivity;
import com.ljq.sushi.entity.Restaurant;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/8.
 */
public class RestaurantFrag extends Fragment {


    private TextView cityName;
    private Button changeCity;
    private RecyclerView mRecyclerView;
    private RelativeLayout noDataView;

    private String city=""; //不能设为null，下面有坑
    private List<Restaurant> list;

    private RestaurantFragAdapter adapter;

    public static RestaurantFrag newInstance(){
        RestaurantFrag fragment = new RestaurantFrag();
        return fragment;
    }

    public RestaurantFrag() {
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter = new RestaurantFragAdapter(list);
                    mRecyclerView.setAdapter(adapter);
                    if(mRecyclerView.getVisibility()==View.GONE){
                        mRecyclerView.setVisibility(View.VISIBLE);
                        noDataView.setVisibility(View.GONE);
                    }
                    adapter.setOnRecyclerViewListener(new RestaurantFragAdapter.OnRecyclerViewListener() {
                        @Override
                        public void onItemClick(int position) {

                        }

                        @Override
                        public boolean onItemLongClick(int position) {
                            return false;
                        }
                    });
                    break;
                case 1:
                    mRecyclerView.setVisibility(View.GONE);
                    noDataView.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // super.onSaveInstanceState(outState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_restaurant,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("RestaurantFrag","onActivityCreated");
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("RestaurantFrag","onStart");
        initData(city);
        Log.d("restaurantUrl",AppConstants.URL_RESTAURANT+city);
        if(city!="")
            cityName.setText(city);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("RestaurantFrag","OnResume");
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AppConstants.CITY_REQUEST:
                switch (resultCode){
                    case AppConstants.CITY_RESULT_SUCCESS:
                        city = data.getStringExtra("city");
                        Log.d("city",city);
                }
        }
    }

    private void initView() {

        cityName = (TextView) getView().findViewById(R.id.cityNmae);

        noDataView = (RelativeLayout) getView().findViewById(R.id.noDataView);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.restaurant_recyler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));

        changeCity = (Button) getView().findViewById(R.id.changCity);
        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LetterSortActivity.class);
                startActivityForResult(intent,AppConstants.CITY_REQUEST);
            }
        });
    }

    private void initData(String city) {
        list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(AppConstants.URL_RESTAURANT+city)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject jo1 = new JSONObject(response.body().string());
                    int code = jo1.getInt("code");
                    Log.d("code","mycode"+code);
                    if(code==AppConstants.WITHOUT_RESTAURANT){
                        //mRecyclerView.setVisibility(View.GONE);
                        //noDataView.setVisibility(View.VISIBLE);
                        mHandler.obtainMessage(1).sendToTarget();//通过Handler更新界面
                    }
                    else{
                        JSONArray ja = new JSONArray(jo1.getString("data"));
                        Restaurant restaurant;
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject data = ja.getJSONObject(i);
                            String imageUrl = data.getString("imgurl");
                            String name = data.getString("name");
                            String addr = data.getString("address");
                            restaurant = new Restaurant(imageUrl, name, addr);
                            list.add(restaurant);
                        }

                        mHandler.obtainMessage(0).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
