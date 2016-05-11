package com.ljq.sushi.UI.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ExpandView extends FrameLayout {
    public ExpandView(Context context) {
        this(context,null);
    }

    public ExpandView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
