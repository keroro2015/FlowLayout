package com.zhang.fldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhang.flowlayout.inter.DataItem;
import com.zhang.flowlayout.widget.MarkLabelView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Data> list=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Data("label"+i));
        }

        for (int i = 0; i < 4; i++) {
            list.add(new Data("label10000"+i));
        }
        for (int i = 0; i < 4; i++) {
            list.add(new Data("label10000000"+i));
        }
        MarkLabelView marketView = (MarkLabelView) findViewById(R.id.market_view);
        marketView.setDatas(list);
    }

    static class Data implements DataItem {
        private String content;

        public Data() {
        }

        public Data(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String getText() {
            return getContent();
        }

        @Override
        public String toString() {
            return "Data{" +
                    "content='" + content + '\'' +
                    '}';
        }
    }
}