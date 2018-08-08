package com.example.crazywah.postgraduaterefactoring3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class NestedRecyclerView extends RecyclerView {

    private static final String TAG = "NestedRecyclerView";

    private OnUpAndDownListener upAndDownListener = null;

    //    private ViewGroup parent;
    private float lastY = 0;

    public NestedRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NestedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //    public void setNestedpParent(ViewGroup parent) {
//        this.parent = parent;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (parent != null) {
//            parent.requestDisallowInterceptTouchEvent(true);
//        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float offest = 0;
                if (lastY == 0) {
                    lastY = ev.getRawY();
                } else {
                    offest = lastY - ev.getRawY();
                    lastY = ev.getRawY();
                    if (offest < 0) {
                        //下拉
                        if(upAndDownListener!=null){
                            if(upAndDownListener.onUp()){
                                return true;
                            }
                        }
                    } else {
                        //上拉
                        if(upAndDownListener!=null){
                            if(upAndDownListener.onDown()){
                                return true;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                lastY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        if (parent != null) {
//            parent.requestDisallowInterceptTouchEvent(true);
//        }
        return super.onInterceptTouchEvent(arg0);
    }

    public OnUpAndDownListener getUpAndDownListener() {
        return upAndDownListener;
    }

    public void setUpAndDownListener(OnUpAndDownListener upAndDownListener) {
        this.upAndDownListener = upAndDownListener;
    }

    public interface OnUpAndDownListener{
        boolean onUp();
        boolean onDown();
    }

}
