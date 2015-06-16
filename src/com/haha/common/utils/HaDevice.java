
package com.haha.common.utils;

import java.util.Locale;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public final class HaDevice {

    public final static class Dev {
        /**
         * get the telephone device id,which is the IMEI for GSM and the MEID or ESN for CMDA phones
         * 
         * @param context
         * @return device id or null;
         */
        public static String getDeviceID(Context context) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null)
                return tm.getDeviceId();
            return null;
        }
    }
    
    public final static class OS{
        /**
         * get the android os version
         * @return android os version
         */
        public static String getVersion(){
            return android.os.Build.VERSION.RELEASE;
        }
        
        /**
         * get the android id,which is A 64-bit number(as a hex String) that is randomly generated 
         * on the device's first boot and should remain constant for the lifetime of the device.
         * (The value may change if a factory reset is performed on the device)
         * @param context
         * @return android id of the device
         */
        public static String getAndroidID(Context context){
            return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }
    }
    
    public final static class Wifi{
        
        /**
         * get the wifi information object
         * @param context
         * @return wifi information object or null
         */
        public static WifiInfo getWifiInfo(Context context){
            WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            if(wm != null){
                WifiInfo wi = wm.getConnectionInfo();
                if(wi != null)
                    return wi;
            }
            return null;
        }
        
        /**
         * get the mac address of device in format:xx.xx.xx.xx.xx.xx
         * @param context
         * @return mac address or null str
         */
        public static String getRawMacAddress(Context context){
            try{
                WifiInfo wi = getWifiInfo(context);
                if(wi != null){
                    String mac = wi.getMacAddress();
                    if(mac!=null)
                        return mac.toLowerCase(Locale.getDefault());
                }
            }catch(Exception e){}
            
            return "00.00.00.00.00.00";
        }
        
        /**
         * get the mac address of device in format:AABBCCDDEEFF
         * @param context
         * @return mac address or null;
         */
        public static String getMacAddress(Context context){
            String macAddress = getRawMacAddress(context);
            if(macAddress!=null)
                return macAddress.replace(":", "").toLowerCase(Locale.getDefault());
            return "000000000000";
        }
    }

}
