package com.vincent.tools.qiniu.cloud.spring.boot.starter.core;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Vincent
 * <p> DateTime: 2023/10/17 21:13
 * <p> Description:  核心逻辑类
 * <p> TODO:
 **/

public class QiNiuCloudHandler {
	private QiNiuCloudProperties properties;
	
	public QiNiuCloudHandler(QiNiuCloudProperties properties) {
		this.properties = properties;
	}
	
	/**
	 * 上传图片到服务器
	 *
	 * @param content  文件字节数组文件
	 * @param fileName 上传文件名称
	 * @return 保存地址
	 */
	public String upload(byte[] content, String fileName) throws QiniuException {
		Configuration cfg = new Configuration(Region.huadongZheJiang2());
		Auth auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
		cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
		UploadManager uploadManager = new UploadManager(cfg);
		String upToken = auth.uploadToken(properties.getBucket());
		uploadManager.put(content, fileName, upToken);
		return properties.getDomain() + fileName;
	}
	
	/**
	 * 同域名之间移动图片到指定位置
	 * 例如:
	 * move(a/test.jpg,b/test/jpg)	将a/目录下的test.jpg文件移动到b/目录下
	 *
	 * @param oldFileName 源文件路径(不包含域名地址,仅包含 路径+文件名)
	 * @param newFileName 新文件路径地址
	 * @return 返回值
	 */
	public String move(String oldFileName, String newFileName) throws QiniuException {
		Configuration cfg = new Configuration(Region.huadongZheJiang2());
		Auth auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
		BucketManager bucketManager = new BucketManager(auth, cfg);
		bucketManager.move(properties.getBucket(), oldFileName, properties.getBucket(), newFileName);
		return "上传成功";
	}
	
	/**
	 * 查询目标目录所有的文件
	 *
	 * @return 文件名数组
	 */
	public String[] showFiles() throws QiniuException {
		List<String> list = new ArrayList<>();
		//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
		String delimiter = "";
		Configuration cfg = new Configuration(Region.huadongZheJiang2());
		Auth auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
		BucketManager bucketManager = new BucketManager(auth, cfg);
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(properties.getBucket(), properties.getPrefix(), properties.getLimit(), delimiter);
		while (fileListIterator.hasNext()) {
			FileInfo[] items = fileListIterator.next();
			for (int i = 0; i < items.length; i++) {
				list.add(items[i].key);
			}
		}
		return list.toArray(new String[0]);
	}
	
	/**
	 * 删除指定文件内容
	 *
	 * @param files 文件名数组
	 */
	public void remove(String[] files) throws QiniuException {
		Configuration cfg = new Configuration(Region.huadongZheJiang2());
		Auth auth = Auth.create(properties.getAccessKey(), properties.getSecretKey());
		BucketManager bucketManager = new BucketManager(auth, cfg);
		BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		batchOperations.addDeleteOp(properties.getBucket(), files);
		Response response = bucketManager.batch(batchOperations);
		response.jsonToObject(BatchStatus[].class);
	}
}
