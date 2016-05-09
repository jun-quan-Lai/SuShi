package com.ljq.sushi.UI.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljq.sushi.R;

/**
 * Created by Administrator on 2016/5/9.
 */
public class defineView1 extends RelativeLayout{

    private ImageView leftImg;
    private TextView midText;
    private ImageView rightImg;
    private String text;
    private float textSize;

    private Drawable leftDrawable;
    private Drawable rightDrawable;


    public defineView1(Context context) {
        this(context,null);
    }

    public defineView1(Context context, AttributeSet attrs) {
        this(context, attrs,0);

        LayoutInflater.from(context).inflate(R.layout.define_view1,this,true);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.defineView1);
        text=a.getString(R.styleable.defineView1_midText);
        textSize=a.getDimension(R.styleable.defineView1_textSize,20f);
        leftDrawable = a.getDrawable(R.styleable.defineView1_leftImg);
        rightDrawable = a.getDrawable(R.styleable.defineView1_rightImg);
        if(leftDrawable==null || rightDrawable==null){
            throw new RuntimeException("图像资源为空");
        }

        a.recycle();
    }

    public defineView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftImg = (ImageView) findViewById(R.id.img_left);
        midText = (TextView) findViewById(R.id.txt_mid);
        rightImg = (ImageView) findViewById(R.id.img_right);

        if(!TextUtils.isEmpty(text)){
            setMidText(text);
            setMidTextSize(textSize);
        }
        setLeftDrawable(leftDrawable);
        setRightDrawable(rightDrawable);
    }

    public void setMidText(String text){
        midText.setText(text);
    }

    public void setMidTextSize(float textSize){
        midText.setTextSize(textSize);
    }

    public void setLeftDrawable(Drawable drawable){
        leftImg.setImageDrawable(drawable);
    }
    public void setRightDrawable(Drawable drawable){
        rightImg.setImageDrawable(drawable);
    }
    public void setOnRightImgClickListener(OnClickListener listener){
        rightImg.setOnClickListener(listener);
    }
}
