package com.zza.stardust.app.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.stardust.R;
import com.zza.stardust.bean.AppInfoBean;

import java.util.List;

/**
 * Created by zza on 2019/11/04.
 */

public class StrItemAdapter extends RecyclerView.Adapter<StrItemAdapter.ViewHolder> {

    private List<String> data;
    private Context context;
    private View view;
    private IOnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public ViewHolder(View view) {
            super(view);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }
    }

    public StrItemAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_str_info, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /**
         *  holder.getAdapterPosition()
         *  使用 position 的话会位置错乱
         */
        holder.tv_content.setText(data.get(holder.getAdapterPosition()).toString());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClick(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
