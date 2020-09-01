package com.hx.utils.linux.defaults;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hx.utils.linux.FileLinux;
import com.hx.utils.linux.LinuxI;
import com.hx.utils.linux.model.LinuxUploadFileModel;
import com.jcraft.jsch.ChannelSftp;

/**
 * linux 文件工具类
 * @author hx
 * @version 创建时间：2020年8月24日  下午10:21:29
 */
public class LinuxFile extends LinuxUse implements LinuxI {
	final static Logger log = LoggerFactory.getLogger(LinuxFile.class);
	protected LinuxFile() {}
	public static LinuxFile newLinuxConn(String host, String userName, String password, int port) {
		return (LinuxFile) new LinuxFile().conn(host, userName, password, port);
	}
	/**
	 * 服务器是否存在文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月24日  下午11:13:59
	 * @param path
	 * @return
	 * </pre>
	 */
	public boolean existServerFile(String file) {
		String result = super.runCmd("if [ -f \"" + file + "\" ]; then echo \"true\"; else echo \"false\"; fi");
		return "true".equals(result);
	}
	/**
	 * 服务器是否存在文件夹
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:04:29
	 * @param dir
	 * @return
	 * </pre>
	 */
	public boolean existServerDir(String dir) {
		String result = super.runCmd("if [ -d \"" + dir + "\" ]; then echo \"true\"; else echo \"false\"; fi");
		return "true".equals(result);
	}
	/**
	 * 创建多级目录
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:04:43
	 * @param dir
	 * </pre>
	 */
	public void mkdirs(String dir) {
		super.runCmd("mkdir -p " + dir);
	}
	/**
	 * 上传文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月26日  下午11:05:09
	 * @param linuxUploadFileModels
	 * </pre>
	 */
	public void uploadFile(List<LinuxUploadFileModel> linuxUploadFileModels) {
		// 创建sftp通信通道
		ChannelSftp sftp = null;
		try {
			sftp = (ChannelSftp) session.openChannel("sftp");
			sftp.connect(1000);
			for (int i = 0; i < linuxUploadFileModels.size(); i++) {
				if (!existServerDir(linuxUploadFileModels.get(i).getServerFile().getParent())) {
					mkdirs(linuxUploadFileModels.get(i).getServerFile().getParent());
				}
				sftp.put(linuxUploadFileModels.get(i).getLocalFile().getAbsolutePath(), linuxUploadFileModels.get(i).getServerFile().getPath());
			}
		} catch (Exception e) {
			log.info("上传文件异常. 异常信息: ", e);
		} finally {
			if (null != sftp) {
				sftp.disconnect();
			}
		}
	}
//	public static void main(String[] args) throws Exception {
//		LinuxFile file = LinuxFile.newLinuxConn("10.0.0.77", "jikai", "dR!^5!FLCnX7XbpE", 52789);
//		String localPath = "D:/test empty.txt";
//		String serverPath = "/app/jikai/files/sh/2//2/test empty.txt";
////		LinuxUploadFileModel linuxUploadFileModel = LinuxUploadFileModel.newLinuxUploadFileModel(localPath, serverPath);
////		file.uploadFile(Arrays.asList(linuxUploadFileModel));
//		file.backFile("/app/jikai/files/sh/2");
//	}
	/**
	 * 备份文件
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月27日  下午11:00:11
	 * @param path
	 * @param backPath
	 * </pre>
	 */
	@Override
	public void backFile(String path, String... backPath) {
		FileLinux fileLinux = new FileLinux(path);
		path = fileLinux.getPath();
		if (!existServerDir(path)) {
			throw new RuntimeException("要备份的目录不存在. path: " + path);
		}
		String backPathTemp;
		if (backPath.length > 0) {
			backPathTemp = backPath[0];
		} else {
			backPathTemp = path + '_' + new SimpleDateFormat("yyyyMMdd").format(new Date());
		}
		while (true) {
			if (!existServerDir(backPathTemp)) {
				break;
			}
			backPathTemp += '_';
		}
		log.info("备份文件. path: {}. backPath: {}", path, backPathTemp);
		String result = super.runCmd(String.format("cp -r %s %s", path, backPathTemp));
		if (!"".equals(result)) {
			throw new RuntimeException("备份文件异常. result: " + result);
		}
	}
	/**
	 * 释放 linux 连接
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年9月1日  下午10:49:13
	 * </pre>
	 */
	@Override
	public void disconn() {
		super.session.disconnect();
	}
}
