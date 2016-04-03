package com.ljq.sushi.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ljq.sushi.Activity.ContentActivity;
import com.ljq.sushi.R;
import com.sushi.news.spider.News;
import com.sushi.news.spider.NewsSpider;

import java.util.ArrayList;


public class BaiKe_tab2_Frag extends Fragment {

    private PullToRefreshListView lv;
    private ArrayList<News> list;
    private String HTTPURL="http://ss.zgfj.cn/SSZX/";
    private Myadapter adapter;

    public BaiKe_tab2_Frag() {
        // Required empty public constructor
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter = new Myadapter();
                    lv.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_baike_tab2,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initdata();
    }
    private void initView() {
        lv = (PullToRefreshListView) getView().findViewById(R.id.lv_bai_ke_tab2);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ContentActivity.class);
                intent.putExtra("contentUrl",list.get(position).getUrl());
                startActivity(intent);
            }
        });
    }
    private void initdata() {

        final NewsSpider spider = new NewsSpider();
        new Thread(){
            public void run(){
            list = spider.getNewList(HTTPURL);
            mHandler.obtainMessage(0).sendToTarget();
        }}.start();


    }

    public class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                convertView = inflater.inflate(R.layout.bai_ke_tab2_listview_item, parent,false);
                holder = new ViewHolder();
                holder.txtview = (TextView) convertView.findViewById(R.id.bai_ke_tab2_list_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            News news = list.get(position);
            holder.txtview.setText(news.getTitle());
            return convertView;
        }
    }

    public class ViewHolder{
        TextView txtview;
    }

}
