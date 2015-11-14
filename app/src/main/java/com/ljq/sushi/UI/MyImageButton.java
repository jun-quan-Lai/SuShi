package com.ljq.sushi.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljq.sushi.R;

/**
 * 实现带文字的ImageButton
 */
public class MyImageButton extends LinearLayout {

    private ImageView imageViewbutton;

    private TextView textView;

    public MyImageButton(Context context,AttributeSet attrs) {
        super(context,attrs);
        // TODO Auto-generated constructor stub

        imageViewbutton = new ImageView(context, attrs);

        imageViewbutton.setPadding(0, 0, 0, 0);

        textView =new TextView(context, attrs);
        //水平居中
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        textView.setPadding(0, 0, 0, 0);

        setClickable(true);

        setFocusable(true);

        setOrientation(LinearLayout.VERTICAL);

        addView(imageViewbutton);

        addView(textView);

    }

}
