package com.example.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.bean.CategoryGoods;

import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoryGoods> goods;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GoodsAdapter(Context context, ArrayList<CategoryGoods> goods) {
        this.context = context;
        this.goods = goods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_name.setText(goods.get(position).getCategoryName());
        Glide.with(context).load(goods.get(position).getCategoryIcon()).into(holder.iv_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_img;
        public TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    public interface OnItemClickListener {

        void onItemClickListener(int position);
    }
}
