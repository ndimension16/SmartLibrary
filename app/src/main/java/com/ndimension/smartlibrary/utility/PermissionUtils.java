package com.ndimension.smartlibrary.utility;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

public class PermissionUtils {
    public static void requestPermission(Context context, String [] permissions, PermissionListener listener) {
        TedPermission.with(context)
                .setPermissionListener(listener)
                .setDeniedMessage("If you reject permission, you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(permissions)
                .check();
    }

    public static boolean isPermissionGranted(Context context, String permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }
}
