package com.ljq.sushi.Fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ljq.sushi.R;
import com.ljq.sushi.UI.View.defineView1;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/11/8.
 */
public class MyFrag  extends Fragment{


    private defineView1 collectionShop;

    private LinearLayout mExpandView;
    private ValueAnimator mAnimator;

    public static MyFrag newInstance(){
        MyFrag fragment = new MyFrag();
        return fragment;
    }

    public MyFrag(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       /* setExpandView();
        collectionShop = (defineView1) getView().findViewById(R.id.shop);
        collectionShop.setClickViewListener(new defineView1.ClickViewListener() {
            @Override
            public void rightImgClick() {
                if (mExpandView.getVisibility()==View.GONE){
                    collectionShop.setRightDrawable(getResources().getDrawable(R.mipmap.ic_error));
                    expand(mExpandView);
                }else{
                    collectionShop.setRightDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                    collapse(mExpandView);
                }
            }
        });*/
    }

    /*private void setExpandView() {
        mExpandView = (LinearLayout) getView().findViewById(R.id.mExpandView);

        mExpandView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mExpandView.getViewTreeObserver().removeOnPreDrawListener(this);
                mExpandView.setVisibility(View.GONE);

                final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                mExpandView.measure(widthSpec, heightSpec);

                mAnimator = slideAnimator(mExpandView,0, mExpandView.getMeasuredHeight());
                return true;
            }
        });
    }

    private void expand(View view) {
        //set Visible
        view.setVisibility(View.VISIBLE);

		*//* Remove and used in preDrawListener
		final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		mExpandView.measure(widthSpec, heightSpec);

		mAnimator = slideAnimator(0, mExpandView.getMeasuredHeight());
		*//*

        mAnimator.start();
    }

    private void collapse(View view) {
        int finalHeight = view.getHeight();

        ValueAnimator mAnimator = slideAnimator(view,finalHeight, 0);
        mAnimator.setDuration(0);

        final View thisview=view;
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                thisview.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(View view, int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        final View thisview=view;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = thisview.getLayoutParams();
                layoutParams.height = value;
                thisview.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
