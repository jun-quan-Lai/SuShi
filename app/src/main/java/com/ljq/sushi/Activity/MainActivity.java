package com.ljq.sushi.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ljq.sushi.Fragment.BaiKeFrag;
import com.ljq.sushi.Fragment.LiveFragment;
import com.ljq.sushi.Fragment.MyFrag;
import com.ljq.sushi.Fragment.RestaurantFrag;
import com.ljq.sushi.R;


/**
 * 实现主界面框架
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private FragmentManager mFrgmanager;
    private Toolbar toolbar;
    private BottomNavigationBar bottomNavigationBar; //主界面底部导航栏
    private BaiKeFrag baiKeFrag;
    private RestaurantFrag restaurantFrag;
    private LiveFragment liveFragment;
    private MyFrag myFrag;
    private Fragment from;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //不做实现,解决Fragment放置后台很久（Home键退出很长时间），返回时出现Fragment重叠
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrgmanager = getSupportFragmentManager();
        initView();
    }
    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null)
            setSupportActionBar(toolbar);

        bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.bottom_baike,"百科"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_restaurant,"素馆"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_live,"素食生活"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_me,"我"))
                .setActiveColor(R.color.color_primary)//背景色
                .setInActiveColor("#B2EBC8")//未选中色
                .setBarBackgroundColor(R.color.white)//选中色
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);

        setDefaultFragment();
    }
    private void setDefaultFragment() {
        baiKeFrag = BaiKeFrag.newInstance();
        mFrgmanager.beginTransaction().add(R.id.main_container,baiKeFrag,"baike").commit();
        from = baiKeFrag;
    }


    @Override
    public void onTabSelected(int position) {

        //开启事务
        FragmentTransaction transaction = mFrgmanager.beginTransaction();
        switch (position){
            case 0:
                if(baiKeFrag==null){
                    baiKeFrag=BaiKeFrag.newInstance();
                    transaction.hide(from).add(R.id.main_container,baiKeFrag,"baike");
                    from = baiKeFrag;
                }

                else{
                    if(mFrgmanager.findFragmentByTag("baike")!=null){
                        transaction.hide(from).show(baiKeFrag);
                        getSupportActionBar().show(); //显示toolbar
                        from = baiKeFrag;
                    }
                    else
                        transaction.add(R.id.main_container,baiKeFrag,"baike");
                }

                break;
            case 1:
                if(restaurantFrag==null)
                {
                    restaurantFrag=RestaurantFrag.newInstance();
                    transaction.hide(from).add(R.id.main_container,restaurantFrag,"res");
                    getSupportActionBar().hide();//隐藏toolbar
                    from = restaurantFrag;
                }

                else
                {
                    if(mFrgmanager.findFragmentByTag("res")!=null)
                    {
                        transaction.hide(from).show(restaurantFrag);
                        getSupportActionBar().hide();//隐藏toolbar
                        from = restaurantFrag;
                    }

                    else
                        transaction.add(R.id.main_container,restaurantFrag,"res");
                }
                break;
            case 2:
                if(liveFragment==null){
                    liveFragment = LiveFragment.newInstance();
                    transaction.hide(from).add(R.id.main_container,liveFragment,"live");
                    getSupportActionBar().hide();//隐藏toolbar
                    from=liveFragment;
                }
                else{
                    if(mFrgmanager.findFragmentByTag("live")!=null){
                        transaction.hide(from).show(liveFragment);
                        getSupportActionBar().hide();//隐藏toolbar
                        from=liveFragment;
                    }
                    else
                        transaction.add(R.id.main_container,liveFragment,"live");
                }
                break;
            case 3:
                if(myFrag==null){
                    myFrag= MyFrag.newInstance();
                    transaction.hide(from).add(R.id.main_container,myFrag,"me");
                    getSupportActionBar().hide();//隐藏toolbar
                    from=myFrag;
                }
                else{
                    if(mFrgmanager.findFragmentByTag("me")!=null)
                    {
                        transaction.hide(from).show(myFrag);
                        getSupportActionBar().hide();//隐藏toolbar
                        from=myFrag;
                    }
                    else
                        transaction.add(R.id.main_container,myFrag,"me");
                }
                break;
            default:
                break;
        }

        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


}
