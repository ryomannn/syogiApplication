package com.example.demo.profile.domain.model;

import java.io.Serializable;


public class Page implements Serializable{

	private static final long seralVersionUID =1L;

	private int page;

	private boolean firstPage;

	private boolean lastPage;

	public Page(int page, boolean firstPage, boolean lastPage) {
		this.page = page;
		this.firstPage = firstPage;
		this.lastPage = lastPage;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public int getPage() {
		return  page;
	}

	public boolean getFirstPage() {
		return  firstPage;
	}

	public boolean getLastPage() {
		return lastPage ;
	}




	public void isFirstPage(int page) {

		if(page == 1) {
			this.firstPage = true;
		}
		else {
			this.firstPage = false;
		}
	}

	public void isLastPage(int page, int lastPage) {

		if(page == lastPage) {
			this.lastPage = true;
		}
		else {
			this.lastPage = false;
		}
	}
}