package com.zza.stardust.app.ui.androidart.thread;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import com.zza.library.utils.LogUtil;

public class LocalIntentService extends IntentService {

    public LocalIntentService() {
        super("LocalIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getStringExtra("task_action");
            LogUtil.d( "receive task :" +  action);
            SystemClock.sleep(3000);
            if ("com.zza.action.TASK1".equals(action)) {
                LogUtil.d( "handle task: " + action);
            }
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.d( "service destroyed.");
        super.onDestroy();
    }
}
