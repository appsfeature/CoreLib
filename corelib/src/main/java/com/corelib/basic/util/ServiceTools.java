package com.corelib.basic.util;

/**
 * Created by Admin on 1/30/2018.
 */

//        boolean checkService = ServiceTools.isServiceRunning(this, GrabLocationDetails.class);
//        if (!checkService) {
//            startService(new Intent(this, GrabLocationDetails.class));
//        }

class ServiceTools {
//    private static final String LOG_TAG = ServiceTools.class.getName();
//
//    // working fine
//    public static boolean isServiceRunning(Context context, Class<?> serviceClass){
//        final ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
//
//        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
//            Log.d(LOG_TAG, String.format("Service:%s", runningServiceInfo.service.getClassName()));
//            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.getName().equals(service.service.getClassName())) {
//                Log.i ("isMyServiceRunning?", true+"");
//                return true;
//            }
//        }
//        Log.i ("isMyServiceRunning?", false+"");
//        return false;
//    }
}
