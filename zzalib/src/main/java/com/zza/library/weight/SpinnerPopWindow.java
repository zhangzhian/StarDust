package com.zza.library.weight;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zza.library.R;
import com.zza.library.common.lmpl.IOnItemClickListener;

import java.util.List;

/**
 * @Author: 张志安
 * @Mail: zhangzhian2016@gmail.com
 * <p>
 * 自定义SpinerPopWindow类
 */
public class SpinnerPopWindow extends PopupWindow implements OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private SpinnerAdapter mAdapter;
    private IOnItemClickListener mIOnItemClickListener;


    public SpinnerPopWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }


    public void setItemListener(IOnItemClickListener listener) {
        mIOnItemClickListener = listener;
    }

    public void setAdapter(SpinnerAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }


    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spiner_window_layout, null);
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);


        mListView = (ListView) view.findViewById(R.id.lv);
        mListView.setOnItemClickListener(this);
    }


    public void refreshData(List<String> list, int selIndex) {
        if (list != null && selIndex != -1) {
            if (mAdapter != null) {
                mAdapter.refreshData(list, selIndex);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        dismiss();
        if (mIOnItemClickListener != null) {
            mIOnItemClickListener.onItemClick(view, pos);
        }
    }
}
