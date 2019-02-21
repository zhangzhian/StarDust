package com.zza.library.api;

public class ApiFactory {

    protected static final Object monitor = new Object();
    static RepairApi repairApiSingleton = null;

    //return Singleton
    public static RepairApi getRepairApiSingleton() {
        synchronized (monitor) {
            if (repairApiSingleton == null) {
                repairApiSingleton = new ApiRetrofit().getRepairApiService();
            }
            return repairApiSingleton;
        }
    }

}
