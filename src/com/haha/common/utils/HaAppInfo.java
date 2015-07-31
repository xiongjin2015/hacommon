
package com.haha.common.utils;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;

/**
 * get app versionName、versionCode和packageName
 * 
 * @author xj 2015/07/30
 */
public final class HaAppInfo {

    public static String getVersionName(Context context) {
        PackageInfo info = getPackageInfo(context);
        if (info == null)
            return "";
        return info.versionName;
    }

    public static int getVersionCode(Context context) {
        PackageInfo info = getPackageInfo(context);
        if (info == null)
            return 0;
        return info.versionCode;
    }

    public static String getPackageName(Context context) {
        PackageInfo info = getPackageInfo(context);
        if (info == null)
            return context.getPackageName();
        return info.packageName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            return manager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    public static String getDeclaredField(Context ctx, String key) {

        try {
            key = key.toUpperCase();
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (TextUtils.equals(key, field.getName().toUpperCase()))
                        return field.get(null).toString();
                } catch (Exception e) {
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

}
