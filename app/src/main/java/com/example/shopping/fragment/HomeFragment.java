package com.example.shopping.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.adapter.HomeDisCountAdapter;
import com.example.shopping.adapter.HomeTopicAdapter;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import me.crosswall.lib.coverflow.core.PagerContainer;

public class HomeFragment extends Fragment {
    private Banner home_banner;
    private RecyclerView rv;
    private ViewPager mTopicPager;
    private PagerContainer mTopicContainer;
    /*
     * 首页Banner
     */
    private String HOME_BANNER_ONE = "https://gitee.com/ccyy2019/server/raw/master/img05.jpg";
    private String HOME_BANNER_TWO = "https://gitee.com/ccyy2019/server/raw/master/img06.jpg";
    private String HOME_BANNER_THREE = "https://gitee.com/ccyy2019/server/raw/master/img07.jpg";
    private String HOME_BANNER_FOUR = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503471047&di=679d7a6c499f59d1b0dcd56b62a9aa6c&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.90sheji.com%2Fdianpu_cover%2F11%2F14%2F64%2F55%2F94ibannercsn_1200.jpg";

    /*
   首页折扣图片
 */
    private String HOME_DISCOUNT_ONE = "https://img14.360buyimg.com/n0/jfs/t3157/231/5756125504/98807/97ab361d/588084a1N06efb01d.jpg";
    private String HOME_DISCOUNT_TWO = "https://img10.360buyimg.com/n7/jfs/t5905/106/1120548052/61075/6eafa3a5/592f8f7bN763e3d30.jpg";
    private String HOME_DISCOUNT_THREE = "https://img10.360buyimg.com/n7/jfs/t5584/99/6135095454/371625/423b9ba5/59681d91N915995a7.jpg";
    private String HOME_DISCOUNT_FOUR = "https://img11.360buyimg.com/n7/jfs/t4447/301/1238553109/193354/13c7e995/58db19a7N25101fe4.jpg";
    private String HOME_DISCOUNT_FIVE = "https://img14.360buyimg.com/n1/s190x190_jfs/t7525/189/155179632/395056/e200017f/598fb8a4N7800dee6.jpg";
    /*
        首页话题图片
     */
    private String HOME_TOPIC_ONE = "https://gitee.com/ccyy2019/server/raw/master/img05.jpg";
    private String HOME_TOPIC_TWO = "https://gitee.com/ccyy2019/server/raw/master/img06.jpg";
    private String HOME_TOPIC_THREE = "https://gitee.com/ccyy2019/server/raw/master/img07.jpg";
    private String HOME_TOPIC_FOUR = "http://img.zcool.cn/community/01796c58772f66a801219c77e4fc04.png@1280w_1l_2o_100sh.png";
    private String HOME_TOPIC_FIVE = "http://img.zcool.cn/community/0154805791ffeb0000012e7edba495.jpg@900w_1l_2o_100sh.jpg";
    private ViewFlipper fp;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initBanner();
        initDiscount();
        initTopic();
        initNews();
        return view;
    }

    private void initNews() {
        //设置公告数据
        ArrayList<String> list = new ArrayList<>();
        list.add("夏日炎炎，第一波福利还有30秒到达战场");
        list.add("新用户立领1000元优惠券");

        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.title_view, null);
            TextView tvTitle = view.findViewById(R.id.tv_title_news);
            //赋值
            tvTitle.setText(list.get(i));
            //动态添加视图
            fp.addView(view);
        }

        //设置的时间间隔来开始切换所有的View,切换会循环进行
        fp.startFlipping();
        //视图进入动画
        fp.setInAnimation(getContext(), R.anim.news_in);
        //视图退出动画
        fp.setOutAnimation(getContext(), R.anim.news_out);
        //自动开始滚动
        fp.setAutoStart(true);
        //视图的切换间隔
        fp.setFlipInterval(3000);
    }

    private void initTopic() {
        ArrayList<String> list = new ArrayList<>();
        list.add(HOME_TOPIC_ONE);
        list.add(HOME_TOPIC_TWO);
        list.add(HOME_TOPIC_THREE);
        list.add(HOME_TOPIC_FOUR);
        list.add(HOME_TOPIC_FIVE);
        mTopicPager.setPageTransformer(true, new GallyPageTransformer());
        HomeTopicAdapter adapter = new HomeTopicAdapter(getActivity(), list);
        mTopicPager.setAdapter(adapter);


    }

    private void initDiscount() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        rv.setLayoutManager(linearLayoutManager);

        ArrayList<String> list = new ArrayList<>();
        list.add(HOME_DISCOUNT_ONE);
        list.add(HOME_DISCOUNT_TWO);
        list.add(HOME_DISCOUNT_THREE);
        list.add(HOME_DISCOUNT_FOUR);
        list.add(HOME_DISCOUNT_FIVE);
        HomeDisCountAdapter adapter = new HomeDisCountAdapter(getActivity(), list);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void initBanner() {
        ArrayList<String> images = new ArrayList<>();
        images.add(HOME_BANNER_ONE);
        images.add(HOME_BANNER_TWO);
        images.add(HOME_BANNER_THREE);
        images.add(HOME_BANNER_FOUR);

        home_banner.setImages(images)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        Glide.with(context).load(path).into(imageView);
                    }
                }).start();


    }

    private void initView(View view) {
        home_banner = view.findViewById(R.id.home_banner);
        rv = view.findViewById(R.id.rv);
        mTopicContainer = view.findViewById(R.id.mTopicContainer);
        mTopicPager = view.findViewById(R.id.mTopicPager);
        fp = view.findViewById(R.id.fp);
    }

}
