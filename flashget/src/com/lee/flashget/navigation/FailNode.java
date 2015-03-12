package com.lee.flashget.navigation;

import javax.swing.ImageIcon;

import com.lee.flashget.util.ImageUtil;

/**
 * 下载失败导航节点
 * @author Melody12ab
 * @version  1.0
 */
public class FailNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.FAIL_NODE_IMAGE;
	}

	public String getText() {
		return "下载失败";
	}

}
