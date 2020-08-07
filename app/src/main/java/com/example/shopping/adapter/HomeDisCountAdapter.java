package com.example.shopping.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;

import java.util.ArrayList;

public class HomeDisCountAdapter extends RecyclerView.Adapter<HomeDisCountAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> list;

    public HomeDisCountAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_diacount, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).into(holder.mGoodsIconIv);
        //静态假数据
        holder.mDiscountAfterTv.setText("￥123.00");
        holder.mDiscountBeforeTv.setText("$1000.00");
        holder.mDiscountBeforeTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mGoodsIconIv;
        public TextView mDiscountAfterTv;
        public TextView mDiscountBeforeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mGoodsIconIv = (ImageView) itemView.findViewById(R.id.mGoodsIconIv);
            this.mDiscountAfterTv = (TextView) itemView.findViewById(R.id.mDiscountAfterTv);
            this.mDiscountBeforeTv = (TextView) itemView.findViewById(R.id.mDiscountBeforeTv);
        }
    }
}
