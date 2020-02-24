package com.zza.library.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zza.library.R;
import com.zza.library.common.lmpl.IOnItemClickListener;

import java.util.List;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description:
 * @CreateDate: 2020/2/21 16:22
 * @UpdateDate: 2020/2/21 16:22
 * @UpdateRemark:
 * @Version: 1.0
 */

public class ListDialogAdapter extends RecyclerView.Adapter<ListDialogAdapter.ViewHolder> {

    private List<String> data;
    private Context context;
    private View view;
    private IOnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_type;

        public ViewHolder(View view) {
            super(view);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
        }
    }

    public ListDialogAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_dialog, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_type.setText(data.get(holder.getAdapterPosition()));
        view.setOnClickListener(v -> {
            //v.setBackgroundResource(R.drawable.item_bt_blue);
            //holder.tv_type.setTextColor(context.getResources().getColor(R.color.white));
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, holder.getAdapterPosition());
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
