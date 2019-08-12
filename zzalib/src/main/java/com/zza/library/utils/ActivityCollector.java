package com.zza.library.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static List<Activity> part1Activities = new ArrayList<Activity>();

    public static List<Activity> part2Activities = new ArrayList<Activity>();

    public static List<Activity> part3Activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void addPart1Activity(Activity activity) {
        part1Activities.add(activity);
    }

    public static void addPart2Activity(Activity activity) {
        part2Activities.add(activity);
    }

    public static void addPart3Activity(Activity activity) {
        part3Activities.add(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishPart1(){
        for (Activity activity : part1Activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishPart2(){
        for (Activity activity : part1Activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishPart3(){
        for (Activity activity : part1Activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


}
