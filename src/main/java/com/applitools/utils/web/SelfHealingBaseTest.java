package com.applitools.utils.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlTest;

import com.applitools.excel.ExcelUtils;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.applitools.utils.eyes.ApplitoolsMetaData;
import com.applitools.utils.eyes.EyesInstance;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import com.google.gson.Gson;

public class SelfHealingBaseTest {

	private static Configuration config;
	protected static EyesRunner runner;

	protected WebDriverUtil webdriverUtil;

	private static BatchInfo batchInfo;

	public void startHealing(String testName) {

		HashMap<String, String> nonEyes = new HashMap<>();
		nonEyes.put("testName", testName);

		((JavascriptExecutor) webdriverUtil.getWebDriver()).executeScript("applitools:startTest", nonEyes);
	}

	public void stopHealing(String status) {
		HashMap<String, String> nonEyes = new HashMap<>();
		nonEyes.put("status", status);

		((JavascriptExecutor) webdriverUtil.getWebDriver()).executeScript("applitools:endTest", nonEyes);

	}

	public SelfHealingBaseTest() {}
	
	@DataProvider
	public Object[][] getTestDataFromExcel(ITestNGMethod method) {
				
		String description = method.getDescription().replaceAll("\\s+", "_");
		
		try {
			Recordset testData = ExcelUtils.instance().getData(description);
			
			ArrayList<String> columns = testData.getFieldNames();
			
			String[][] testDatas = new String[testData.getCount()][];
			
			String[] currentSet;
			
			int rowIndex = 0;
			int colmIndex;
			
			while(testData.next()) {
				currentSet = new String[columns.size()];
				colmIndex = 0;
				for(String column:columns) {
					currentSet[colmIndex++] = testData.getField(column);
				}
				
				testDatas[rowIndex++] = currentSet;
			}
			
			return testDatas;
			
		} catch (FilloException e) {
			e.printStackTrace();
		}
		
		return new Object[0][0];
	}
	
	@BeforeSuite
	public static void setUpConfigAndRunner() {
		
		// Create a configuration for Applitools Eyes.
		config = new Configuration();
		
		int threadConcurrency = ApplitoolsMetaData.instance().getThreadConcurrency();

		if ("VG".equalsIgnoreCase(ApplitoolsMetaData.instance().getRunnerType())) {
			runner = new VisualGridRunner(new RunnerOptions().testConcurrency(threadConcurrency));
			
			BrowserInfo[] targetedBrowsers = ApplitoolsMetaData.instance().getTargetedBrowsers();
			
			for(BrowserInfo targetedBrowser:targetedBrowsers) {
				config.addBrowser(targetedBrowser.getWidth(), targetedBrowser.getHeight(), 
						targetedBrowser.getBrowserType());
			}
			
			DeviceInfo[] targetedDevices = ApplitoolsMetaData.instance().getTargetedDevices();
			
			for(DeviceInfo targetedDevice:targetedDevices) {
				config.addDeviceEmulation(targetedDevice.getDeviceType(), targetedDevice.getOrientation());
			}
		}
		
		config.setApiKey(ApplitoolsMetaData.instance().getApiKey());

		config.setLayoutBreakpoints(true);

		

		batchInfo = new BatchInfo(ApplitoolsMetaData.instance().getBatchName());
		batchInfo.setId(ApplitoolsMetaData.instance().getBatchId());
		// Set the batch for the config.
		config.setBatch(batchInfo);

		Eyes eyes = new Eyes(runner);
		eyes.setBatch(batchInfo);
		eyes.setServerUrl(ApplitoolsMetaData.instance().getCloudURL());
		eyes.setConfiguration(config);
		EyesInstance.instance().setEyesInstance(eyes);

	}

	private Capabilities getDesiredCapabilities(XmlTest xmlTest)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		
		Map<String, Object> capabilitiesMap = new HashMap<>();

		String browserCapClassName = xmlTest.getParameter("browser.options.class");

		browserCapClassName = Objects.isNull(browserCapClassName) ? "org.openqa.selenium.chrome.ChromeOptions"
				: browserCapClassName;

		Class<?> capClass = Class.forName(browserCapClassName);
		MutableCapabilities mutableCap = ((MutableCapabilities) capClass.getConstructor().newInstance());
		capabilitiesMap.putAll(mutableCap.asMap());

		Gson gson = new Gson();
		
		// Adding Browser capabilities
		String browserCaps = xmlTest.getParameter("browser.capabilities");
		browserCaps = Objects.isNull(browserCaps) ? "{}" : browserCaps;

		@SuppressWarnings("unchecked")
		Map<String, Object> browserCapabilities = gson.fromJson(browserCaps, Map.class);
		capabilitiesMap.putAll(browserCapabilities);
		
		// Adding Options capabilities
		String optionCapValues = xmlTest.getParameter("browser.options.values");
		optionCapValues = Objects.isNull(optionCapValues) ? "{}" : optionCapValues;

		@SuppressWarnings("unchecked")
		Map<String, Object> optionsCapabilities = gson.fromJson(optionCapValues, Map.class);
		capabilitiesMap.putAll(optionsCapabilities);
		
		// Adding Applitools Options capabilities
		HashMap<String, String> applitoolsOptions = new HashMap<>();
		applitoolsOptions.put("useSelfHealing", ApplitoolsMetaData.instance().performSelfHealing());
		applitoolsOptions.put("apiKey", ApplitoolsMetaData.instance().getApiKey());

		capabilitiesMap.put("applitools:options", applitoolsOptions);

		return new DesiredCapabilities(capabilitiesMap);
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest(XmlTest xmlTest)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		Capabilities desiredCapabilities = getDesiredCapabilities(xmlTest);

		this.webdriverUtil = new WebDriverUtil(desiredCapabilities);
	}

	@BeforeMethod(alwaysRun = true)
	public void openBrowserAndEyes(XmlTest xmlTest, ITestResult result) {

		String testName = result.getMethod().getDescription();
		
		System.out.println(" *** Test Started - " + testName);

		Eyes eyes = EyesInstance.instance().getEyesInstance();

		eyes.open(this.webdriverUtil.getWebDriver(), // WebDriver object to "watch"
				ApplitoolsMetaData.instance().getAppName(), // The name of the app under test
				testName // The name of the test case
		);

		// =================== Start Healing ===================
		if ("true".equalsIgnoreCase(ApplitoolsMetaData.instance().performSelfHealing())) {
			startHealing(testName);
		}

	}

	@AfterMethod(alwaysRun = true)
	public void cleanUpTest(ITestResult result) {
		
		String testName = result.getMethod().getDescription();
		
		String testResult = result.isSuccess() ? "Passed" : "Failed";
		
		System.out.println(" *** Test Completed - " + testName + " Result - " + testResult);

		
		if ("true".equalsIgnoreCase(ApplitoolsMetaData.instance().performSelfHealing())) {
			stopHealing(testResult);
		}

		Eyes eyes = EyesInstance.instance().getEyesInstance();
		// Close Eyes to tell the server it should display the results.
		eyes.closeAsync();
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		// Quit the WebDriver instance.
		webdriverUtil.quit();
	}

	@AfterSuite
	public static void printResults() {

		TestResultsSummary allTestResults = runner.getAllTestResults();
		System.out.println(allTestResults);
	}

}
