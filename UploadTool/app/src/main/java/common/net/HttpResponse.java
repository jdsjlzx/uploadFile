/**
 * Copyright ® 2016 DQ ENCH Co. Ltd.
 * All right reserved.
 */
package common.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class HttpResponse {

	private final int code;
	private final String status;
	private final InputStream inputStream;
	private final String encoding;

	public HttpResponse(HttpURLConnection conn) throws IOException {
		String contentType = conn.getContentType();
		String encoding = HttpRequest.getEncoding(contentType);
		int code = conn.getResponseCode();
		String status = conn.getResponseMessage();
		InputStream inputStream = conn.getInputStream();
		this.code = code;
		this.status = status;
		this.encoding = encoding;
		this.inputStream = inputStream;
	}

	public HttpResponse(int code, String status, String encoding,
			InputStream inputStream) {
		super();
		this.code = code;
		this.status = status;
		this.encoding = encoding;
		this.inputStream = inputStream;
	}

	public int getHttpCode() {
		return code;
	}

	public String getHttpStatus() {
		return status;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getEncoding() {
		return encoding;
	}

	public String toString() {
		return "{code:" + code + ",status:" + status + ",encoding:" + encoding
				+ "}";
	}

}