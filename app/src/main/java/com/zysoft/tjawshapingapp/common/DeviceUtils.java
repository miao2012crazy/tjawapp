package com.zysoft.tjawshapingapp.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;


public class DeviceUtils {

    @SuppressLint("MissingPermission")
    public static String getUniqueID() {
        TelephonyManager TelephonyMgr = (TelephonyManager) UIUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }
}