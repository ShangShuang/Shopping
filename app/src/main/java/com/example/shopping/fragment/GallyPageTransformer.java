package com.example.shopping.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

class GallyPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setPivotX(0);
        if (position < -1 || position > 1) {
            page.setRotationY(0);
        } else {
            if (position < 0) {
                page.setPivotX(page.getWidth());
                page.setRotationY((position) * 30);
                page.setScaleX(1 + (position / 2));
            } else {
                page.setPivotX(0);
                page.setRotationY(position * 30);
                page.setScaleX(1 - (position / 2));
            }
        }
    }
}