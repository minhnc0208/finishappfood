package com.duanmot.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.duanmot.myapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    //Array
    public int[] slide_images = {
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3

    };

    public String[] slide_heading = {
            "Nấu và chia sẻ món ngon cùng cộng đồng",
            "Cùng nấu và chia sẻ đam mê",
            "Lưu lại món ăn yêu thích, đánh giá và bình luận món ăn"
    };

    public String[] slide_description = {
            "Cùng chung tay xây dựng một cộng đồng đam mê nấu ăn đúng nghĩa cho người Việt",
            "Chia sẻ nguyên liệu, các bước nấu ăn và món ăn đã thực hiện",
            "Nếu có ý tưởng cho một món ăn ngon. Hãy lưu lại và thực hiện sau ở mục yêu thích. Đóng góp ý kiến riêng của bản thân"
    };


    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide,container,false);
        CircleImageView img_slide = view.findViewById(R.id.img_slide1);
        TextView tv_Heading = view.findViewById(R.id.tv_HeadingSlide);
        TextView tv_Info = view.findViewById(R.id.tv_InfoSlide);

        img_slide.setImageResource(slide_images[position]);
        tv_Heading.setText(slide_heading[position]);
        tv_Info.setText(slide_description[position]);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);

    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
