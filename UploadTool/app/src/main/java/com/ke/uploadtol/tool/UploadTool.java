package com.ke.uploadtol.tool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Base64;
import android.util.Log;

public class UploadTool {
	private final static String TAG = "lzx";
	private static final int TIMEOUT = 10000;// 10秒 
	
	/**
	 * 上传文件
	 * @return
	 * String
	 * UploadTool.java
	 */
	public static String upLoadFile(String urlPath)
			throws Exception{
		Log.d(TAG, "@upLoadFile");
		String end ="\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		URL url = new URL(urlPath);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		/*允许Input，Output。不使用Cache*/
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);//不允许使用缓存
//		/*设置传送的method=POST*/
		con.setRequestMethod("POST");
//		con.setRequestProperty("Connection", "Keep-Alive");  
//	    con.setRequestProperty("Charset", "UTF-8");
//		con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//		/*设置DataOutputStream*/
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		//ds.writeBytes("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + newName + "\";" +"" + end);
		ds.writeBytes(end);
//		//取得文件的FileInputStream
		//FileInputStream fs = new FileInputStream(newName);
//		
//		//设置每次写入1024byte
////		int byteSize = 1024*1024;
////		byte[] buffer = new byte[];
//		int length = -1;
//		//将文件写入到缓冲区
//		while( (length = fs.read(buffer)) != -1){
//			//ds.write(buffer, 0 ,buffer.length);
//		}
		//Log.v(TAG, "11111111111");
		//ds.writeBytes(end);
	    //ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	    /*close stream*/
	    //fs.close();
	    //ds.flush();
	    
	    /*取得response的内容*/
	    InputStream is = con.getInputStream();
	    int ch;
	    StringBuffer b = new StringBuffer();
	    while( (ch=is.read()) != -1 ){
	    	b.append( (char)ch );
	    }
	    /*关闭DataOutputStream*/
	    //ds.close();
	    return b.toString();
	}

	private static String encodeByte(byte[] buffer) {
		Log.d("lzx","encodeByte");
		return Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
	}

	/**
	 * 上传文件夹
	 * @return
	 * String
	 * UploadTool.java
	 * @throws Exception 
	 */
	public static String upLoadFolder(){
		Log.d(TAG, "@upLoadFolder");
		//TODO
		return null;
	}
	public static String sendPost(String json) throws Exception{
		URL url = new URL("http://10.10.114.98:8080/AppServer/UploadFileServlet");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
//		con.setRequestProperty("Content-Type", "application/json");
//		con.setRequestProperty("Accept", "application/json");
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		StringBuilder sb1 = new StringBuilder();
			ds.writeBytes(sb1.toString());
	 
	
		con.setRequestMethod("POST");
		con.connect();
		byte[] outputBytes=json.getBytes("UTF-8");
		OutputStream os=con.getOutputStream();
		os.write(outputBytes);
		os.close();
		int status=con.getResponseCode();
		if(status!=200){
			throw new IOException("Post failed with error code "+ status);
		}
		BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder sb=new StringBuilder();
		String line;
		while((line=br.readLine())!=null){
			sb.append(line+"\n");
		}
		br.close();
		return sb.toString();
	} 

}
