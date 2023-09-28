package com.applitools.utils.eyes;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

import com.applitools.utils.web.BrowserInfo;
import com.applitools.utils.web.DeviceInfo;
import com.google.gson.Gson;

public class ApplitoolsMetaData {

	private static ApplitoolsMetaData applitoolsBatch;
	private String batchName;
	private String batchId;
	private String appName;
	private String apiKey;
	private String cloudURL;
	private String selfHealing;
	private int threadConcurrency;
	private String runnerType;
	private BrowserInfo[] targetedBrowsers;
	private DeviceInfo[] targetedDevices;

	private ApplitoolsMetaData() {

		this.batchName = System.getenv("APPLITOOLS_BATCH_NAME");
		this.batchId = System.getenv("APPLITOOLS_BATCH_ID");
		this.appName = System.getenv("APPLITOOLS_APP_NAME");
		this.apiKey = System.getenv("APPLITOOLS_API_KEY");
		this.cloudURL = System.getenv("APPLITOOLS_SERVER_URL");
		this.selfHealing  = System.getenv("APPLITOOLS_USE_SELF_HEALING");
		
		String thConcurrency = System.getenv("APPLITOOLS_CONCURRENCY");
		
		Properties appProperties = new Properties();

		Path appPropertiesFile = Paths.get("src/test/resources/application.properties");

		try (FileInputStream fis = new FileInputStream(appPropertiesFile.toFile())) {
			appProperties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String batchNameKey = "applitools.batch.name";
		String batchIdKey = "applitools.batch.id";
		String appNameKey = "applitools.app.name";
		String apiKeyKey = "applitools.api.key";
		String cloudNameKey = "applitools.cloud.name";
		String selfHealingKey = "applitools.enable.self.healing";
		String threadConKey = "applitools.thread.concurrency";
		String runnerTypeKey = "applitools.runner.type";
		String browserInfoKey = "applitools.browser.info";
		String deviceInfoKey = "applitools.device.info";
		
		if (Objects.isNull(this.selfHealing)) {
			selfHealing = appProperties.getProperty(selfHealingKey, "false");
		}
		
		if (Objects.isNull(this.batchName)) {
			this.cloudURL = appProperties.getProperty(cloudNameKey, "https://eyesapi.applitools.com/");
		}
		
		if (Objects.isNull(this.batchName)) {
			this.batchName = System.getProperty(batchNameKey, appProperties.getProperty(batchNameKey, "Default_Batch"));
		}

		if (Objects.isNull(this.batchId)) {
			this.batchId = System.getProperty(batchIdKey, appProperties.getProperty(batchIdKey, "1"));
		}

		if (Objects.isNull(this.appName)) {
			this.appName = System.getProperty(appNameKey, appProperties.getProperty(appNameKey, "Default_App"));
		}

		if (Objects.isNull(this.apiKey)) {
			this.apiKey = System.getProperty(apiKeyKey, appProperties.getProperty(apiKeyKey));
		}
		
		if (Objects.isNull(thConcurrency)) {
			thConcurrency = System.getProperty(threadConKey, appProperties.getProperty(threadConKey,"5"));
		}
		
		this.threadConcurrency = Integer.parseInt(thConcurrency);
		
		runnerType = System.getProperty(runnerTypeKey, appProperties.getProperty(runnerTypeKey,"CL"));
		Gson gson = new Gson();
		String targetBrowserStr = System.getProperty(browserInfoKey, appProperties.getProperty(browserInfoKey,"[]"));
		
		targetedBrowsers = gson.fromJson(targetBrowserStr, BrowserInfo[].class);
		
		String targetDeviceStr = System.getProperty(deviceInfoKey, appProperties.getProperty(deviceInfoKey,"[]"));
		
		targetedDevices = gson.fromJson(targetDeviceStr, DeviceInfo[].class);
	}

	public static ApplitoolsMetaData instance() {

		if (Objects.isNull(applitoolsBatch)) {
			applitoolsBatch = new ApplitoolsMetaData();
		}

		return applitoolsBatch;
	}

	public String getBatchName() {
		return batchName;
	}

	public String getBatchId() {
		return batchId;
	}

	public String getAppName() {
		return appName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getCloudURL() {
		return cloudURL;
	}

	public String performSelfHealing() {
		return selfHealing;
	}

	public int getThreadConcurrency() {
		return threadConcurrency;
	}

	public String getRunnerType() {
		return runnerType;
	}

	public BrowserInfo[] getTargetedBrowsers() {
		return targetedBrowsers;
	}

	public DeviceInfo[] getTargetedDevices() {
		return targetedDevices;
	}
	
}
