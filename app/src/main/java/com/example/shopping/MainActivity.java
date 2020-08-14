package com.example.shopping;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.shopping.fragment.CartFragment;
import com.example.shopping.fragment.CategoryFragment;
import com.example.shopping.fragment.HomeFragment;
import com.example.shopping.fragment.MsgFragment;
import com.example.shopping.fragment.UserFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public class MainActivity extends RxAppCompatActivity {
    private BottomNavigationBar mBottomNavigationBar;
    private FragmentManager fm;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private CartFragment cartFragment;
    private MsgFragment msgFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化底部导航
        initFragment();
        initListener();//底部监听
    }

    private void initView() {
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        //底部标题图片的设置
        BottomNavigationItem homeItem = new BottomNavigationItem(R.mipmap.btn_nav_home_press, "首页")
                .setInactiveIconResource(R.mipmap.btn_nav_home_normal);
        BottomNavigationItem categoryItem = new BottomNavigationItem(R.mipmap.btn_nav_category_press, "分类")
                .setInactiveIconResource(R.mipmap.btn_nav_category_normal);
        BottomNavigationItem cartItem = new BottomNavigationItem(R.mipmap.btn_nav_home_press, "购物车")
                .setInactiveIconResource(R.mipmap.btn_nav_cart_normal);
        BottomNavigationItem msgItem = new BottomNavigationItem(R.mipmap.btn_nav_msg_press, "消息")
                .setInactiveIconResource(R.mipmap.btn_nav_msg_normal);
        BottomNavigationItem userItem = new BottomNavigationItem(R.mipmap.btn_nav_user_press, "我的")
                .setInactiveIconResource(R.mipmap.btn_nav_user_normal);

        mBottomNavigationBar.addItem(homeItem)
                .addItem(categoryItem)
                .addItem(cartItem)
                .addItem(msgItem)
                .addItem(userItem)
                .setMode(BottomNavigationBar.MODE_FIXED)//底部导航的样式  固定模式，未选中Item显示文字
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//底部导航背景  静态样式，点击无波纹效果
                .setFirstSelectedPosition(0)
                .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#e9e6e6")//未选中颜色
                .initialise();//initialise 一定要放在 所有设置的最后一项

    }

    private void initFragment() {
        //得到fragment的碎片管理器
        fm = getSupportFragmentManager();
        //得到fragment对象
        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        cartFragment = new CartFragment();
        msgFragment = new MsgFragment();
        userFragment = new UserFragment();

        fm.beginTransaction()
                .add(R.id.fl, homeFragment)
                .add(R.id.fl, categoryFragment)
                .add(R.id.fl, cartFragment)
                .add(R.id.fl, msgFragment)
                .add(R.id.fl, userFragment)
                .hide(categoryFragment)
                .hide(cartFragment)
                .hide(msgFragment)
                .hide(userFragment)
                .commit();
    }

    private void initListener() {
        //设置监听
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        fm.beginTransaction().show(homeFragment)
                                .hide(categoryFragment)
                                .hide(cartFragment).hide(msgFragment)
                                .hide(userFragment)
                                .commit();
                        break;
                    case 1:
                        fm.beginTransaction().show(categoryFragment).hide(homeFragment)
                                .hide(cartFragment).hide(msgFragment)
                                .hide(userFragment)
                                .commit();
                        break;
                    case 2:
                        fm.beginTransaction().show(cartFragment).hide(homeFragment)
                                .hide(categoryFragment).hide(msgFragment)
                                .hide(userFragment)
                                .commit();
                        break;
                    case 3:
                        fm.beginTransaction().show(msgFragment).hide(homeFragment)
                                .hide(categoryFragment).hide(cartFragment)
                                .hide(userFragment)
                                .commit();
                        break;
                    case 4:
                        fm.beginTransaction().show(userFragment).hide(homeFragment)
                                .hide(categoryFragment).hide(msgFragment)
                                .hide(cartFragment)
                                .commit();
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}
