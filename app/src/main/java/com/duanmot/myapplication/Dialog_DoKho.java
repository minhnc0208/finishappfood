package com.duanmot.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import java.util.Collections;


public class Dialog_DoKho extends Dialog {
    ListView lvDoKho;
    Context context;
    String[] listDoKho = {"Dễ", "Trung Bình", "Khó"};
    ArrayList<String> dsDoKho;

    public Dialog_DoKho(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_dokho);
        setCanceledOnTouchOutside(false);
        lvDoKho = findViewById(R.id.lvDoKho);
        dsDoKho = new ArrayList<>();

        // add dữ liệu từ listDoKho sang ArrayList dsDoKho
        Collections.addAll(dsDoKho, listDoKho);

        //Thêm tiêu đề cho listview
        TextView textView = new TextView(context);
        textView.setText("Chọn Độ Khó");
        textView.setTextSize(25);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setPadding(50, 30, 35, 30);
        textView.setTextColor(Color.rgb(0, 0, 0));
        lvDoKho.addHeaderView(textView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, dsDoKho) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                // trong này là chỉnh màu cho textview bên trong listview ( chỉnh màu cho từng item)
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.rgb(0, 0, 0));
                tv.setPadding(120, 30, 35, 30);
                tv.setTextSize(20);
                return view;
            }
        };
        lvDoKho.setAdapter(arrayAdapter);
    }

}
