package com.zza.stardust.app.ui.androidart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zza.library.base.BasePresenter;
import com.zza.library.common.lmpl.IOnItemClickListener;
import com.zza.stardust.R;
import com.zza.stardust.app.adpter.StrItemAdapter;
import com.zza.stardust.app.ui.androidart.animation.AnimationActivity;
import com.zza.stardust.app.ui.androidart.binderpool.BinderPoolActivity;
import com.zza.stardust.app.ui.androidart.bitmapcache.BitmapCacheActivity;
import com.zza.stardust.app.ui.androidart.crash.CrashActivity;
import com.zza.stardust.app.ui.androidart.customview.CustomViewActivity;
import com.zza.stardust.app.ui.androidart.remoteViews.RemoteViewsActivity;
import com.zza.stardust.app.ui.androidart.serializableParcelable.SerializableParcelableActivity1;
import com.zza.stardust.app.ui.androidart.aidl.BookManagerActivity;
import com.zza.stardust.app.ui.androidart.messenger.MessengerActivity;
import com.zza.stardust.app.ui.androidart.provider.ProviderActivity;
import com.zza.stardust.app.ui.androidart.slidingConflict.SlidingConflictExteralActivity;
import com.zza.stardust.app.ui.androidart.slidingConflict.SlidingConflictInterActivity;
import com.zza.stardust.app.ui.androidart.socket.TCPClientActivity;
import com.zza.stardust.app.ui.androidart.thread.AndroidThreadActivity;
import com.zza.stardust.app.ui.androidart.viewbase.ViewBaseActivity;
import com.zza.stardust.app.ui.androidart.window.WindowActivity;
import com.zza.stardust.base.MActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AndroidArtActivity extends MActivity implements IOnItemClickListener {

    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    private List<String> data = new ArrayList<>();
    private StrItemAdapter adapter = null;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_android_art;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        initData();
        initRecycleView();
    }

    private void initData() {
        data.clear();
        data.add("Serializable Parcelable");
        data.add("Messenger");
        data.add("AIDL");
        data.add("Provider");
        data.add("Socket");
        data.add("BinderPool");
        data.add("View Base");
        data.add("Sliding conflict: External interception");
        data.add("Sliding conflict: Internal interception");
        data.add("Custom View");
        data.add("Remote View");
        data.add("Animation");
        data.add("Window");
        data.add("Android Thread And ThreadPool");
        data.add("Bitmap Cache");
        data.add("Crash");

    }

    private void initRecycleView() {
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rvTest.setLayoutManager(manager);
        adapter = new StrItemAdapter(data, this);
        adapter.setOnItemClick(this);
        rvTest.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(AndroidArtActivity.this, SerializableParcelableActivity1.class));
                break;
            case 1:
                startActivity(new Intent(AndroidArtActivity.this, MessengerActivity.class));
                break;
            case 2:
                startActivity(new Intent(AndroidArtActivity.this, BookManagerActivity.class));
                break;
            case 3:
                startActivity(new Intent(AndroidArtActivity.this, ProviderActivity.class));
                break;
            case 4:
                startActivity(new Intent(AndroidArtActivity.this, TCPClientActivity.class));
                break;
            case 5:
                startActivity(new Intent(AndroidArtActivity.this, BinderPoolActivity.class));
                break;
            case 6:
                startActivity(new Intent(AndroidArtActivity.this, ViewBaseActivity.class));
                break;
            case 7:
                startActivity(new Intent(AndroidArtActivity.this, SlidingConflictExteralActivity.class));
                break;
            case 8:
                startActivity(new Intent(AndroidArtActivity.this, SlidingConflictInterActivity.class));
                break;
            case 9:
                startActivity(new Intent(AndroidArtActivity.this, CustomViewActivity.class));
                break;
            case 10:
                startActivity(new Intent(AndroidArtActivity.this, RemoteViewsActivity.class));
                break;
            case 11:
                startActivity(new Intent(AndroidArtActivity.this, AnimationActivity.class));
                break;
            case 12:
                startActivity(new Intent(AndroidArtActivity.this, WindowActivity.class));
                break;
            case 13:
                startActivity(new Intent(AndroidArtActivity.this, AndroidThreadActivity.class));
                break;
            case 14:
                startActivity(new Intent(AndroidArtActivity.this, BitmapCacheActivity.class));
                break;
            case 15:
                startActivity(new Intent(AndroidArtActivity.this, CrashActivity.class));
                break;
        }
    }
}
