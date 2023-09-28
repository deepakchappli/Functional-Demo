package com.applitools.utils.web;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.applitools.eyes.selenium.Eyes;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverUtil {

	private WebDriver driver;
	
	public void switchIframe(By iframeLocator) {
		WebElement iFrame = getObject(iframeLocator, 30);
		this.driver.switchTo().frame(iFrame);
	}
	
	public void resetFrame() {
		this.driver.switchTo().defaultContent();
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}
	
	public void getUrl(String url) {
		this.driver.get(url);
	}
	
	public void quit() {
		try {
			this.driver.quit();
		}catch(Exception e) {
			
		}
		
	}
	
	public String getCurrentWindowHandle() {
		return this.driver.getWindowHandle();
	}
	
	public Set<String> getCurrentWindowHandles() {
		return this.driver.getWindowHandles();
	}
	
	public void switchToWindow(String windowHandle) {
		this.driver.switchTo().window(windowHandle);
	}
	
	public void close() {
		this.driver.close();
	}
	
	public WebDriverUtil() {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions chromeOption = new ChromeOptions();
		
		chromeOption.addArguments("--remote-allow-origins=*");
		chromeOption.addArguments("start-maximized");
		
		driver = new ChromeDriver(chromeOption);
		
		WebDrivers.instances().setDriver(this);
	}
	
	public WebDriverUtil(Capabilities capabilities) {
		try {
			driver = new RemoteWebDriver(new URL(Eyes.getExecutionCloudURL()), capabilities);
			WebDrivers.instances().setDriver(this);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void takeScreenshot() {
		File scrShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(scrShot, new File("failedScreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void refresh() {
		driver.navigate().refresh();
	}

	public WebElement getObject(By locator,int timeOutSecond) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutSecond));
		wait.pollingEvery(Duration.ofSeconds(1));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void executeJS(String jsCommand) {
		((JavascriptExecutor)driver).executeScript(jsCommand);
	}

	public WebElement click(By locator) {
		WebElement element = getObject(locator,60);
		element.click();
		return element;
	}

	public void setValue(By locator, String value) {
		WebElement element = getObject(locator,60);
		element.sendKeys(value);
	}
	
	public String getAttribute(By locator, String attributeName) {
		WebElement element = getObject(locator,60);
		return element.getAttribute(attributeName);
	}
	
	public void click(By locator,int timeOutInSecond) {
		WebElement element = getObject(locator,timeOutInSecond);
		element.click();
	}

	public void setValue(By locator, String value,int timeOutInSecond) {
		WebElement element = getObject(locator,timeOutInSecond);
		element.sendKeys(value);
	}
	
	public String getAttribute(By locator, String attributeName,int timeOutInSecond) {
		WebElement element = getObject(locator,timeOutInSecond);
		return element.getAttribute(attributeName);
	}

    public WebElement getAttribute(By otherCreditCardsNo) {
		return null;
	}
}
