package com.ljq.sushi.Fragment;


import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljq.sushi.Global.AppConstants;
import com.ljq.sushi.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.ljq.sushi.entity.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class BaiKe_tab1_Frag extends Fragment {

    RecyclerView mRecyclerView;
    private List<Article> list;

    private MyRecyclerViewAdapter adapter;


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

    }

    private void initData() {
        list = new ArrayList<Article>();

        final File baseDir = getActivity().getCacheDir();
        OkHttpClient client = new OkHttpClient();
        if(baseDir!=null){
            final File cacheDir = new File(baseDir,"HttpResponseCache");
            try {
                client.setCache(new Cache(cacheDir,HTTP_RESPONSE_DISK_CACHE_MAX_SIZE));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Request request = new Request.Builder()
                .url(AppConstants.URL_ARTICLE)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject jo1 = new JSONObject(response.body().string());
                    JSONArray ja = new JSONArray(jo1.getString("data"));
                    Article article;
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject data = ja.getJSONObject(i);
                        String imageUrl = data.getString("thumbnail");
                        String title = data.getString("title");
                        String summary = data.getString("ssumary");
                        article = new Article(imageUrl, title, summary);
                        list.add(article);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.obtainMessage(0).sendToTarget();
            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {

            }
        });
    }

    public static class MyRecyclerViewAdapter extends RecyclerView.Adapter {

        private List<Article> list;
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

        public MyRecyclerViewAdapter( List<Article> list) {

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bai_ke_tab1_listview_item,null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myholder = (MyViewHolder) holder;
            myholder.position=position;
            Article article = list.get(position);

            myholder.title.setText(article.getTitle());
            myholder.summary.setText(article.getSummary());
            if(list.get(position).getImageUrl()!=null)
            {
                ImageLoader.getInstance().displayImage(list.get(position).getImageUrl(),myholder.iv,options,animateFirstListener);
            }
            else
            {
                myholder.iv.setVisibility(View.GONE); //没有图片，则不显示
            }
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
            TextView title, summary;
            public int position;

            public MyViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.iv);
                title = (TextView) itemView.findViewById(R.id.title);
                summary = (TextView) itemView.findViewById(R.id.summary);

                //txtview.setOnClickListener(this);
                //txtview.setOnLongClickListener(this);

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



