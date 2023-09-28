package com.applitools.utils.eyes;

import java.util.Objects;

import com.applitools.eyes.selenium.Eyes;

public class EyesInstance {
	
	private static EyesInstance eyesInstance;
	private ThreadLocal<Eyes> eyesTL;
	
	private EyesInstance() {
		this.eyesTL = new ThreadLocal<>();
	}
	
	public static EyesInstance instance() {
		if(Objects.isNull(eyesInstance)) {
			eyesInstance = new EyesInstance();
		}
		return eyesInstance;
	}
	
	public Eyes getEyesInstance() {
		return this.eyesTL.get();
	}
	
	public void setEyesInstance(Eyes eyes) {
		this.eyesTL.set(eyes);
	}

}
