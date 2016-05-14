package com.ljq.sushi.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.R;
import com.ljq.sushi.citylist.LetterSortActivity;
import com.ljq.sushi.entity.Restaurant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
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
import java.util.Collections;
import java.util.LinkedList;
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

    private MyRecyclerViewAdapter adapter;

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
                    adapter = new MyRecyclerViewAdapter(list);
                    mRecyclerView.setAdapter(adapter);
                    adapter.setOnRecyclerViewListener(new MyRecyclerViewAdapter.OnRecyclerViewListener() {
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
                    cityName.setText(city);
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
        Log.d("restaurantUrl",AppConstants.URL_RESTAURANT+city);
        //initData(city);在这里实现也会遇到坑

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("RestaurantFrag","onStart");
        initData(city);
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


    public static class MyRecyclerViewAdapter extends RecyclerView.Adapter {

        private List<Restaurant> list;
        private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
        private DisplayImageOptions options;

        public  interface OnRecyclerViewListener {
            void onItemClick(int position);
            boolean onItemLongClick(int position);
        }
        private OnRecyclerViewListener onRecyclerViewListener;

        public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
            this.onRecyclerViewListener = onRecyclerViewListener;
        }


        public MyRecyclerViewAdapter(List<Restaurant> list){
            this.list = list;
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_stub)
                    .showImageForEmptyUri(R.mipmap.ic_empty)
                    .showImageOnFail(R.mipmap.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .displayer(new SimpleBitmapDisplayer())
                    .build();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item,null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myholder = (MyViewHolder) holder;
            myholder.position=position;

            Restaurant restaurant = list.get(position);
            myholder.name.setText(restaurant.getRestaurantName());
            myholder.address.setText(restaurant.getAddress());

            if(restaurant.getImgUrl()!=null)
                ImageLoader.getInstance().displayImage(restaurant.getImgUrl(),myholder.iv,options,animateFirstListener);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
                View.OnLongClickListener{


            ImageView iv;
            TextView name, address;
            public int position;

            public MyViewHolder(View itemView) {
                super(itemView);
                iv= (ImageView) itemView.findViewById(R.id.restaurant_img);
                name = (TextView) itemView.findViewById(R.id.restaurant_name);
                address = (TextView) itemView.findViewById(R.id.restaurant_address);
            }

            @Override
            public void onClick(View v) {
                if(null != onRecyclerViewListener){
                    onRecyclerViewListener.onItemClick(position);
                }
            }

            @Override
            public boolean onLongClick(View v) {
                if(null != onRecyclerViewListener){
                    onRecyclerViewListener.onItemLongClick(position);
                }
                return false;
            }

        }

        private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
            static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(loadedImage != null){
                    ImageView imageView = (ImageView)view;
                    boolean firstDisplay = !displayedImages.contains(imageUri);
                    if(firstDisplay){
                        FadeInBitmapDisplayer.animate(imageView,500);
                        displayedImages.add(imageUri);
                    }
                }
            }
        }

    }
}
