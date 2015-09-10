package com.moonwu.mobilemanager.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

	public static String readInputStream(InputStream is) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int length = 0;
			byte[] buffer = new byte[1024];
			if ((length = is.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			is.close();
			String result = out.toString();
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return "资源读取失败!";
		}
	}

}
