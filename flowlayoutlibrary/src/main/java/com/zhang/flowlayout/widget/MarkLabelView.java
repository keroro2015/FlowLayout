package com.zhang.flowlayout.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.flowlayout.R;
import com.zhang.flowlayout.inter.DataItem;

import java.util.List;

/**
 * Created by zhang on 2016/6/22.
 */
public class MarkLabelView extends LinearLayout {
    private FlowLayout mainLayout;
    private int ITEM_MARGIN;

    public MarkLabelView(Context context) {
        this(context,null);
    }

    public MarkLabelView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MarkLabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.mark_label_view,this);
        mainLayout = (FlowLayout) findViewById(R.id.main);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        ITEM_MARGIN= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5, metrics);
    }

    public void setDatas(List<? extends DataItem> datas){
        mainLayout.removeAllViews();
        for (DataItem data : datas) {
            addData(data);
        }
    }

    public void addData(DataItem data){
        mainLayout.addView(createView(data,true));
    }

    private View createView(final DataItem data, boolean cancelable){
        final View view= View.inflate(getContext(), R.layout.mark_item_view,null);
        TextView tv= (TextView) view.findViewById(R.id.tv_label);

        int wrap_content= FlowLayout.LayoutParams.WRAP_CONTENT;
        FlowLayout.LayoutParams params=new FlowLayout.LayoutParams(wrap_content,wrap_content);
        params.setMargins(ITEM_MARGIN,ITEM_MARGIN,ITEM_MARGIN,ITEM_MARGIN);
        view.setLayoutParams(params);

        tv.setText(data.getText());

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_label);
        if (cancelable) {

            imageView.setVisibility(VISIBLE);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   mainLayout.removeView(view);
                }
            });
        }else {
            imageView.setVisibility(INVISIBLE);
        }

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), data.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
