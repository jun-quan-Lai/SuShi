package com.ljq.sushi.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ljq.sushi.Fragment.BaiKeFrag;
import com.ljq.sushi.Fragment.MyFrag;
import com.ljq.sushi.Fragment.RestaurantFrag;
import com.ljq.sushi.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * 实现主界面框架
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private FragmentManager mFrgmanager;
    private Toolbar toolbar;
    BottomNavigationBar bottomNavigationBar; //主界面底部导航栏
    private BaiKeFrag baiKeFrag;
    private RestaurantFrag restaurantFrag;
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
        //对Android4.4做状态栏和Toolbar的沉浸（只支持4.4以上）
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams winParams = getWindow().getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            getWindow().setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.statusbar);
        }

        mFrgmanager = getSupportFragmentManager();
        initView();
    }
    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.bottom_baike,"百科"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_restaurant,"素食地图"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_me,"我"))
                .setActiveColor("#FFFFFF")
                .setInActiveColor("#ECECEC")
                .setBarBackgroundColor(R.color.green)
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
                    from = restaurantFrag;
                }

                else
                {
                    if(mFrgmanager.findFragmentByTag("res")!=null)
                    {
                        transaction.hide(from).show(restaurantFrag);
                        from = restaurantFrag;
                    }

                    else
                        transaction.add(R.id.main_container,restaurantFrag,"res");
                }
                break;
            case 2:
                if(myFrag==null){
                    myFrag= MyFrag.newInstance();
                    transaction.hide(from).add(R.id.main_container,myFrag,"me");
                    from=myFrag;
                }
                else{
                    if(mFrgmanager.findFragmentByTag("me")!=null)
                    {
                        transaction.hide(from).show(myFrag);
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
