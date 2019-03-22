package com.yll.progresslayout.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yll.progresslayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 四种状态的LinearLayout，用于切换当前的页面显示
 * ERROR,CONTENT,LOADING，NODATA
 */

public class ProgressLayout extends LinearLayout {
    private static final String LOADING_TAG = "loading_tag";
    private static final String ERROR_TAG = "error_tag";
    private static final String NODATA_TAG = "nodata_tag";


    private LayoutParams layoutParams;
    private LayoutInflater layoutInflater;
    private LinearLayout loadingView, errorView;

    private TextView btn_error;

    private List<View> contentViews = new ArrayList<>();
//    private RotateAnimation rotateAnimation;-

    private AnimationDrawable animationDrawable;
    private LinearLayout noDataView;

    public ProgressLayout(Context context) {
        super(context);
    }

    private enum State {
        LOADING, CONTENT, ERROR,NODATA
    }

    private State currentState = State.LOADING;

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(LOADING_TAG) && !child.getTag().equals(ERROR_TAG) && !child.getTag().equals(NODATA_TAG))) {
            contentViews.add(child);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (btn_error != null) {
            btn_error.setOnClickListener(null);
        }
    }

    /**
     * 显示正在加载
     */
    public void showLoading() {
        currentState = State.LOADING;
        showLoadingView();
        hideErrorView();
        hideNoDataView();
        setContentVisibility(false);
    }

    /**
     * 显示数据内容
     */
    public void showContent() {
        //延迟500毫秒显示
//        Observable.timer(500, TimeUnit.MILLISECONDS)
//                .compose(SchedulersCompat.<Long>applyIoSchedulers())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//
//                    }
//                });
        currentState = State.CONTENT;
        setContentVisibility(true);
        hideLoadingView();
        hideErrorView();
        hideNoDataView();
    }

    /**
     * 显示错误
     * @param click
     */
    public void showError(OnClickListener click) {
        currentState = State.ERROR;
        hideLoadingView();
        hideNoDataView();
        showErrorView();
        btn_error.setOnClickListener(click);
        setContentVisibility(false);
    }

    /**
     * 显示无数据
     */
    public void showNoData(){
        currentState = State.NODATA;
        hideLoadingView();
        hideErrorView();
        showNoDataView();
        setContentVisibility(false);
    }


    public boolean isContent() {
        return currentState == State.CONTENT;
    }

    /**
     * 显示loading
     */
    private void showLoadingView() {
        if (loadingView == null) {
            loadingView = (LinearLayout) layoutInflater.inflate(R.layout.layout_loading_view, null);
            loadingView.setTag(LOADING_TAG);
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            ImageView iv_loading = (ImageView) loadingView.findViewById(R.id.iv_loading);
//            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//            rotateAnimation.setDuration(800);
//            rotateAnimation.setRepeatMode(Animation.RESTART);//重复
//            rotateAnimation.setRepeatCount(Animation.INFINITE);//无限旋转
//            rotateAnimation.start();
//            LinearInterpolator lir = new LinearInterpolator();//线性差值器,一直匀速旋转
//            rotateAnimation.setInterpolator(lir);
//            iv_loading.startAnimation(rotateAnimation);
//            this.addView(loadingView, layoutParams);
            animationDrawable = (AnimationDrawable) iv_loading.getDrawable();
            animationDrawable.start();
            addView(loadingView, layoutParams);

        } else {
            animationDrawable.start();
            loadingView.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示error
     */
    private void showErrorView() {
        if (errorView == null) {
            errorView = (LinearLayout) layoutInflater.inflate(R.layout.layout_error_view, null);
            errorView.setTag(ERROR_TAG);
            btn_error = (TextView) errorView.findViewById(R.id.btn_try);
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            this.addView(errorView, layoutParams);
        } else {
            errorView.setVisibility(VISIBLE);
        }
    }



    private void showNoDataView() {
        if (noDataView == null){
            Log.d("xxxxx", "showNoDataView: noDataView == null");
            noDataView = ((LinearLayout) layoutInflater.inflate(R.layout.layout_nodata_view, null));
            noDataView.setTag(NODATA_TAG);
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            this.addView(noDataView, layoutParams);
        } else {
            Log.d("xxxxx", "showNoDataView: noDataView VISIBLE");
            noDataView.setVisibility(VISIBLE);
        }
    }



    private void hideLoadingView() {
        if (loadingView != null && loadingView.getVisibility() != GONE) {
            loadingView.setVisibility(GONE);
            animationDrawable.stop();
        }
    }


    private void hideErrorView() {
        if (errorView != null && errorView.getVisibility() != GONE) {
            errorView.setVisibility(GONE);
        }
    }


    private void hideNoDataView(){
        if (noDataView != null && noDataView.getVisibility() != GONE){
            noDataView.setVisibility(GONE);
        }
    }

    /**
     * 设置是否显示 contentview
     * @param visible
     */
    public void setContentVisibility(boolean visible) {
        for (View contentView : contentViews) {
            contentView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }
}
