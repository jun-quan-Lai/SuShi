package com.ljq.sushi.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.RadioGroup;

import com.ljq.sushi.Fragment.BaiKeFrag;
import com.ljq.sushi.Fragment.MyFrag;
import com.ljq.sushi.Fragment.RestaurantFrag;
import com.ljq.sushi.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * 实现主界面框架
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private FragmentManager mFrgmanager;
    private Toolbar toolbar;
    private RadioGroup tabHost;  //主界面底部导航栏
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
        //对Android4.4以上的版本做状态栏和Toolbar的沉浸（只支持4.4以上）
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
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
        tabHost = (RadioGroup) findViewById(R.id.main_radioGroup);
        tabHost.setOnCheckedChangeListener(this);

        baiKeFrag = new BaiKeFrag();
        mFrgmanager.beginTransaction().add(R.id.main_container,baiKeFrag,"baike").commit();
        from = baiKeFrag;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.bai_ke:
                if(baiKeFrag==null){
                    baiKeFrag=new BaiKeFrag();
                    mFrgmanager.beginTransaction().hide(from).add(R.id.main_container,baiKeFrag,"baike").commit();
                    from = baiKeFrag;
                }

                else{
                    if(mFrgmanager.findFragmentByTag("baike")!=null){
                        mFrgmanager.beginTransaction().hide(from).show(baiKeFrag).commit();
                        from = baiKeFrag;
                    }
                    else
                        mFrgmanager.beginTransaction().add(R.id.main_container,baiKeFrag,"baike").commit();
                }

                break;
            case R.id.restaurant:
                if(restaurantFrag==null)
                {
                    restaurantFrag=new RestaurantFrag();
                    mFrgmanager.beginTransaction().hide(from).add(R.id.main_container,restaurantFrag,"res").commit();
                    from = restaurantFrag;
                }

                else
                {
                    if(mFrgmanager.findFragmentByTag("res")!=null)
                    {
                        mFrgmanager.beginTransaction().hide(from).show(restaurantFrag).commit();
                        from = restaurantFrag;
                    }

                    else
                        mFrgmanager.beginTransaction().add(R.id.main_container,restaurantFrag,"res").commit();
                }
                break;
            case R.id.me:
                if(myFrag==null){
                    myFrag=new MyFrag();
                    mFrgmanager.beginTransaction().hide(from).add(R.id.main_container,myFrag,"me").commit();
                    from=myFrag;
                }
                else{
                    if(mFrgmanager.findFragmentByTag("me")!=null)
                    {
                        mFrgmanager.beginTransaction().hide(from).show(myFrag).commit();
                        from=myFrag;
                    }

                    else
                        mFrgmanager.beginTransaction().add(R.id.main_container,myFrag,"me").commit();
                }
                break;
            default:
                break;
        }
    }


}
