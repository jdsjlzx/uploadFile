package com.tgb.lk.demo.appserver;

import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class UploadFileServlet extends HttpServlet {

	/**
	 * 上传文件
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String json = req.getParameter("json");
		String video = req.getParameter("video");
		System.out.println("json " + json);
		try {
			f_uploadVedio(json,video);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 音视频图片处理
	 * 
	 * @param mStr
	 * @return
	 * @throws Exception
	 */
	public static String f_uploadVedio(String mStr,String data) throws Exception {
		String mResult = "";
		String fileType = "video";
		int startPosL = 0;
		RandomAccessFile oSavedFile = null;
		JSONObject jsonObject = new JSONObject(mStr);
		//byte[] vedioBytes = jsonObject.getString("VEDIO").getBytes("UTF-8");
		byte[] vedioBytes = decode(data);
		for (int i = 0; i < vedioBytes.length; ++i) {  
            if (vedioBytes[i] < 0) {  
                // 调整异常数据  
            	vedioBytes[i] += 256;  
            }  
        }  
		startPosL = (Integer) jsonObject.get("start"); // 接收客户端的开始位置(文件读取到的字节大小)
		fileType = (String) jsonObject.getString("filetype");
		String fileName = (String) jsonObject.getString("FileName");
		System.out.println("fileName " + fileName);
		if (fileType.equals("picture")) {
			oSavedFile = new RandomAccessFile("E:\\" + fileName + ".jpg", "rw");
		} else if (fileType.equals("photo")) {
			oSavedFile = new RandomAccessFile("E:\\" + fileName + ".jpg", "rw");
		} else if (fileType.equals("voice")) {
			oSavedFile = new RandomAccessFile("E:\\" + fileName + ".mp3", "rw");
		} else if (fileType.equals("video")) {
			oSavedFile = new RandomAccessFile("E:\\" + fileName, "rw");
		}
		// 设置标志位,标志文件存储的位置
		oSavedFile.seek(startPosL);
		oSavedFile.write(vedioBytes);
		oSavedFile.close();
		mResult = "000";
		return mResult;
	}

	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}

	public static String encode(byte[] bstr) {
		return new BASE64Encoder().encode(bstr);
	}

}
