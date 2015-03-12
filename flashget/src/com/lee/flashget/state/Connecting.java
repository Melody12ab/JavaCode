package com.lee.flashget.state;

import javax.swing.ImageIcon;

import com.lee.flashget.util.ImageUtil;

public class Connecting extends AbstractState {

	@Override
	public ImageIcon getIcon() {
		return ImageUtil.CONNECTING_IMAGE;
	}
	
	public String getState() {
		return "connecting";
	}
}
