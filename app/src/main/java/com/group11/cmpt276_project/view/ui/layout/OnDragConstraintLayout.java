package com.group11.cmpt276_project.view.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class OnDragConstraintLayout extends ConstraintLayout {

    private IDragCallBack dragCallBack;
    private GestureDetector gestureDetector;


    public OnDragConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void setDragCallBack(IDragCallBack listener) {
        this.dragCallBack = listener;
    }

    public interface IDragCallBack {
        void onDrag();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        this.gestureDetector.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    private class GestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(dragCallBack != null) {
                dragCallBack.onDrag();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
