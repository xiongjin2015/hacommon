
package com.haha.common.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
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

    public final static class OS {
        /**
         * get the android os version
         * 
         * @return android os version
         */
        public static String getVersion() {
            return android.os.Build.VERSION.RELEASE;
        }

        /**
         * get the android id,which is A 64-bit number(as a hex String) that is randomly generated
         * on the device's first boot and should remain constant for the lifetime of the device.
         * (The value may change if a factory reset is performed on the device)
         * 
         * @param context
         * @return android id of the device
         */
        public static String getAndroidID(Context context) {
            return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        }
    }

    public final static class Wifi {

        /**
         * get the wifi information object
         * 
         * @param context
         * @return wifi information object or null
         */
        public static WifiInfo getWifiInfo(Context context) {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wm != null) {
                WifiInfo wi = wm.getConnectionInfo();
                if (wi != null)
                    return wi;
            }
            return null;
        }

        /**
         * get the mac address of device in format:xx.xx.xx.xx.xx.xx
         * 
         * @param context
         * @return mac address or null str
         */
        public static String getRawMacAddress(Context context) {
            try {
                WifiInfo wi = getWifiInfo(context);
                if (wi != null) {
                    String mac = wi.getMacAddress();
                    if (mac != null)
                        return mac.toLowerCase(Locale.getDefault());
                }
            } catch (Exception e) {
            }

            return "00.00.00.00.00.00";
        }

        /**
         * get the mac address of device in format:AABBCCDDEEFF
         * 
         * @param context
         * @return mac address or null;
         */
        public static String getMacAddress(Context context) {
            String macAddress = getRawMacAddress(context);
            if (macAddress != null)
                return macAddress.replace(":", "").toLowerCase(Locale.getDefault());
            return "000000000000";
        }
    }

    /**
     * pack the file system utility methods of device
     */
    public final static class FileSystem {

        /**
         * test if the given directory is writable
         * 
         * @param dir:directory path to be test
         * @return true if writable ,otherwise return false
         */
        public static boolean isWritable(String dir) {
            if (dir == null)
                return false;

            File fdir = new File(dir);
            if (fdir.exists() && !fdir.mkdirs())
                return false;

            if (!fdir.canWrite())
                return false;

            return true;

        }

        public static Volume getVolume(String path) {
            if (path == null)
                return null;

            State state = getState(path);
            if (state != null)
                return new Volume(path, state);
            else
                return null;
        }

        /**
         * get the state of specified volume by path
         * 
         * @param path
         * @return state of the volume,or null
         */
        public static State getState(String path) {
            if (path == null)
                return null;

            StatFs sf = new StatFs(path);
            long total = sf.getBlockCount() * sf.getBlockSize();
            long available = sf.getAvailableBlocks() * sf.getBlockSize();
            long free = sf.getFreeBlocks() * sf.getBlockSize();

            return new State(total, available, free);
        }
        
        /**
         * get the total size in bytes of volume specified by the input path
         * @param path
         * @return total size in bytes
         */
        public static long getTotalSize(String path){
            if(path == null)
                return 0;
            try{
                StatFs sf = new StatFs(path);
                return sf.getBlockCount()*sf.getBlockSize(); 
            }catch(Exception e){
                return 0;
            }
        }
        
        /**
         * get the available size in bytes of volume specified by the input path
         * @param path
         * @return available size in bytes
         */
        public static long getAvailableSize(String path){
            if(path == null)
                return 0;
            
            try{
                StatFs sf = new StatFs(path);
                return sf.getAvailableBlocks()*sf.getBlockSize();
            }catch(Exception e){
                return 0;
            }
        }
        
        /**
         * get the free size in bytes of volume specified by the input path
         * @param path
         * @return free size in bytes
         */
        public static long getFreeSize(String path){
            if(path == null)
                return 0;
            
            try{
                StatFs sf = new StatFs(path);
                return sf.getFreeBlocks()*sf.getBlockSize();
            }catch(Exception e){
                return 0;
            }
        }
        
        /**
         * get the external storage path
         * @return external storage dir
         */
        public static String getExternalStorageDir(){
            try{
                File path = Environment.getExternalStorageDirectory();
                return path.getAbsolutePath();
            }catch(Exception e){
                return null;
            }
        }
        
        public static Volume[] getValidVolumes(Context context){
            try{
                /*for create the app files directory on the sdcard for some device*/
                FileSystem.createAppExternalFilesDir(context);
                
                List<Volume> volumes = new ArrayList<Volume>();

                /*add the external storage directory*/
                Volume externalStorageVolume = FileSystem.getVolume(FileSystem.getExternalStorageDir());
                if(externalStorageVolume != null && externalStorageVolume.isAvaliable() && !FileSystem.existVolumeInList(externalStorageVolume, volumes)){
                    externalStorageVolume.setExternal(true);
                    volumes.add(externalStorageVolume);
                }

                /*find all the valid volumes*/
                String otherVolumePaths[] = FileSystem.getVolumePaths(context);
                if(otherVolumePaths != null){
                    for(String path: otherVolumePaths){
                        if(!FileSystem.isWritable(path) && HaDir.exist(FileSystem.getAndroidDataDir(path))){
                            path = FileSystem.getAndroidDataDir(path)+"/"+context.getPackageName()+"/files";
                        }
                        
                        Volume volume = FileSystem.getVolume(path);
                        if(volume != null && !FileSystem.existVolumeInList(volume, volumes) && volume.isAvaliable()){
                            volume.setExternal(false);
                            volumes.add(volume);
                        }
                    }
                }

                /*transmit the volumes to array and return*/
                if(!volumes.isEmpty()){
                    return volumes.toArray(new Volume[volumes.size()]);
                }else{
                    return null;
                }
            }catch(Exception e){
                return null;
            }
        }
        
        /**
         * get all the volume paths
         * @param context: current context
         * @return
         *      all the volume paths, or null
         */
        public static String[] getVolumePaths(Context context){
            try{
                Method getSystemService = context.getClass().getMethod("getSystemService", String.class);
                Object sm = getSystemService.invoke(context, "storage");                        
                Method getVolumePaths = sm.getClass().getMethod("getVolumePaths");
                
                return (String[])getVolumePaths.invoke(sm);
                
            }catch(Exception e){
                return null;
            }
        }
        
        public static String getAppFilesDir(Context context){
            try{
                Class<?> c = Class.forName("android.os.Environment");
                Method getExternalStorageAppFilesDirectory = c.getMethod("getExternalStorageAppFilesDirectory", String.class);
                File dir = (File)getExternalStorageAppFilesDirectory.invoke(Environment.class, context.getPackageName());
                if(dir.exists() || dir.mkdirs())
                    return dir.getAbsolutePath();
                else
                    return FileSystem.getDefaultAppFilesDir(context);
            }catch(Exception e){
                return FileSystem.getDefaultAppFilesDir(context);
            }
        }
        
        public static String getDefaultAppFilesDir(Context context){
            try{
                File filesDir = context.getFilesDir();
                if(filesDir.exists() || filesDir.mkdirs()){
                    return filesDir.getAbsolutePath();
                }else{
                    return null;
                }
            }catch(Exception e){
                return null;
            }
        }
        
        public static String getAppMediaDir(Context context){
            try{
                Class<?> c = Class.forName("android.os.Environment");
                Method getExternalStorageAppMediaDirectory = c.getMethod("getExternalStorageAppMediaDirectory", String.class);
                File dir = (File)getExternalStorageAppMediaDirectory.invoke(Environment.class, context.getPackageName());
                if(dir.exists() || dir.mkdirs()){
                    return dir.getAbsolutePath();
                }else{
                    return FileSystem.getDefaultAppMediaDir(context);
                }
            }catch(Exception e){
                return FileSystem.getDefaultAppMediaDir(context);
            }
        }
        
        public static String getDefaultAppMediaDir(Context context){
            try{
                File defaultAppMediaDir = new File(new File(Environment.getDataDirectory(),"data"), context.getPackageName()+"/media");
                if(defaultAppMediaDir.exists() || defaultAppMediaDir.mkdirs()){
                    return defaultAppMediaDir.getAbsolutePath();
                }else{
                    return null;
                }
            }catch(Exception e){
                return null;
            }
        }
        
        private static void createAppExternalFilesDir(Context context){
            try{
                context.getExternalFilesDir(null);
            }catch(Exception e){}
        }
        
        private static boolean existVolumeInList(Volume volume, List<Volume> volumeList){
            try{
                for(Volume listVolume: volumeList){
                    if(volume.equals(listVolume)){
                        return true;
                    }
                }
                return false;
            }catch(Exception e){
                return false;
            }
        }
        
        private static String getAndroidDataDir(String root){
            if(root == null){
                return "/data/data";
            }
            
            return root+"/Android/data";            
        }
    }

    /**
     * storage space data structure of a volume
     */
    public static class State {
        private long total; // in bytes
        private long available; // in bytes
        private long free; // in bytes

        public State(long total, long available, long free) {
            this.total = total;
            this.available = available;
            this.free = free;
        }

        public long getTotal() {
            return total;
        }

        public long getAvailable() {
            return available;
        }

        public long getFree() {
            return free;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof State)) {
                return false;
            }

            State state = (State) obj;

            if (this.total == state.total && this.available == state.available
                    && this.free == state.free) {
                return true;
            }

            return false;
        }

    }

    /**
     * volume information data structure
     */
    public static class Volume {

        private String name;
        private String path;
        private State state;
        private boolean isExternal;

        public Volume(String path, State state) {
            this.name = path;
            this.name = path;
            this.state = state;
        }

        public Volume(String name, String path, State state) {
            this.name = name;
            this.path = path;
            this.state = state;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }

        public State getState() {
            return this.state;
        }

        public boolean isExternal() {
            return isExternal;
        }

        public void setExternal(boolean isExternal) {
            this.isExternal = isExternal;
        }

        public boolean isAvaliable() {
            if (this.state != null && this.state.getAvailable() > 0
                    && FileSystem.isWritable(this.getPath())) {
                return true;
            }
            return false;
        }

        public boolean isAvaliable(long limitAvaliableSize) {
            if (limitAvaliableSize < 0) {
                limitAvaliableSize = 0;
            }

            if (this.state != null && this.state.getAvailable() > limitAvaliableSize
                    && FileSystem.isWritable(this.getPath())) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "name: " + this.name + ", path: " + this.path + ", total: "
                    + this.state.getTotal() + "B, available: " + this.state.getAvailable()
                    + "B, left: " + this.state.getFree() + "B";
        }

        public boolean equals(Object obj) {
            try {
                if (!(obj instanceof Volume)) {
                    return false;
                }

                Volume volume = (Volume) obj;
                String path1 = volume.getPath() + "/";
                String path2 = this.getPath() + "/";
                if (path1.startsWith(path2) || path2.startsWith(path1)) {
                    return true;
                }

                return false;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
