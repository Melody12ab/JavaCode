package com.lee.flashget.state;

import javax.swing.ImageIcon;

import com.lee.flashget.object.Resource;
import com.lee.flashget.util.ImageUtil;

public class Downloading extends AbstractState {

	@Override
	public ImageIcon getIcon() {
		return ImageUtil.DOWNLOADING_IMAGE;
	}
	public String getState() {
		return "downloading";
	}
}
