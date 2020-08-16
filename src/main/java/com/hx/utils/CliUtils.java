package com.hx.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CliUtils {
	final static Logger log = LoggerFactory.getLogger(CliUtils.class);
	public static String getCliDataString(boolean... delWrap) {
		String fromClipboard = "";
		try {
			fromClipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.stringFlavor);
		} catch (Throwable e) {
			throw new RuntimeException("剪切板不是字符串类型", e);
		}
		if (delWrap.length == 0 || !delWrap[0]) {
			return fromClipboard;
		}
		return fromClipboard.replaceAll("(\r\n|\n)", "");
	}
}
