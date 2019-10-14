package com.android.basic.summary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.basic.summary.R;

import java.util.ArrayList;

/**
 * author : Harris Luffy
 * e-mail : 744423651@qq.com
 * date   : 2019/10/10-10:01
 * desc   : MyActivity的适配器
 * version: 1.0
 */
public class MyActivityAdapter extends RecyclerView.Adapter<MyActivityAdapter.MyActivityViewHolder> {
    private ArrayList<String> mActivityNames;//此项目中所有activity的名字的集合
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    public MyActivityAdapter(ArrayList<String> mActivityNames, Context mContext) {
        this.mActivityNames = mActivityNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_activity, null, false);
        return new MyActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyActivityViewHolder myActivityViewHolder, final int position) {
        myActivityViewHolder.tvActivityName.setText(mActivityNames.get(position));
        myActivityViewHolder.llMyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mActivityNames == null ? 0 : mActivityNames.size();
    }

    public class MyActivityViewHolder extends RecyclerView.ViewHolder {
        private TextView tvActivityName;
        private LinearLayout llMyActivity;

        public MyActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityName = itemView.findViewById(R.id.tv_activity_name);
            llMyActivity = itemView.findViewById(R.id.ll_my_activity);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
