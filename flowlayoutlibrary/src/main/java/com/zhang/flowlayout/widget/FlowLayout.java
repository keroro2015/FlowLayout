package com.zhang.flowlayout.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2016/6/21.
 */
public class FlowLayout extends ViewGroup {

    private int childCount;

    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    List<Rect> childRectList;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (childRectList == null) {
            childRectList=new ArrayList<>();
        }
        childRectList.clear();

        int parentWidth= MeasureSpec.getSize(widthMeasureSpec);
        int hMode= MeasureSpec.getMode(widthMeasureSpec);
        int parentHeight= MeasureSpec.getSize(heightMeasureSpec);
        int vMode= MeasureSpec.getMode(heightMeasureSpec);

        int lineHeight=0;
        int lineWidth=0;

        int height=0;
        int width=0;

        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lps = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth()+lps.leftMargin+lps.rightMargin;
            int childHeight=child.getMeasuredHeight()+lps.topMargin+lps.bottomMargin;

            Rect rect=new Rect();

            if (childWidth+lineWidth<=parentWidth){
                rect.left=lineWidth+lps.leftMargin;
                lineWidth+=childWidth;
                width= Math.max(lineWidth+lps.rightMargin,width);
                lineHeight= Math.max(childHeight,lineHeight);
            }
            else{
                rect.left=lps.leftMargin;
                width= Math.max(lineWidth+lps.rightMargin,width);
                lineWidth=childWidth;
                height+=lineHeight;
                lineHeight=childHeight;
            }

            rect.right=rect.left+childWidth-lps.rightMargin;
            rect.top=height+lps.topMargin;
            rect.bottom=rect.top+childHeight-lps.bottomMargin;
            childRectList.add(rect);

            if (i==childCount-1) {
                height+=lineHeight+lps.bottomMargin;
            }
        }


        int dimenWidth=hMode== MeasureSpec.EXACTLY?parentWidth:width;
        int dimenHeight=vMode== MeasureSpec.EXACTLY?parentHeight:height;

        Log.d("test", "height:" + dimenHeight+"  width:" + dimenWidth);
        setMeasuredDimension(dimenWidth,dimenHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            Rect rect=childRectList.get(i);
            //Log.d("test", "onLayout: "+rect.left+"/"+rect.top+"/"+rect.right+"/"+rect.bottom);
            child.layout(rect.left,rect.top,rect.right,rect.bottom);
        }
    }


    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
