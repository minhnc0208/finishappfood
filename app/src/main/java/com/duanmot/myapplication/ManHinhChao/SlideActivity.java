package com.duanmot.myapplication.ManHinhChao;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.duanmot.myapplication.Adapter.SlideAdapter;
import com.duanmot.myapplication.LoginActivity;
import com.duanmot.myapplication.R;

public class SlideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView[] mDots;
    private SlideAdapter slideAdapter;
    private Button btnBack, btnNext, btnSkip;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager = findViewById(R.id.viewPager);
        linearLayout = findViewById(R.id.dotslinnear);
        btnBack = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SlideActivity.this, LoginActivity.class));
            }
        });

        slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(viewListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentPage == mDots.length - 1) {
                    Intent intent = new Intent(SlideActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    viewPager.setCurrentItem(mCurrentPage + 1);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void addDots(int position) {
        mDots = new TextView[3];
        linearLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.a6gray));
            linearLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.black));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            mCurrentPage = position;
            if (position == 0) {
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);
                btnNext.setText("NEXT");
                btnBack.setText("");
            } else if (position == mDots.length - 1) {
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("FINISH");
                btnBack.setText("BACK");
            } else {
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("NEXT");
                btnBack.setText("BACK");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
