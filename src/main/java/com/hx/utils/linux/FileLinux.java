package com.hx.utils.linux;

import java.io.File;

/**
 * linux 文件路径解析
 * @author hx
 * @version 创建时间：2020年8月26日  下午11:05:56
 */
public class FileLinux {
	/** linux 文件分隔符*/
	public final static char separatorChar = '/';
	/** linux 文件分隔符*/
	public final static String separator = "" + separatorChar;
	
	private String path;
	private int prefixLength;
	private FileLinux(String pathname, int prefixLength) {
		path = new File(pathname).getPath().replaceAll(File.separator + File.separator, separator);
		this.prefixLength = prefixLength == -1 ? getPrefixLength() : prefixLength;
	}
	public FileLinux(String pathname) {
		this(pathname, -1);
	}
	private int getPrefixLength() {
		if (path.length() == 0) return 0;
		return (path.charAt(0) == '/') ? 1 : 0;
	}
	/**
	 * 获取上级路径
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:07:03
	 * @return
	 * </pre>
	 */
	public String getParent() {
		int index = path.lastIndexOf(separatorChar);
        if (index < prefixLength) {
            if ((prefixLength > 0) && (path.length() > prefixLength))
                return path.substring(0, prefixLength);
            return null;
        }
        return path.substring(0, index);
	}
	/**
	 * 获取路径
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:07:18
	 * @return
	 * </pre>
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 获取上级 linux 文件路径
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:07:37
	 * @return
	 * </pre>
	 */
	public FileLinux getParentFile() {
		String p = this.getParent();
        if (p == null) return null;
        return new FileLinux(p, this.prefixLength);
	}
	/**
	 * 获取名称
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:08:10
	 * @return
	 * </pre>
	 */
	public String getName() {
		int index = path.lastIndexOf(separatorChar);
        if (index < prefixLength) return path.substring(prefixLength);
        return path.substring(index + 1);
	}
	@Override
	public String toString() {
		return path;
	}
}
