package com.lee.flashget.navigation;

import javax.swing.ImageIcon;

import com.lee.flashget.util.ImageUtil;

/**
 * 下载完成节点
 * @author Melody12ab
 * @version  1.0
 */
public class FinishNode implements DownloadNode {

	public ImageIcon getImageIcon() {
		return ImageUtil.FINISH_NODE_IMAGE;
	}

	public String getText() {
		return "下载完成";
	}

}
