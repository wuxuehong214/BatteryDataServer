package com.green.battery.action;

import com.opensymphony.xwork2.ActionSupport;

public class LangAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6324218467339289612L;
	private String page;

	public String change(){
		page = "login";
		return "target";
	}
	
	public String jsp(){
		return "target";
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
