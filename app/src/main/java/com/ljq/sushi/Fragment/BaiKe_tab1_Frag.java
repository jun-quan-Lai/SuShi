package com.ljq.sushi.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljq.sushi.Adapter.BaikeTab1Adapter;
import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.R;
import com.ljq.sushi.UI.DividerItemDecoration;
import com.ljq.sushi.entity.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BaiKe_tab1_Frag extends Fragment {

    RecyclerView mRecyclerView;
    private List<Article> list;

    private BaikeTab1Adapter adapter;


    private final int  HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    public static BaiKe_tab1_Frag newInstance(){
        BaiKe_tab1_Frag fragment = new BaiKe_tab1_Frag();
        return fragment;
    }

    public BaiKe_tab1_Frag() {
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter = new BaikeTab1Adapter(list);
                    mRecyclerView.setAdapter(adapter);
                    adapter.setOnRecyclerViewListener(new BaikeTab1Adapter.OnRecyclerViewListener() {
                        @Override
                        public void onItemClick(int position) {

                        }

                        @Override
                        public boolean onItemLongClick(int position) {
                            return false;
                        }
                    });
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_baike_tab1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        initData();

    }


    private void initView() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.baike_tab1_recyler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));

    }

    private void initData() {
        list = new ArrayList<Article>();

      final File baseDir = getActivity().getCacheDir();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(baseDir!=null){
            final File cacheDir = new File(baseDir,"HttpResponseCache");
            builder.cache(new Cache(cacheDir,HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
        }
        OkHttpClient client = builder.build();

        Request request = new Request.Builder()
                .url(AppConstants.URL_ARTICLE)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                try {
                    JSONObject jo1 = new JSONObject(response.body().string());
                    JSONArray ja = new JSONArray(jo1.getString("data"));
                    Article article;
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject data = ja.getJSONObject(i);
                        String imageUrl = data.getString("thumbnail");
                        String title = data.getString("title");
                        String summary = data.getString("summary");
                        article = new Article(imageUrl, title, summary);
                        list.add(article);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.obtainMessage(0).sendToTarget();
            }

        });
    }
}