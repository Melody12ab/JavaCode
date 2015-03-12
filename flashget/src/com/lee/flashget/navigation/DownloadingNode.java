package com.lee.flashget.navigation;

import javax.swing.ImageIcon;

import com.lee.flashget.util.ImageUtil;

/**
 * 正在下载的导航节点
 * @author Melody12ab
 * @version  1.0
 */
public class DownloadingNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.DOWNLOADING_NODE_IMAGE;
	}

	public String getText() {
		return "正在下载";
	}

}
