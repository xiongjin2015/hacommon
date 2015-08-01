package com.haha.common.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.haha.common.config.HaDirMgmt;
import com.haha.common.config.HaDirMgmt.WorkDir;
import com.haha.common.logger.Logcat;
import com.haha.common.utils.HaDigest;
import com.haha.common.utils.HaDir;


/**
 * read from cache file or write to cache file
 * @author xj
 *
 */
public class HaCacheFiles {
	private final static String TAG = "HaCacheFiles";
	
	private String cacheDir;
	
	private static HaCacheFiles instance = null;
	
	public static HaCacheFiles getInstance(){
		if(instance == null){
			instance = new HaCacheFiles();
		}
		return instance;
	}
	
	public void init(){
		this.cacheDir = HaDirMgmt.getInstance().getPath(WorkDir.CACHE_DAS);
		HaDir.createDirs(this.cacheDir);
	}
	
	/**
	 * cache the json string content to file,identity by url
	 * @param url:request url
	 * @param content:json string content of return
	 * @throws Exception
	 */
	public void write(String url, String content) throws Exception{
		FileOutputStream fos = null;
		try{
			if(!HaDir.createDirs(this.cacheDir)){
				Logcat.e(TAG, "create directory "+this.cacheDir+" failed!");
			}
			File cacheFile = new File(this.getFilePath(url));
			fos = new FileOutputStream(cacheFile);
			fos.write(content.getBytes());
			fos.close();
		}finally{
			try{
				if(fos != null)
					fos.close();
			}catch(Exception e){
				Logcat.d(TAG, e.getMessage());
			}
		}
	}
	
	/**
	 * read cache json content from cache file
	 * @param url
	 * @return json string content relate to url
	 * @throws Exception
	 */
	public String read(String url) throws Exception{
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(this.getFilePath(url));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[16*1024];
			int rdsz = fis.read(buffer);
			while(rdsz != -1){
				baos.write(buffer, 0, rdsz);
				rdsz = fis.read(buffer);
			}
			fis.close();
			
			return new String(baos.toByteArray());
		}finally{
			try{
				if(fis != null)
					fis.close();
			}catch(Exception e){
				Logcat.d(TAG, e.getMessage());
			}
		}
	}
	
	public boolean isHit(String url){
		File file = new File(this.getFilePath(url));
		if(file.exists() && file.isFile())
			return true;
		return false;
	}
	
	public boolean isExpired(String url, long expireInMillis){
		File file = new File(this.getFilePath(url));
		if(file.exists() && file.isFile()){
			long cachedTime = System.currentTimeMillis()-file.lastModified();
			if(cachedTime < expireInMillis)
				return false;
		}
		return true;
	}
		
	private String getFilePath(String url){
		return this.cacheDir+"/"+getFileName(url);
	}
	
	   /**
     * generate cache file name relate with url 
     * @param url
     * @return
     * cache file name, or null
     */
    private static String getFileName(String url){
        String urlSha1 = HaDigest.sha1(url);
        if(urlSha1 == null)
            return null;
        
        String fileName = urlSha1+".cache";
        
        return fileName;
    }
}
