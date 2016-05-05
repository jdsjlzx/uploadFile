/**
 * Copyright ® 2016 DQ ENCH Co. Ltd.
 * All right reserved.
 */
package common.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import common.FileUtiles;

import java.util.Set;

public class HttpRequest {

	private URL url;
	private String surl;
	private final Map<String, String> params;
	private final Map<String, File> files;
	private Method method;
	private int timeout = 5000;
	private String encoding = "UTF-8";
	private static final String BOUNDARYA = "ench";//naosdnfoasndfio

	public HttpRequest(String url) {
		surl = url;
		this.params = new HashMap<String, String>();
		this.files = new HashMap<String, File>();
	}

	public void addParam(String name, String value) {
		this.params.put(name, value);
	}

	public void addFile(String name, File file) {
		this.files.put(name, file);
	}

	public void removeParam(String name) {
		params.remove(name);
	}

	public void removeFile(String name) {
		files.remove(name);
	}

	public void clearAll() {
		params.clear();
		files.clear();
	}

	public void clearParams() {
		params.clear();
	}

	public void clearFiles() {
		files.clear();
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 将map的键值用 & 和 = 连接起来，用于简单post请求
	 * 
	 * @param params
	 * @return
	 */
	private final static String joinParams(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		if (!params.isEmpty()) {
			Set<Entry<String, String>> enties = params.entrySet();
			Iterator<Entry<String, String>> its = enties.iterator();
			while (its.hasNext()) {
				Entry<String, String> entry = its.next();
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(entry.getValue());
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 将所有参数全部附加到surl，用于get请求
	 */
	private void addParamsToURL() {
		String params = joinParams(this.params);
		if (params.length() > 0) {
			surl += "?" + params;
		}
	}

	public HttpResponse exeute() throws IOException {
		HttpResponse response = null;
		if (method == Method.GET) {
			response = executeGET();
		} else if (method == Method.POST) {
			response = executePOST();
		} else {
			throw new IllegalArgumentException("必须调用   setMethod  方法");
		}

		return response;
	}

	/**
	 * 例：contentType=text/html;charset=utf-8<br/>
	 * return utf-8
	 * 
	 * @param contentType
	 * @return
	 */
	final static String getEncoding(String contentType) {
		String encoding = null;
		if (contentType.contains("charset=")) {
			int index = contentType.indexOf("charset=");
			encoding = contentType.substring(index + 8);
		}
		return encoding;
	}

	private HttpResponse executeGET() throws IOException {
		HttpResponse response = null;
		addParamsToURL();
		url = new URL(surl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(timeout);
		String contentType = conn.getContentType();
		String encoding = getEncoding(contentType);
		int code = conn.getResponseCode();
		String status = conn.getResponseMessage();
		InputStream inputStream = conn.getInputStream();
		response = new HttpResponse(code, status, encoding, inputStream);
		return response;
	}

	private HttpResponse executePOST() throws IOException {
		HttpResponse response = null;
		url = new URL(surl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(timeout);
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		OutputStream os = null;
		if (files.isEmpty()) {
			os = conn.getOutputStream();
			os.write(joinParams(params).getBytes(encoding));
		} else {
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + BOUNDARYA);
			os = conn.getOutputStream();
			String encodedParams = encodePostBody(params);
			System.out.println(encodedParams);
			os.write(encodedParams.getBytes());
			for (String name : files.keySet()) {
				File file = files.get(name);
				os.write(encodePostFile(name, file));
			}
			os.write("--".getBytes());
			os.write(BOUNDARYA.getBytes());
			os.write("--".getBytes());
		}
		os.flush();
		os.close();
		response = new HttpResponse(conn);
		return response;
	}

	private final static String encodePostBody(Map<String, String> params) {
		if (params == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			sb.append("--" + BOUNDARYA).append("\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + key
					+ "\"\r\n\r\n" + params.get(key));
			sb.append("\r\n");
		}
		return sb.toString();
	}

	private final static byte[] encodePostFile(String name, File file)
			throws IOException {
		System.out.println("开始写文件：" + name + "," + file.getAbsolutePath());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StringBuilder sb = new StringBuilder();
		sb.append("--" + BOUNDARYA + "\r\n");
		sb.append("Content-Disposition: form-data;name=\"" + name + "\";");
		sb.append(" filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:").append(FileUtiles.getFileContentType(file));
		sb.append("\r\n\r\n");
		System.out.println(sb.toString());
		out.write(sb.toString().getBytes("UTF-8"));
		sb.setLength(0);
		out.write(FileUtiles.getBytes(file));
		out.write("\r\n".getBytes());
		return out.toByteArray();
	}
}