package com.applitools.utils.web;

import java.util.Objects;

public class WebDrivers {
	
	private static WebDrivers webDrivers;
	private ThreadLocal<WebDriverUtil> drivers;
	private WebDrivers() {
		this.drivers = new ThreadLocal<WebDriverUtil>();
	}
	
	public static WebDrivers instances() {
		if(Objects.isNull(webDrivers)) {
			webDrivers = new WebDrivers();
		}
		return webDrivers;
	}
	
	public void setDriver(WebDriverUtil driver) {
		this.drivers.set(driver);
	}
	
	public WebDriverUtil getDriver() {
		return this.drivers.get();
	}
	
}
