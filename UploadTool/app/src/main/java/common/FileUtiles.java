/**
 * Copyright ® 2016 DQ ENCH Co. Ltd.
 * All right reserved.
 */
package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtiles {

	private static final Map<String, String> contentTypes = new HashMap<String, String>();

	static {
		initContentTypes();
	}

	 //请求的类型文件
	private static final void initContentTypes() {
		contentTypes.put("ez", "application/andrew-inset");
		contentTypes.put("hqx", "application/mac-binhex40");
		contentTypes.put("cpt", "application/mac-compactpro");
		contentTypes.put("doc", "application/msword");
		contentTypes.put("bin", "application/octet-stream");
		contentTypes.put("dms", "application/octet-stream");
		contentTypes.put("lha", "application/octet-stream");
		contentTypes.put("lzh", "application/octet-stream");
		contentTypes.put("exe", "application/octet-stream");
		contentTypes.put("class", "application/octet-stream");
		contentTypes.put("so", "application/octet-stream");
		contentTypes.put("dll", "application/octet-stream");
		contentTypes.put("oda", "application/oda");
		contentTypes.put("pdf", "application/pdf");
		contentTypes.put("ai", "application/postscript");
		contentTypes.put("eps", "application/postscript");
		contentTypes.put("ps", "application/postscript");
		contentTypes.put("smi", "application/smil");
		contentTypes.put("smil", "application/smil");
		contentTypes.put("csv", "application/csv");
		//contentTypes.put("csv", "application/x-www-form-urlencoded");
		contentTypes.put("mif", "application/vnd.mif");
		contentTypes.put("xls", "application/vnd.ms-excel");
		contentTypes.put("ppt", "application/vnd.ms-powerpoint");
		contentTypes.put("wbxml", "application/vnd.wap.wbxml");
		contentTypes.put("wmlc", "application/vnd.wap.wmlc");
		contentTypes.put("wmlsc", "application/vnd.wap.wmlscriptc");
		contentTypes.put("bcpio", "application/x-bcpio");
		contentTypes.put("vcd", "application/x-cdlink");
		contentTypes.put("pgn", "application/x-chess-pgn");
		contentTypes.put("cpio", "application/x-cpio");
		contentTypes.put("csh", "application/x-csh");
		contentTypes.put("dcr", "application/x-director");
		contentTypes.put("dir", "application/x-director");
		contentTypes.put("dxr", "application/x-director");
		contentTypes.put("dvi", "application/x-dvi");
		contentTypes.put("spl", "application/x-futuresplash");
		contentTypes.put("gtar", "application/x-gtar");
		contentTypes.put("hdf", "application/x-hdf");
		contentTypes.put("js", "application/x-javascript");
		contentTypes.put("skp", "application/x-koan");
		contentTypes.put("skd", "application/x-koan");
		contentTypes.put("skt", "application/x-koan");
		contentTypes.put("skm", "application/x-koan");
		contentTypes.put("latex", "application/x-latex");
		contentTypes.put("nc", "application/x-netcdf");
		contentTypes.put("cdf", "application/x-netcdf");
		contentTypes.put("sh", "application/x-sh");
		contentTypes.put("shar", "application/x-shar");
		contentTypes.put("swf", "application/x-shockwave-flash");
		contentTypes.put("sit", "application/x-stuffit");
		contentTypes.put("sv4cpio", "application/x-sv4cpio");
		contentTypes.put("sv4crc", "application/x-sv4crc");
		contentTypes.put("tar", "application/x-tar");
		contentTypes.put("tcl", "application/x-tcl");
		contentTypes.put("tex", "application/x-tex");
		contentTypes.put("texinfo", "application/x-texinfo");
		contentTypes.put("texi", "application/x-texinfo");
		contentTypes.put("t", "application/x-troff");
		contentTypes.put("tr", "application/x-troff");
		contentTypes.put("roff", "application/x-troff");
		contentTypes.put("man", "application/x-troff-man");
		contentTypes.put("me", "application/x-troff-me");
		contentTypes.put("ms", "application/x-troff-ms");
		contentTypes.put("ustar", "application/x-ustar");
		contentTypes.put("src", "application/x-wais-source");
		contentTypes.put("xhtml", "application/xhtml+xml");
		contentTypes.put("xht", "application/xhtml+xml");
		contentTypes.put("zip", "application/zip");
		contentTypes.put("au", "audio/basic");
		contentTypes.put("snd", "audio/basic");
		contentTypes.put("mid", "audio/midi");
		contentTypes.put("midi", "audio/midi");
		contentTypes.put("kar", "audio/midi");
		contentTypes.put("mpga", "audio/mpeg");
		contentTypes.put("mp2", "audio/mpeg");
		contentTypes.put("mp3", "audio/mpeg");
		contentTypes.put("aif", "audio/x-aiff");
		contentTypes.put("aiff", "audio/x-aiff");
		contentTypes.put("aifc", "audio/x-aiff");
		contentTypes.put("m3u", "audio/x-mpegurl");
		contentTypes.put("ram", "audio/x-pn-realaudio");
		contentTypes.put("rm", "audio/x-pn-realaudio");
		contentTypes.put("rpm", "audio/x-pn-realaudio-plugin");
		contentTypes.put("ra", "audio/x-realaudio");
		contentTypes.put("wav", "audio/x-wav");
		contentTypes.put("pdb", "chemical/x-pdb");
		contentTypes.put("xyz", "chemical/x-xyz");
		contentTypes.put("bmp", "image/bmp");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("ief", "image/ief");
		contentTypes.put("jpeg", "image/jpeg");
		contentTypes.put("jpg", "image/jpeg");
		contentTypes.put("jpe", "image/jpeg");
		contentTypes.put("png", "image/png");
		contentTypes.put("tiff", "image/tiff");
		contentTypes.put("tif", "image/tiff");
		contentTypes.put("djvu", "image/vnd.djvu");
		contentTypes.put("djv", "image/vnd.djvu");
		contentTypes.put("wbmp", "image/vnd.wap.wbmp");
		contentTypes.put("ras", "image/x-cmu-raster");
		contentTypes.put("pnm", "image/x-portable-anymap");
		contentTypes.put("pbm", "image/x-portable-bitmap");
		contentTypes.put("pgm", "image/x-portable-graymap");
		contentTypes.put("ppm", "image/x-portable-pixmap");
		contentTypes.put("rgb", "image/x-rgb");
		contentTypes.put("xbm", "image/x-xbitmap");
		contentTypes.put("xpm", "image/x-xpixmap");
		contentTypes.put("xwd", "image/x-xwindowdump");
		contentTypes.put("igs", "model/iges");
		contentTypes.put("iges", "model/iges");
		contentTypes.put("msh", "model/mesh");
		contentTypes.put("mesh", "model/mesh");
		contentTypes.put("silo", "model/mesh");
		contentTypes.put("wrl", "model/vrml");
		contentTypes.put("vrml", "model/vrml");
		contentTypes.put("css", "text/css");
		contentTypes.put("html", "text/html");
		contentTypes.put("htm", "text/html");
		contentTypes.put("asc", "text/plain");
		contentTypes.put("txt", "text/plain");
		contentTypes.put("rtx", "text/richtext");
		contentTypes.put("rtf", "text/rtf");
		contentTypes.put("sgml", "text/sgml");
		contentTypes.put("sgm", "text/sgml");
		contentTypes.put("tsv", "text/tab-separated-values");
		contentTypes.put("wml", "text/vnd.wap.wml");
		contentTypes.put("wmls", "text/vnd.wap.wmlscript");
		contentTypes.put("etx", "text/x-setext");
		contentTypes.put("xsl", "text/xml");
		contentTypes.put("xml", "text/xml");
		contentTypes.put("mpeg", "video/mpeg");
		contentTypes.put("mpg", "video/mpeg");
		contentTypes.put("mpe", "video/mpeg");
		contentTypes.put("qt", "video/quicktime");
		contentTypes.put("mov", "video/quicktime");
		contentTypes.put("mxu", "video/vnd.mpegurl");
		contentTypes.put("avi", "video/x-msvideo");
		contentTypes.put("movie", "video/x-sgi-movie");
		contentTypes.put("ice", "x-conference/x-cooltalk");
	}

	public static final String getFileContentType(File file) {
		if (contentTypes.isEmpty()) {
			initContentTypes();
		}
		final String name = file.getName();
		int index = name.lastIndexOf(".");
		String subfix = "";
		if (index != -1) {
			subfix = name.substring(index + 1);
		}
		String type = contentTypes.get(subfix);
		return type == null ? "" : type;
	}
    //将文件读取，写入到流中
	public static final byte[] getBytes(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int length = 0;
		while ((length = fis.read(bytes)) != -1) {
			bos.write(bytes, 0, length);
		}
		return bos.toByteArray();
	}
}
