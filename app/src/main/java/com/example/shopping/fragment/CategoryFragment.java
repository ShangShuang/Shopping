package com.example.shopping.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httplibrary.utils.LogUtils;
import com.example.mvplibrary.base.BaseMvpFragment;
import com.example.shopping.R;
import com.example.shopping.activity.GoodsActivity;
import com.example.shopping.adapter.CategoryAdapter;
import com.example.shopping.adapter.GoodsAdapter;
import com.example.shopping.bean.CategoryGoods;
import com.example.shopping.bean.ReCatrgoryPrams;
import com.example.shopping.contract.CategoryContract;
import com.example.shopping.presenter.CateGoryPresenterImp;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseMvpFragment<CategoryContract.CategoryView, CateGoryPresenterImp> implements CategoryContract.CategoryView {


    private Toolbar tool_bar_category;
    private RecyclerView rv_category;
    private RecyclerView rv_goods;
    private ArrayList<String> tab;
    private CategoryAdapter adapter;
    private ArrayList<CategoryGoods> goods;
    private GoodsAdapter adapter1;
    private ReCatrgoryPrams reCatrgoryPrams;
    private ImageView iv_category_banner;
    private TextView tv_hot;
    private TextView tv_S;


    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initPData() {
        reCatrgoryPrams = new ReCatrgoryPrams();
        reCatrgoryPrams.setParentId("0");
        adapter.isSelect = true;
        mPresenter.getCategoryTab(reCatrgoryPrams);

        reCatrgoryPrams.setParentId("1");
        mPresenter.getCategoryGoods(reCatrgoryPrams);
    }

    @Override
    protected CateGoryPresenterImp initPresenter() {
        return new CateGoryPresenterImp();
    }

    @Override
    protected void initView(View rootView) {

        rv_category = rootView.findViewById(R.id.rv_category);
        rv_category.setLayoutManager(new LinearLayoutManager(getActivity()));
        tab = new ArrayList<>();
        rv_category.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new CategoryAdapter(getActivity(), tab);
        rv_category.setAdapter(adapter);

        rv_goods = rootView.findViewById(R.id.rv_goods);
        rv_goods.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        goods = new ArrayList<>();
        adapter1 = new GoodsAdapter(getActivity(), goods);
        rv_goods.setAdapter(adapter1);

        initListener();
    }

    private void initListener() {

        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    int parentId = position + 1;
                    adapter.mPosition = position;
                    goods.clear();
                    reCatrgoryPrams.setParentId(parentId + "");
                    mPresenter.getCategoryGoods(reCatrgoryPrams);
                    adapter.notifyDataSetChanged();
                } else if (position == 1) {
                    int parentId = position + 1;
                    reCatrgoryPrams.setParentId(parentId + "");
                    goods.clear();
                    adapter.mPosition = position;
                    mPresenter.getCategoryGoods(reCatrgoryPrams);
                    adapter.notifyDataSetChanged();
                } else {
                 /*   adapter.mPosition = position;
                    rv_goods.setVisibility(View.GONE);
                    tv_hot.setVisibility(View.GONE);
                    iv_category_banner.setVisibility(View.GONE);
                    tv_S.setVisibility(View.VISIBLE);*/
                    goods.clear();
                    adapter.notifyDataSetChanged();
                }
                adapter.setmPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        adapter1.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                int id = goods.get(position).getId();
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void showCategoryTab(List<String> tabs) {
        LogUtils.e(tabs.toString());
        tab.addAll(tabs);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoryGoods(List<CategoryGoods> goods) {
        Log.i("liangxq", goods.toString());
        //局部变量
        this.goods.addAll(goods);
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onError(String msg, int code) {
        LogUtils.e(msg);
    }

    @Override
    public void onCancle() {

    }
}
