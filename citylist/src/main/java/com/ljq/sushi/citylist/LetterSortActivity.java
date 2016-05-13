package com.ljq.sushi.citylist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ljq.sushi.citylist.common.T;
import com.ljq.sushi.citylist.db.City;
import com.ljq.sushi.citylist.db.MyCityDBHelper;

import java.util.ArrayList;
import java.util.List;

public class LetterSortActivity extends Activity implements View.OnClickListener {

    public static final String TAG = LetterSortActivity.class.getSimpleName();

    private Context context = LetterSortActivity.this;

    private Button btn_back;
    private ClearEditText mClearEditText;
    private TextView tv_mid_letter;
    private ListView listView;
    private MyLetterSortView right_letter;

    private ItemBeanAdapter mAdapter;
    private List<City> mlist = new ArrayList<City>();
    private InputMethodManager inputMethodManager;

    private View mCityContainer;
    private FrameLayout mSearchContainer;
    private ListView mSearchListView;
    private SearchCityAdapter mSearchCityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_letter_sort);
        initView();
        setLinstener();
        initData();
        fillData();
    }

    protected void initData() {

        getDataCity();
        mAdapter = new ItemBeanAdapter(this, mlist);
        listView.setEmptyView(findViewById(R.id.citys_list_load));
        listView.setAdapter(mAdapter);

    }

    protected void initView() {

        btn_back = (Button) findViewById(R.id.back);

        inputMethodManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        listView = (ListView) findViewById(R.id.list);
        mClearEditText = (ClearEditText) findViewById(R.id.et_msg_search);
        // ���������м���ĸ
        right_letter = (MyLetterSortView) findViewById(R.id.right_letter);
        tv_mid_letter = (TextView) findViewById(R.id.tv_mid_letter);
        right_letter.setTextView(tv_mid_letter);
        //����
        mCityContainer = findViewById(R.id.city_content_container);
        mSearchContainer = (FrameLayout) this.findViewById(R.id.search_content_container);
        mSearchListView = (ListView) findViewById(R.id.search_list);
        mSearchListView.setEmptyView(findViewById(R.id.search_empty));
        mSearchContainer.setVisibility(View.GONE);

    }

    protected void setLinstener() {


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // tv_reget_pwd.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                T.showShort(getApplicationContext(),
                        ((City) mAdapter.getItem(position)).toString());

            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // ���������
                if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(
                                getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        // �����Ҳഥ������
        right_letter
                .setOnTouchingLetterChangedListener(new MyLetterSortView.OnTouchingLetterChangedListener() {

                    @Override
                    public void onTouchingLetterChanged(String s) {
                        // ����ĸ�״γ��ֵ�λ��
                        int position = mAdapter.getPositionForSection(s
                                .charAt(0));
                        if (position != -1) {
                            listView.setSelection(position);
                        }

                    }
                });

        // �������������ֵ�ĸı�����������
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
                filterData2(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSearchListView
                .setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                    }
                });
    }

    protected void fillData() {
        // TODO Auto-generated method stub

    }

    private void getDataCity() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                MyCityDBHelper myCityDBHelper = new MyCityDBHelper(context);
                mlist = myCityDBHelper.getCityDB().getAllCity();
                mHandler.sendEmptyMessage(0);

            }
        }).start();

        // ��list��������
        // Collections.sort(mlist, new PinyinComparator() {
        // });

    }



    private void filterData2(String filterStr) {
        mSearchCityAdapter = new SearchCityAdapter(LetterSortActivity.this, mlist);
        mSearchListView.setAdapter(mSearchCityAdapter);
        mSearchListView.setTextFilterEnabled(true);
        if (mlist.size() < 1 || TextUtils.isEmpty(filterStr)) {
            mCityContainer.setVisibility(View.VISIBLE);
            mSearchContainer.setVisibility(View.INVISIBLE);

        } else {

            mCityContainer.setVisibility(View.INVISIBLE);
            mSearchContainer.setVisibility(View.VISIBLE);
            mSearchCityAdapter.getFilter().filter(filterStr);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //
            default:
                break;
        }

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    mAdapter.updateListView(mlist);
                    break;
                default:
                    break;
            }
        }
    };
}

