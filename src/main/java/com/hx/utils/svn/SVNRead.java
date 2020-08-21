package com.hx.utils.svn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.hx.utils.svn.model.SVNInfoModel;
import com.hx.utils.svn.model.SVNPathModel;
import com.hx.utils.svn.model.enums.FileStatusEnums;

/**
 * SVN 读操作
 * @author hx
 * @version 创建时间：2020年8月21日  下午10:22:30
 */
public class SVNRead {
	final static Logger log = LoggerFactory.getLogger(SVNUtils.class);

	private	SVNURL svnURL;
	private SVNClientManager client;
	private ISVNOptions options;
	private SVNRepository repository;
	public SVNRead(String url, String userName, String password) {
		if (null == url || !url.startsWith("svn://")) {
			throw new RuntimeException("请输入正确的 svn 地址. url: " + url);
		}
		SVNRepositoryFactoryImpl.setup();
		try {
			svnURL = SVNURL.parseURIEncoded(url);
		} catch (SVNException e) {
			throw new RuntimeException("转换 svn 链接异常. ", e);
		}
		try {
			repository = SVNRepositoryFactory.create(svnURL, null);
		} catch (SVNException e) {
			throw new RuntimeException("创建资源库异常. ", e);
		}
		ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName, password.toCharArray());
		repository.setAuthenticationManager(authManager);
		client = SVNClientManager.newInstance(options, authManager);
	}
	/**
	 * 获取最新版本号
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月20日  下午10:44:12
	 * @return
	 * </pre>
	 */
	public long getLastVersion() {
		SVNInfo info;
		try {
			info = client.getWCClient().doInfo(svnURL, SVNRevision.HEAD, SVNRevision.HEAD);
			return info.getCommittedRevision().getNumber();
		} catch (SVNException e) {
			log.info("获取最新版本号异常. 异常信息: ", e);
			return -1;
		}
	}
	/**
	 * 获取 svn 日志文件信息列表
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月21日  下午10:15:38
	 * @param startVersion
	 *    传入 -1 只获取最新版本文件
	 * @param endVersion 
	 *     可为空. 为空获取最新版本号
	 * @return
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public List<SVNInfoModel> getSVNInfos(long startVersion, long... endVersion) {
		Collection<SVNLogEntry> logEntries = null;
		try {
			long endVersionTemp;
			if (startVersion == -1) {
				startVersion = getLastVersion();
				endVersionTemp = startVersion;
			} else {
				if (endVersion.length == 0) {
					endVersionTemp = endVersion[0];
				} else {
					endVersionTemp = getLastVersion();
				}
			}
			logEntries = repository.log(new String[]{""}, null, startVersion, endVersionTemp, true, true);
		} catch (SVNException e) {
			log.info("获取 svn 日志文件异常. 异常信息: ", e);
		}
        if (null == logEntries) {
        	return Collections.emptyList();
        }
        List<SVNInfoModel> infoModels = new ArrayList<>();
        Iterator<SVNLogEntry> entries = logEntries.iterator();
        SVNInfoModel infoModel = null;
        while (entries.hasNext()) {
        	SVNLogEntry logEntry = entries.next();
        	infoModel = new SVNInfoModel();
        	infoModel.setAuthor(logEntry.getAuthor());
        	infoModel.setMessage(logEntry.getMessage());
        	infoModel.setDate(logEntry.getDate());
        	infoModel.setVersion(logEntry.getRevision());
        	if (logEntry.getChangedPaths().size() > 0) {
        		Iterator<Entry<String, SVNLogEntryPath>> iterator = logEntry.getChangedPaths().entrySet().iterator();
        		
        		List<SVNPathModel> pathModels = new ArrayList<SVNPathModel>();
        		SVNPathModel pathModel = null;
        		while (iterator.hasNext()) {
        			SVNLogEntryPath logEntryPath = iterator.next().getValue();
        			pathModel = new SVNPathModel();
        			pathModel.setType(FileStatusEnums.toEnum(logEntryPath.getType() + ""));
        			pathModel.setPath(logEntryPath.getPath());
        			pathModels.add(pathModel);
				}
        		infoModel.setPathModels(pathModels);
        	}
        	infoModels.add(infoModel);
        }
        return infoModels;
	}
	/**
	 * 获取 svn 修改文件列表
	 * <pre>
	 * @author hx
	 * @version 创建时间：2020年8月21日  下午10:21:01
	 * @param startVersion
	 *     传入 -1 只获取最新版本文件
	 * @param endVersion
	 *     可为空. 为空获取最新版本号
	 * @return
	 * </pre>
	 */
	public List<SVNPathModel> getSVNPaths(long startVersion, long... endVersion) {
		List<SVNInfoModel> svnInfos = this.getSVNInfos(startVersion, endVersion);
		List<SVNPathModel> paths = new ArrayList<>();
		for (int i = 0; i < svnInfos.size(); i++) {
			paths.addAll(svnInfos.get(i).getPathModels());
		}
		return paths;
	}
}
