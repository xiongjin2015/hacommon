
package com.haha.common.config;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.haha.common.config.HaPreference.PrefID;
import com.haha.common.haudid.HaAppType;
import com.haha.common.logger.Logcat;
import com.haha.common.utils.HaDevice;
import com.haha.common.utils.HaDevice.Volume;
import com.haha.common.utils.HaDir;

public final class HaDirMgmt {

    private final String TAG = "HaDirMgmt";
    
    /**
     * relate root directory
     */
    private String ROOT_DIR = "/haha";
    
    /**
     * work directory selected,default use the system default external storage
     */
    private String workDir;
    
    /**
     * media directory selected, default use the system default external storage
     */
    private String mediaDir;
    
    /**
     * single instance object
     */
    private static HaDirMgmt instance;
    
    /**
     * get the single instance object
     * @return single instance object
     */
    public static HaDirMgmt getInstance(){
        if(instance==null)
            instance = new HaDirMgmt();
        return instance;
    }

    /**
     * initialize the directory management single instance object, must be invoked
     * at the application entrance!
     * @param context
     * @return
     * return true if initialize success,other wise return false
     */
    public boolean init(Context context,HaAppType appType){
        this.ROOT_DIR = getRootDirByApp(appType);
        
        /*initial the work directory & media directory*/
        boolean flag1 = this.initWorkDir(context);
        boolean flag2 = this.initMediaDir(context);
        
        /*create the directory*/
        this.createWorkDirs();
        this.createMediaDirs(); 

        return flag1 && flag2;
    }
    
    
    /**
     * get the relate directory path
     * @param type: directory type
     * @return
     * request directory path of relate type, or ""
     */
    public String getPath(WorkDir type){
        String path = "";
        if(this.workDir != null){
            boolean flag = HaDir.createDirs(this.workDir);
            if(!flag){
                Logcat.e(TAG, "create work directory: "+this.workDir+" failed!");
            }
            
            path = this.workDir + type.getPath();
            flag = HaDir.createDirs(path);
            
            if(!flag){
                Logcat.e(TAG, "create directory: "+path+" failed!");
            }
        }
        
        return path;
    }
    
    private boolean initWorkDir(Context context) {
        /*try to get the work directory from shared preferences*/
        workDir = getWorkDir();
        if(TextUtils.isEmpty(workDir)||HaDevice.FileSystem.isWritable(workDir)){
            /*select new work root directory if saved path not available*/
            String workRootDir = selectWorkDir(context);
            /*save the root path to shared preference*/
            if(workRootDir != null){
                this.workDir = workRootDir+this.ROOT_DIR;
                this.saveWorkDir(this.workDir);
                Logcat.d("TAG", "init with new selected work dir: "+this.workDir);
            }
            else{
                Logcat.d("TAG", "fatal: init with new selected work dir failed");
                return false;
            }
        }else{
            Logcat.d("TAG","init with last selected work dir: "+this.workDir);
        }
        return true;
    }
    
    private boolean initMediaDir(Context context){
        /*try to get the media directory from shared preference*/
        this.mediaDir = this.getMediaDir();
        boolean dirNotSelected = this.mediaDir==null || this.mediaDir.equals("");
        if(dirNotSelected || !HaDevice.FileSystem.isWritable(this.mediaDir)){
            /*select new media directory if current not usable*/
            String mediaRootDir = selectMediaDir(context);
            /*save the root path to shared preference*/
            if(mediaRootDir != null){
                this.mediaDir = mediaRootDir+this.ROOT_DIR;
                if(dirNotSelected){
                    HaPreference.getInstance().putString(PrefID.PREF_MEDIA_DIR, this.mediaDir);
                }
                
                Logcat.d(TAG, "init with new selected media dir: "+this.workDir);
            }
            else{
                Logcat.d(TAG, "fatal: init with new selected media dir failed");
                return false;
            }
        }else{
            Logcat.d(TAG, "init with last selected media dir: "+this.workDir);
        }
        
        return true;
    }
    
    private String getWorkDir(){
        return HaPreference.getInstance().getString(PrefID.PREF_WORK_DIR);
    }
    
    /**
     * save the root path to context shared preferences
     * @param context: current context
     * @param path: root path to save
     */
    private void saveWorkDir(String path){
        HaPreference.getInstance().putString(PrefID.PREF_WORK_DIR, path);
    }
    
