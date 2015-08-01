package com.haha.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class HaDigest {
	/**
	 * compute the sha1 of input string
	 * @param input
	 * @return
	 * sha1 string, or null
	 */
	public static String sha1(String input){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(input.getBytes());
			byte[] res = md.digest();
			return byte2hex(res);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * compute the the sha1 of input data
	 * @param input
	 * @return
	 * sha1 string, or null
	 */
	public static String sha1(byte[] input){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(input);
			byte[] res = md.digest();
			return byte2hex(res);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * compute the md5 of input string
	 * @param input
	 * @return
	 * md5 string, or null
	 */
	public static String md5(String input){
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] res = md.digest();
			return byte2hex(res);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * compute the the md5 of input data
	 * @param input
	 * @return
	 * md5 string, or null
	 */
	public static String md5(byte[] input){
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			byte[] res = md.digest();
			return byte2hex(res);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * compute the the md5 of input file
	 * @param input file
	 * @return
	 * md5 string, or null
	 */
	public static String md5(File file){
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			InputStream fileStream = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 100];
			int numRead = 0;
			
			while ((numRead = fileStream.read(buffer)) > 0) {
				md.update(buffer, 0, numRead);
			}
			
			fileStream.close();
			byte[] res = md.digest();
			return byte2hex(res);
		} catch(Exception e){
			return null;
		}
	}
	
	/**
	 * convert byte array @data to hex string
	 * @param data: byte array data
	 * @return: hex string of byte array @data
	 */
	public static String byte2hex(byte[] data){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<data.length; i++){
			int high = (data[i]>>4)&0x0F;
			int low = data[i]&0x0F;
			
			sb.append(Integer.toHexString(high));
			sb.append(Integer.toHexString(low));
		}
		
		return sb.toString();
	}
	
}
