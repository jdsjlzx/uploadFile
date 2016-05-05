/**
 * Copyright ® 2016 DQ ENCH Co. Ltd.
 * All right reserved.
 */
package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamReader {
	public final static String getStringFromStream(InputStream is,
			String encoding) throws IOException {
		byte[] bytes = getBytesFromStream(is);
		encoding = encoding == null ? "UTF-8" : encoding;
		return new String(bytes, encoding);
	}

	public final static byte[] getBytesFromStream(InputStream is)
			throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int length = 0;
		byte[] bytes = new byte[1024];
		while ((length = is.read(bytes)) != -1) {
			out.write(bytes, 0, length);
		}
		return out.toByteArray();
	}
}