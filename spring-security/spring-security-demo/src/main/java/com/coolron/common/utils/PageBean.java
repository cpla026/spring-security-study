package com.coolron.common.utils;

import java.util.List;

/**
 * 分页的实体类
 * 
 * @author Admin
 *
 */
public class PageBean<T> {
	private int currpage; // 当前页码
	private int pageSize; // 每页显示记录数
	private int totalCount; // 总记录数
	private int totalPage;; // 总页数
	private List<T> pageList; // 当前页数据
	public int getCurrpage() {
		return currpage;
	}
	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getPageList() {
		return pageList;
	}
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
}
