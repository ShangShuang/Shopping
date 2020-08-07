package com.example.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.shopping.R;

import java.util.ArrayList;

public class HomeTopicAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> list;

    public HomeTopicAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_topic, container, false);
        ImageView iv_img_topic = view.findViewById(R.id.iv_img_topic);
        TextView tv_life = view.findViewById(R.id.tv_life);
        TextView tv_topic_life = view.findViewById(R.id.tv_topic_life);
        Glide.with(context).load(list.get(position)).into(iv_img_topic);
        tv_life.setText("爱生活");
        tv_topic_life.setText("爱生活爱时尚");
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
