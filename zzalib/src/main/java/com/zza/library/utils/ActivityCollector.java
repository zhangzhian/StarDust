package com.zza.library.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhian on 16/12/28.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
