package com.applitools.utils.web;

import com.applitools.excel.ExcelUtils;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.applitools.utils.eyes.ApplitoolsMetaData;
import com.applitools.utils.eyes.EyesInstance;

import java.util.ArrayList;

public class BaseWebTest {
	
	private static String applitoolsApiKey;
	private static Configuration config;
	protected static EyesRunner runner;
	private String appName;
//	private String testName;

	// Test-specific objects
	protected Eyes eyes;
	
	protected WebDriverUtil webdriverUtil;
	
	private BatchInfo batchInfo;
	
	public BaseWebTest() {
		this.appName = ApplitoolsMetaData.instance().getAppName();
		this.batchInfo = new BatchInfo(ApplitoolsMetaData.instance().getBatchName());
		this.batchInfo.setId(ApplitoolsMetaData.instance().getBatchId());
	}
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

		applitoolsApiKey = ApplitoolsMetaData.instance().getApiKey();

		runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));

		// Create a configuration for Applitools Eyes.
		config = new Configuration();
		
		config.setApiKey(applitoolsApiKey);
		
		config.setLayoutBreakpoints(true);

		config.addBrowser(800, 600, BrowserType.CHROME);
		config.addBrowser(1200, 800, BrowserType.CHROME);
		config.addBrowser(800, 600, BrowserType.CHROME_ONE_VERSION_BACK);
//		config.addBrowser(1600, 1200, BrowserType.CHROME_TWO_VERSIONS_BACK);
		config.addBrowser(800, 1024, BrowserType.FIREFOX);
		config.addBrowser(600, 1100, BrowserType.SAFARI);
		config.addBrowser(800, 600, BrowserType.SAFARI);
//		config.addBrowser(1600, 1200, BrowserType.EDGE_CHROMIUM);
		config.addDeviceEmulation(DeviceName.Pixel_4_XL, ScreenOrientation.PORTRAIT);
		config.addDeviceEmulation(DeviceName.iPhone_11_Pro_Max, ScreenOrientation.PORTRAIT);
		config.addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.PORTRAIT);
		config.addDeviceEmulation(DeviceName.Galaxy_S20, ScreenOrientation.PORTRAIT);
		config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);


	}

	@BeforeMethod
	public void openBrowserAndEyes(ITestResult testResult) {
		
		// Set the batch for the config.
		config.setBatch(batchInfo);
		
		eyes = new Eyes(runner);
		
		eyes.setApiKey(applitoolsApiKey);
		eyes.setConfiguration(config);

		this.webdriverUtil = new WebDriverUtil();
		
		EyesInstance.instance().setEyesInstance(eyes);
		
		eyes.open(this.webdriverUtil.getWebDriver(), // WebDriver object to "watch"
				this.appName, // The name of the app under test
				testResult.getMethod().
				getDescription() // The name of the test case
		);
	}
	
	@AfterMethod
	public void cleanUpTest() {
		
		// Close Eyes to tell the server it should display the results.
		eyes.closeAsync();

		// Quit the WebDriver instance.
		webdriverUtil.quit();
	}

	@AfterSuite
	public static void printResults() {

		TestResultsSummary allTestResults = runner.getAllTestResults();
		System.out.println(allTestResults);
	}

}
