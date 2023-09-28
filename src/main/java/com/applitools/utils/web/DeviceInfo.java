package com.applitools.utils.web;

import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;

public class DeviceInfo {
	
	private String orientation;
	private String deviceName;
	
	public DeviceName getDeviceType() {
		return DeviceName.fromName(deviceName);
	}
	
	public ScreenOrientation getOrientation() {
		return ScreenOrientation.fromOrientation(orientation);
	}

	@Override
	public String toString() {
		return DeviceName.fromName(deviceName).toString();
	}
	
	
	
}
