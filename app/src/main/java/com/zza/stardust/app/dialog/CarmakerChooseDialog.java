package com.zza.stardust.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.stardust.R;
import com.zza.stardust.app.adpter.CarmakerListAdapter;
import com.zza.stardust.bean.CarmakerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class CarmakerChooseDialog extends Dialog implements IOnItemClickListener {

    private Context context;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private CarmakerListAdapter adapter;
    private List<CarmakerBean> data = new ArrayList<>();


    public CarmakerChooseDialog(Context context, List<CarmakerBean> data) {
        super(context, R.style.ActionDialogStyle);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_carmaker);
        this.data.clear();
        this.data.addAll(data);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        tvTitle.setText("选择车企");
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter = new CarmakerListAdapter(context, data);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);

    }

    public void showDialog() {
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        //layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels*0.9);
        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels);
        layoutParams.y = 0; //距离屏幕底部的距离
        dialogWindow.setAttributes(layoutParams);
        show();
    }

    public abstract void onChooseReviewer(View view, int position);

    public void setData(List<CarmakerBean> data) {
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        onChooseReviewer(view, position);
    }
}
