package com.zza.library.weight;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zza.library.R;
import com.zza.library.adpter.ListDialogAdapter;
import com.zza.library.common.lmpl.IOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 张志安
 * @Mail: zhangzhian_123@qq.com zhangzhian2016@gmail.com
 * @Description: ListDialogAdapter配套使用
 * @CreateDate: 2020/2/21 15:32
 * @UpdateDate: 2020/2/21 15:32
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ListDialog extends Dialog {
    private Context context;
    private RecyclerView rv;
    private TextView tvTitle;

    //设置RecyclerView
    private List<String> data = new ArrayList<>();
    private ListDialogAdapter adapter;
    private IOnItemClickListener listener;
    public ListDialog(Context context, IOnItemClickListener listener, List<String> data, String title) {
        super(context, R.style.ActionDialogStyle);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list);
        rv = findViewById(R.id.rv);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        this.data.clear();
        this.data.addAll(data);
        this.listener = listener;
        initRecyclerView();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new ListDialogAdapter(data, context);
        adapter.setOnItemClick(listener);
        rv.setAdapter(adapter);
    }

    public void show() {
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
////        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels*0.9);
        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels);
        layoutParams.y = 0; //距离屏幕底部的距离
        dialogWindow.setAttributes(layoutParams);
        super.show();
    }

}
