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
import com.zza.library.utils.LogUtil;
import com.zza.stardust.R;
import com.zza.stardust.bean.AppInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private List<AppInfoBean> data;
    private Context context;
    private View view;
    private IOnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_type;

        public ViewHolder(View view) {
            super(view);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
        }
    }

    public AppAdapter(List<AppInfoBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_info, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /**
         *  holder.getAdapterPosition()
         *  使用 position 的话会位置错乱
         */
        holder.tv_type.setText(data.get(holder.getAdapterPosition()).getName());

        int sourceId = data.get(holder.getAdapterPosition()).getImage();
        Drawable imageSouce = null ;
        try {
            imageSouce = view.getResources().getDrawable(sourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (imageSouce == null) {
            imageSouce = view.getResources().getDrawable(R.drawable.ic_launcher_foreground);
        }

        holder.iv_image.setImageDrawable(imageSouce);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClick(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
