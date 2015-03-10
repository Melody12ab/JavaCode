package com.lee.mysql.object.list;

import javax.swing.Icon;

import com.lee.mysql.object.ViewObject;


public abstract class AbstractData implements ViewObject {
	
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract String toString();
}
