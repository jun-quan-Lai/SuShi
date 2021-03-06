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
import android.widget.LinearLayout;

import com.ljq.sushi.R;
import com.ljq.sushi.UI.DividerItemDecoration;

public class BaiKe_tab2_Frag extends Fragment  {

    RecyclerView mRecyclerView;

    private MyRecyclerViewAdapter adapter;

    public static BaiKe_tab2_Frag newInstance(){
        BaiKe_tab2_Frag fragment = new BaiKe_tab2_Frag();
        return fragment;
    }

    public BaiKe_tab2_Frag() {
        // Required empty public constructor
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter = new MyRecyclerViewAdapter();
                    adapter.setOnRecyclerViewListener(new MyRecyclerViewAdapter.OnRecyclerViewListener() {
                        @Override
                        public void onItemClick(int position) {

                        }

                        @Override
                        public boolean onItemLongClick(int position) {
                            return false;
                        }
                    });
                    mRecyclerView.setAdapter(adapter);
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
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.baike_tab2_recyler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));

    }
    private void initdata() {


    }


    public static class MyRecyclerViewAdapter extends RecyclerView.Adapter {



        public  interface OnRecyclerViewListener {
            void onItemClick(int position);
            boolean onItemLongClick(int position);
        }
        private OnRecyclerViewListener onRecyclerViewListener;

        public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener){
            this.onRecyclerViewListener = onRecyclerViewListener;
        }


        public MyRecyclerViewAdapter(){

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bai_ke_tab2_listview_item,null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myholder = (MyViewHolder) holder;
            myholder.position=position;

        }

        @Override
        public int getItemCount() {
                return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
                View.OnLongClickListener{


            public int position;

            public MyViewHolder(View itemView) {
                super(itemView);
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

    }
}

