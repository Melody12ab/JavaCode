package com.lee.flashget.navigation;

import javax.swing.ImageIcon;

import com.lee.flashget.util.ImageUtil;

/**
 * 全部下载资源(任务)节点
 * 
 * @version  1.0
 */
public class TaskNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.TASK_NODE_IMAGE;
	}

	public String getText() {
		return "任务";
	}

}