    /**
     * get media directory
     * @return
     */
    private String getMediaDir(){
        return HaPreference.getInstance().getString(PrefID.PREF_MEDIA_DIR); 
    }
    
    private String selectWorkDir(Context context){
        final long MIN_SPACE_LIMIT = 10*1024*1024; //1MB
        
        /*use external storage directory default*/
        Volume externalStorage = HaDevice.FileSystem.getVolume(HaDevice.FileSystem.getExternalStorageDir());
        if(externalStorage != null && externalStorage.isAvaliable(MIN_SPACE_LIMIT))
            return externalStorage.getPath();
        
        /*if default external storage is not useable,use other volumes*/
        Volume[] volumes = HaDevice.FileSystem.getValidVolumes(context);
        if(volumes != null){
            /*use the max available size volume*/
            Volume selected = null;
            for(Volume volume: volumes){
                if(selected == null){
                    selected = volume;
                    continue;
                }
                
                if(volume.getState().getAvailable() > selected.getState().getAvailable()){
                    selected = volume;
                }
            }
            
            if(selected != null){
                return selected.getPath();
            }
        }
        
        return HaDevice.FileSystem.getAppFilesDir(context);
    }
    
    /**
     * select media root path used for haha root
     * @param context: current context
     * @return
     * path selected or null
     */
    private String selectMediaDir(Context context){
        Volume selectVolume = null;
        List<Volume> validVolumes = this.getValidVolumes(context);
        for(Volume volume : validVolumes){                      
            if(selectVolume == null){
                selectVolume = volume;
                continue;
            }
            
            if(volume.getState().getAvailable() > selectVolume.getState().getAvailable()){
                selectVolume = volume;
            }
        }
        
        if(selectVolume != null){
            return selectVolume.getPath();
        }else{
            return HaDevice.FileSystem.getAppMediaDir(context);
        }
    }
    
    public List<Volume> getValidVolumes(Context context){
        final long MIN_SPACE_LIMIT = 10*1024*1024; // 1MB
        
        /*valid storage volumes*/
        List<Volume> validVolumes = new ArrayList<Volume>();
        
        /*try to select the other volumes*/
        Volume[] volumes = HaDevice.FileSystem.getValidVolumes(context);
        if(volumes != null){
            for(Volume volume: volumes){
                if(volume.getState() == null || volume.getState().getAvailable() < MIN_SPACE_LIMIT){
                    continue;
                }

                if(volume.isExternal()){
                    volume.setName("手机存储");
                }else{
                    volume.setName("SD卡存储");
                }
                validVolumes.add(volume);
            }
        }
        
        return validVolumes;
    }
    
    /**
     * create funshion directory under the funshion root path
     */
    private boolean createWorkDirs(){
        if(this.workDir == null)
            return false;
        
        HaDir.createDirs(this.workDir);
        
        for(WorkDir dir: WorkDir.values()){
            String path = this.workDir+dir.getPath();
            HaDir.createDirs(path);
        }
        
        return true;
    }
    
    /**
     * create funshion directory under the funshion media path
     */
    private boolean createMediaDirs(){
        if(this.mediaDir == null)
            return false;
        
        HaDir.createDirs(this.mediaDir);
        
        for(MediaDir dir: MediaDir.values()){
            String path = this.mediaDir+dir.getPath();
            HaDir.createDirs(path);
        }
        
        return true;
    }

    /**
     * get the relate root directory by app type
     * @param appType
     * @return
     */
    private String getRootDirByApp(HaAppType appType){
        String rootDir = "/haha";
        switch(appType){
        case APHONE:
            rootDir = "/haha";
            break;
        case APAD:
            rootDir = "/haha/pad";
            break;
        default:
            break;
        }
        return rootDir;
    }
    
    /**
     * directory types that application used
     */
    public enum WorkDir {
        ROOT(""),

        CACHE("/cache"),
        CACHE_DAS("/cache/das"),
        CACHE_IMG("/cache/img"),
        
        CONFIG("/config");

        private String path;
        
        private WorkDir(String path){
            this.path = path;
        }
        
        public String getPath(){
            return this.path;
        }
    }
    
    /**
     * directory types that application used
     */
    public enum MediaDir{   
        MEDIA("/media");
                
        private String path;
        
        private MediaDir(String path){
            this.path = path;
        }
        
        public String getPath(){
            return this.path;
        }
    };

}
