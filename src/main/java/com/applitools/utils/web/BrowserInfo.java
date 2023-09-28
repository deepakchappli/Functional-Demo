package com.applitools.utils.web;

import com.applitools.eyes.selenium.BrowserType;

public class BrowserInfo {
	
	private int height;
	private int width;
	private String browserName;
	
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	
	public BrowserType getBrowserType() {
		return BrowserType.fromName(browserName);
	}
	
	@Override
	public String toString() {
		
		return BrowserType.fromName(browserName).toString();
	}
	
}
