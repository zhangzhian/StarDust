package com.zza.stardust.mine.adpter;

import android.content.Context;
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


public class MineListAdapter extends RecyclerView.Adapter<MineListAdapter.ViewHolder> {

    private List<AppInfoBean> data;
    private Context context;
    private IOnItemClickListener mListener;
    private View view;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;
        ImageView iv_info;

        public ViewHolder(View view) {
            super(view);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            iv_info = (ImageView) view.findViewById(R.id.iv_info);
        }
    }

    public MineListAdapter(Context context, List<AppInfoBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(v -> mListener.onItemClick(v, holder.getAdapterPosition()));
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_content.setText(data.get(holder.getAdapterPosition()).getName());
        holder.iv_info.setImageResource(data.get(holder.getAdapterPosition()).getImage());

    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

}
