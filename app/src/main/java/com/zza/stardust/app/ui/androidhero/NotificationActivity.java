package com.zza.stardust.app.ui.androidhero;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import com.zza.library.base.BasePresenter;
import com.zza.stardust.R;
import com.zza.stardust.base.MActivity;

import static com.zza.stardust.R.id.visibility_radio_group;


/**
 * TODO 适配 NotificationChannel
 */
public class NotificationActivity extends MActivity {

    private int NOTIFICATION_ID_BASIC = 1;
    private int NOTIFICATION_ID_COLLAPSE = 2;
    private int NOTIFICATION_ID_HEADSUP = 3;
    private int NOTIFICATION_ID_VISIBILITY = 4;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_notification;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void basicNotify(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.baidu.com"));
        // 构造PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        // 创建Notification对象
        Notification.Builder builder = new Notification.Builder(this);
        // 设置Notification的各种属性
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Basic Notifications");
        builder.setContentText("I am a basic notification");
        builder.setSubText("it is really basic");
        // 通过NotificationManager来发出Notification
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(
                        NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID_BASIC,
                builder.build());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void collapsedNotify(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.sina.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(
                getResources(), R.mipmap.ic_launcher));
        // 通过RemoteViews来创建自定义的Notification视图
        RemoteViews contentView =
                new RemoteViews(getPackageName(),
                        R.layout.notification);
        contentView.setTextViewText(R.id.textView,
                "show me when collapsed");

        Notification notification = builder.build();
        notification.contentView = contentView;
        // 通过RemoteViews来创建自定义的Notification视图
        RemoteViews expandedView =
                new RemoteViews(getPackageName(),
                        R.layout.notification_expanded);
        notification.bigContentView = expandedView;

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_COLLAPSE, notification);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void headsupNotify(View view) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Headsup Notification")
                .setContentText("I am a Headsup notification.");

        Intent push = new Intent();
        push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        push.setClass(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentText("Heads-Up Notification on Android 5.0")
                .setFullScreenIntent(pendingIntent, true);

        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_HEADSUP, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void visibilityNotify(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(
                visibility_radio_group);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Notification for Visibility Test");
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radio_button_public:
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setContentText("Public");
                builder.setSmallIcon(R.drawable.ic_public);
                break;
            case R.id.radio_button_private:
                builder.setVisibility(Notification.VISIBILITY_PRIVATE);
                builder.setContentText("Private");
                builder.setSmallIcon(R.drawable.ic_private);
                break;
            case R.id.radio_button_secret:
                builder.setVisibility(Notification.VISIBILITY_SECRET);
                builder.setContentText("Secret");
                builder.setSmallIcon(R.drawable.ic_secret);
                break;
        }
        NotificationManager nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTIFICATION_ID_VISIBILITY, builder.build());
    }

}
