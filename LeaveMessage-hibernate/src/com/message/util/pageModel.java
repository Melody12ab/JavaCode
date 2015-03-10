package com.message.util;

import java.util.List;

public class pageModel {
	//当前页数
	private int currpage;
	//总的记录数
	private int totalRecord;
	private List<?> list;
	//每页记录数
	private int pageSize;
	
	public int getCurrpage() {
		return currpage;
	}

	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	//总的页数目
	public int getTotalPage(){
		return (totalRecord/pageSize)+1;
	}
	
	public int getFirstPage(){
		return 1;
	}
	
	//最后一页
	public int getLastPage(){
		return getTotalPage()<=0 ? 1 : getTotalPage();
	}
	
	//获取上一页
	public int getPreviousPage(){
		return currpage <=1 ? 1 : currpage-1;
	}
	
	//获取下一页
	public int getNextPage(){
		if(currpage >= getTotalPage()){
			return getTotalPage();
		}
		return currpage+1;
	}
	
}
