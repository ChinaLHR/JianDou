package com.lhr.jiandou.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.lhr.jiandou.R;
import com.lhr.jiandou.utils.LogUtils;

/**
 * Created by 发条橙 on 2016/11/7.
 */

public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {
    private int mTopViewHeight;
    private OverScroller mScroller;
    private View mTop;
    private View mTab;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();
    }


    //判断如果纵向滑动返回true
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//        return true;
    }

    //判断，如果是上滑且顶部控件未完全隐藏，则消耗掉dy
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //getScrollY：返回视图滚动的最高位置
        //dy:垂直方向嵌套滑动的子View滑动的距离
        LogUtils.e("dx==="+dx+";dy==="+dy);
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }


    //当顶部控件显示时，fling(快速滑动)可以让顶部控件隐藏或者显示，返回值：父View是否消耗了fling
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //如果Y的滑动速度>mTopViewHeight，消耗掉fling
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    //嵌套滚动轴 0 = none 1 = HORIZONTAL 2 = VERTICAL
    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.leaderboard_topview);
        mTab = findViewById(R.id.leaderboard_ll);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mTab.getLayoutParams();
        params.height = getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + mTab.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //确定topview的高度
        mTopViewHeight = mTop.getMeasuredHeight();
    }

    //控制滑动范围,设置滚动视图的位置
    @Override
    public void scrollTo(int x, int y) {


        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    //计算拖动的位移量、更新背景
    @Override
    public void computeScroll() {
        //判断滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //滚动到新的Y点
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

}
