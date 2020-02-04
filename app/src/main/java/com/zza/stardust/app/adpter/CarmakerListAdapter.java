package com.zza.stardust.app.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.stardust.R;
import com.zza.stardust.bean.CarmakerBean;

import java.util.List;


public class CarmakerListAdapter extends RecyclerView.Adapter<CarmakerListAdapter.ViewHolder> {

    private List<CarmakerBean> data;
    private Context context;
    private IOnItemClickListener mListener;
    private View view;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content;

        public ViewHolder(View view) {
            super(view);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }
    }

    public CarmakerListAdapter(Context context, List<CarmakerBean> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carmaker_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_content.setText(data.get(holder.getAdapterPosition()).getCarmakeName());
        view.setOnClickListener(v -> mListener.onItemClick(v, holder.getAdapterPosition()));
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnItemClickListener(IOnItemClickListener listener) {
        mListener = listener;
    }

}
