package com.zza.stardust.app.ui.androidart.remoteViews;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;
import com.zza.stardust.common.MConstant;

import android.os.Process;

public class RemotoViewTestActivity extends MActivity {


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_remoto_view_test;
    }

    public void onButtonClick(View view) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
        remoteViews.setTextViewText(R.id.msg, "msg from process:" + Process.myPid());
        remoteViews.setImageViewResource(R.id.icon, R.drawable.app_android_art);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, new Intent(this, RemoteViewsActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, RemotoViewTestActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
        Intent intent = new Intent(MConstant.REMOTE_ACTION);
        intent.putExtra(MConstant.EXTRA_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);
    }
}
