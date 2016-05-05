package com.ke.uploadtol.tool;

import android.util.Base64;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import common.FileUtiles;

public class HttpUtils {
	public static void cutFileUpload(String fileType, String filePath) {
		try {
			FileAccessI fileAccessI = new FileAccessI(filePath, 0);
			Long nStartPos = 0l;
			Long length = fileAccessI.getFileLength();
			int mBufferSize = 1024 * 100; // 每次处理1024 * 100字节
			byte[] buffer = new byte[mBufferSize];
			FileAccessI.Detail detail;
			long nRead = 0l;
			String vedioFileName = generatePicName(); // 分配一个文件名
			long nStart = nStartPos;
			while (nStart < length) {
				detail = fileAccessI.getContent(nStart);
				nRead = detail.length;
				buffer = detail.b;
				final JSONObject mInDataJson = new JSONObject();
				mInDataJson.put("FileName", vedioFileName);
				mInDataJson.put("start", nStart); // 服务端获取开始文章进行写文件
				mInDataJson.put("filetype", fileType);
				nStart += nRead;
				nStartPos = nStart;
				String url = "http://172.16.1.60:8080/AppServer/UploadFileServlet";

				RequestParams params = new RequestParams();
				params.put("json", mInDataJson.toString());
				params.put("video", encodeByte(buffer));

				AsyncHttpClient client = new AsyncHttpClient();
				client.post(url, params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {

					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

					}
				});
			}
		} catch (Exception e) {
		}
	}

	private final static byte[] encodePostFileu(String name, byte[] file) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(file);
		return out.toByteArray();
	}

	private static String encodeByte(byte[] buffer) {
		Log.d("lzx","encodeByte");
		return Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
	}

	public static String generatePicName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.CHINA);
		String filename = sdf.format(new Date(System.currentTimeMillis())) + ".mp4";
		return filename;
	}

}
