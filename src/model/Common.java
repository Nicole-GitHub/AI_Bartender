package model;

import util.CommonUtil;

public class Common {
	private String action; // not real
	private Integer pageNum;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getPageNum() {
		pageNum = new CommonUtil().StringToInt(pageNum);
		return pageNum < 0 ? 0 : pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

}
