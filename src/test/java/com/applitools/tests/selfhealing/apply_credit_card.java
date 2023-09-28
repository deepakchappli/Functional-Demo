package com.applitools.tests.selfhealing;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.utils.eyes.EyesInstance;
import com.applitools.utils.web.SelfHealingBaseTest;
import com.applitools.utils.web.BaseWebTest;
import com.codoid.products.exception.FilloException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

//public class apply_credit_card extends SelfHealingBaseTest{
public class apply_credit_card extends BaseWebTest {

	@Test(description = "First Test", dataProvider = "getTestDataFromExcel", enabled = true)
	public void test(String userName, String password) throws FilloException {
		
		Eyes eyes = EyesInstance.instance().getEyesInstance();
		eyes.setForceFullPageScreenshot(true);
		eyes.setStitchMode(StitchMode.CSS);
		
		webdriverUtil.getUrl("https://www.halodoc.com/corporate-partnership");
		
		// =================== Break selector ===================
//		webdriverUtil.executeJS("document.querySelector('#username').setAttribute('id', 'username_12345');");
//		webdriverUtil.executeJS("document.querySelector('#password').setAttribute('id', 'password_1234567');");
//
//		webdriverUtil.setValue(By.id("username"), userName);
//
//		webdriverUtil.setValue(By.id("password"), password);
//
//		webdriverUtil.click(By.id("log-in"));

		eyes.check("Login Window", Target.region(By.cssSelector("#hd-openNav > div > app-root > div > div.page-content.h-100.w-100.d-flex.flex-column")).fully().withName("Home Page"));
		;

		
	}


	@Test(description = "Credit card apply", dataProvider = "getTestDataFromExcel",enabled = false)
	public void apply_mandatory_fields(String firstName, String email, String Salary, String Savings, String Assets, String total, String other, String rent, String finance,
									   String credLimit, String repayment, String owned, String lastname, String mobile) throws FilloException, InterruptedException {

		Eyes eyes = EyesInstance.instance().getEyesInstance();
		eyes.setIgnoreDisplacements(true);

		webdriverUtil.getUrl("https://www.westpac.com.au/personal-banking/credit-cards/reward/altitude-velocity-platinum/summary/");
		eyes.check(Target.window().fully().withName("WHAT YOU'RE APPLYING FOR"));

		webdriverUtil.click(By.id("ready-to-apply-checkbox-gel-fieldtext-children"));
		webdriverUtil.click(By.xpath("//*[@id=\"-gel-fieldtext-children\"]/div[1]"));
		webdriverUtil.click(By.id("apply-now"));

		webdriverUtil.click(By.xpath("//*[@id=\"container\"]/div[1]/form[1]/section/article[1]/div/div/div/div[1]/div[2]/div/input[1]")); //Are you a westpac customer: NO

		eyes.check(Target.window().fully().withName("Are you a westpac customer?"));
		webdriverUtil.click(By.xpath("//*[@id=\"container\"]/div[1]/div/div[1]/a[2]"));	//Continue button

		//click on the continue button of the Get started Page
		webdriverUtil.click(By.id("spbtnsubmit1"));

		eyes.check(Target.window().fully().withName("Get started(Error)"));
		//Input the details
		webdriverUtil.setValue(By.id("firstName"), firstName);
		webdriverUtil.setValue(By.id("emailInput"), email);
		webdriverUtil.setValue(By.id("salaryA"), Salary);
		webdriverUtil.setValue(By.id("savings"), Savings);
		//other assests dropdown
		webdriverUtil.click(By.xpath("//*[@id=\"otherAssets\"]"));
		webdriverUtil.click(By.xpath("//*[@id=\"otherAssets\"]/option[2]"));
		webdriverUtil.setValue(By.id("otherAssetsAmount"), Assets);
		//relationship status
		webdriverUtil.click(By.xpath("//*[@id=\"financeForm\"]/div[1]/div[8]/div[3]/div[1]"));
		webdriverUtil.click(By.xpath("//*[@id=\"relationShipStatus\"]/option[2]"));
		//dependants
		webdriverUtil.click(By.xpath("//*[@id=\"financeForm\"]/div[1]/div[8]/div[4]/div[1]"));
		webdriverUtil.click(By.xpath("//*[@id=\"dependentsNumber\"]/option[2]"));
		//Renting situation
		webdriverUtil.click(By.xpath("//*[@id=\"financeForm\"]/div[1]/div[8]/div[5]/div[1]"));
		webdriverUtil.click(By.xpath("//*[@id=\"housingSituation\"]/option[5]"));
		//Liabilities Section
		webdriverUtil.click(By.id("otherHomeLoansNo"));
		webdriverUtil.click(By.id("otherPersonalLoansNo"));
		webdriverUtil.click(By.id("otherCreditCardsNo"));
		webdriverUtil.click(By.xpath("//div[@id='otherLoans1']/input[@id='otherCreditCardsNo']"));
		webdriverUtil.setValue(By.id("rentOrBoardAmount"), rent);
		webdriverUtil.setValue(By.id("totalLivingExpensesSimpleCalc"), total);
		webdriverUtil.setValue(By.id("totalOtherRegExpesesSimpleCalc"), other);
		webdriverUtil.click(By.id("futureCircumstancesNo"));
		eyes.check(Target.window().fully().withName("Get started"));

		webdriverUtil.click(By.id("spbtnsubmit1"));
		eyes.check(Target.window().fully().withName("Confirm & continue"));

		webdriverUtil.click(By.xpath("//*[@id=\"ng-app\"]/body/div[4]/div/div/div/div[3]/div[1]/button"));
		webdriverUtil.click(By.id("btnNext"));
		eyes.check(Target.window().fully().withName("Credit Limit(Error)"));

		webdriverUtil.click(By.id("creditLimitType"));
		webdriverUtil.click(By.id("title"));
		webdriverUtil.click(By.id("title"));
		webdriverUtil.click(By.xpath("//*[@id=\"title\"]/option[2]"));
		webdriverUtil.setValue(By.id("lastName"), lastname);
		//date
		webdriverUtil.click(By.id("day"));
		webdriverUtil.click(By.xpath("//*[@id=\"day\"]/option[3]"));
		webdriverUtil.click(By.id("month"));
		webdriverUtil.click(By.xpath("//*[@id=\"month\"]/option[2]"));
		webdriverUtil.click(By.id("year"));
		webdriverUtil.click(By.xpath("//*[@id=\"year\"]/option[25]"));
		webdriverUtil.setValue(By.id("mobileNumber"), mobile);
		//residential address select
		WebElement address = webdriverUtil.click(By.id("residentialAddress"));
		address.sendKeys("Emirates House, 251-257 Collins Street, MELBOURNE  VIC  3000");
		webdriverUtil.click(By.cssSelector("ul.dropdown-menu.ng-isolate-scope li[id$='option-0']"));
		//address length
		webdriverUtil.click(By.id("adressTime"));
		webdriverUtil.click(By.xpath("//*[@id=\"adressTime\"]/option[11]"));
		webdriverUtil.click(By.xpath("//*[@id=\"residencyFlag1\"]"));
		//skip check box
		webdriverUtil.click(By.xpath("//*[@id=\"IDSection\"]/fieldset/form[4]/div/div/div/label"));
		//employment type
		webdriverUtil.click(By.id("employmentTypeInput"));
		webdriverUtil.click(By.xpath("//*[@id=\"employmentTypeInput\"]/option[6]"));
		//reward program
		webdriverUtil.click(By.id("rewards"));
		webdriverUtil.click(By.xpath("//*[@id=\"rewards\"]/option[3]"));
		webdriverUtil.click(By.id("branchLocatorId"));
		webdriverUtil.click(By.id("branchLocator_blInput")).sendKeys("3000");
		webdriverUtil.click(By.xpath("//*[@id=\"branchLocator_input-group\"]/span/button"));
		webdriverUtil.click(By.id("searchResult-37.816526:144.963763:wbc"));
		eyes.check(Target.window().fully().withName("Credit Limit"));

		//continue button
		webdriverUtil.click(By.id("btnNext"));
		Thread.sleep(3000);
		eyes.check(Target.window().fully().withName("Review and Submit"));
	}

	@Test(description = "Credit card apply", dataProvider = "getTestDataFromExcel",enabled = true)
	public void apply_self_heal(String firstName, String email, String Salary, String Savings, String Assets, String total, String other, String rent, String finance,
								String credLimit, String repayment, String owned, String lastname, String mobile) throws FilloException, InterruptedException {

		Eyes eyes = EyesInstance.instance().getEyesInstance();
		eyes.setIgnoreDisplacements(true);

		webdriverUtil.getUrl("https://www.westpac.com.au/personal-banking/credit-cards/reward/altitude-velocity-platinum/summary/");

		// Execute JavaScript to change the text
		webdriverUtil.executeJS("var element = document.querySelector('#content > div:nth-child(2) > div > div > div.col-xs-12.col-sm-8 > div > div > table > tbody > tr:nth-child(2) > td:nth-child(2) > b');"
				+ "element.textContent = '29.99% p.a. ';");

		// Execute JavaScript to hide the element Consideration
		webdriverUtil.executeJS("var element = document.querySelector('#content > div:nth-child(5) > div > div > div:nth-child(2) > div:nth-child(2) > ul > li:nth-child(2)');"
				+ "element.style.display = 'none';");

		// Execute JavaScript to hide the linkedIN
		webdriverUtil.executeJS("var element = document.querySelector('body > div.wrapper > div.footer-wrapper > div.container-fluid > footer > div:nth-child(3) > div > div.footer-icons > a.icon-linkedin-colour > svg > path:nth-child(2)');"
				+ "element.style.display = 'none';");

		//colour change
		String jsCode = "var element = document.querySelector('#content > div.disclaimer-wrapper.colored-container > div > div > div > div > div > div:nth-child(7) > p > b');" +
				"element.style.color = 'red';";
		webdriverUtil.executeJS(jsCode);

		eyes.check(Target.window().fully().withName("WHAT YOU'RE APPLYING FOR"));

		webdriverUtil.click(By.id("ready-to-apply-checkbox-gel-fieldtext-children"));
		webdriverUtil.click(By.xpath("//*[@id=\"-gel-fieldtext-children\"]/div[1]"));
		//self healing apply button
		webdriverUtil.executeJS("document.querySelector('#apply-now').setAttribute('id', 'apply-now_12345');");
		webdriverUtil.click(By.id("apply-now"));
		webdriverUtil.click(By.xpath("//*[@id=\"container\"]/div[1]/form[1]/section/article[1]/div/div/div/div[1]/div[2]/div/input[1]")); //Are you a westpac customer: NO

//		Thread.sleep(2000);
//		colour change of the Yes Button
//		String color = "var element = document.querySelector('#container > div:nth-child(1) > form.ng-pristine.ng-invalid.ng-invalid-required > section > article:nth-child(2) > div > div > div > div.form-group.clearfix.bottom-margin1-xs.true.true > div:nth-child(2) > div > input.btn.btn-lg.col-xs-6.right-rounded-Btn.ng-pristine.ng-valid');" +
//						"element.style.color = 'green';";
//		webdriverUtil.executeJS(color);

//		Thread.sleep(2000);
		// Execute JavaScript to hide the icon
//		String hide = "var element = document.querySelector('#container > div:nth-child(1) > form.ng-pristine.ng-invalid.ng-invalid-required > section > span:nth-child(4) > article:nth-child(2) > p:nth-child(3) > span:nth-child(3)');"
//				+ "element.style.display = 'none';";
//		webdriverUtil.executeJS(hide);

		eyes.check(Target.window().fully().withName("Are you a westpac customer?"));
		//self healing
		webdriverUtil.executeJS("document.querySelector('#container > div:nth-child(1) > div > div.btn-xl.col-xs-12.col-sm-2.pull-right.content-container-reverse > a:nth-child(2)').setAttribute('id', 'Conitnue_123');");
		webdriverUtil.click(By.xpath("//*[@id=\"container\"]/div[1]/div/div[1]/a[2]"));	//Continue button
		//click on the continue button of the Get started Page
		webdriverUtil.click(By.id("spbtnsubmit1"));

		// Execute JavaScript to change the font style
		webdriverUtil.executeJS("var element = document.querySelector('body > header > div.pull-right > a > div.hidden-xs > p');"
				+ "element.style.fontFamily = 'Aerial';");

		// Execute JavaScript to change the text
		webdriverUtil.executeJS("var element = document.querySelector('#financeForm > div.well.well-responsive.col-xs-12.no-top-margin-xs.bottom-margin7-xs > div:nth-child(7) > div:nth-child(8) > div > p');"
				+ "element.textContent = 'Please select other property owned by you and provide details.';");

		// Execute JavaScript to change the text
		webdriverUtil.executeJS("var element = document.querySelector('#enterpriseFooterId > div:nth-child(2) > div.col-xs-11.logo-text > p:nth-child(3)');"
				+ "element.textContent = 'ABN 33 0o7 457 141';");

		eyes.check(Target.window().fully().withName("Get started(Error)"));
		//Input the details
		//self healing
		webdriverUtil.executeJS("document.querySelector('#firstName').setAttribute('id', 'firstName_123');");
		webdriverUtil.setValue(By.id("firstName"), firstName);
		webdriverUtil.setValue(By.id("emailInput"), email);
		webdriverUtil.setValue(By.id("salaryA"), Salary);
		webdriverUtil.setValue(By.id("savings"), Savings);
		//other assests dropdown
		webdriverUtil.click(By.xpath("//*[@id=\"otherAssets\"]"));
		webdriverUtil.click(By.xpath("//*[@id=\"otherAssets\"]/option[2]"));
		webdriverUtil.setValue(By.id("otherAssetsAmount"), Assets);
		//relationship status
		webdriverUtil.click(By.xpath("//*[@id=\"financeForm\"]/div[1]/div[8]/div[3]/div[1]"));
		webdriverUtil.click(By.xpath("//*[@id=\"relationShipStatus\"]/option[2]"));
		//dependants
		webdriverUtil.click(By.xpath("//*[@id=\"financeForm\"]/div[1]/div[8]/div[4]/div[1]"));
		webdriverUtil.click(By.xpath("//*[@id=\"dependentsNumber\"]/option[2]"));
		//Renting situation
		webdriverUtil.click(By.xpath("//*[@id=\"financeForm\"]/div[1]/div[8]/div[5]/div[1]"));
		webdriverUtil.click(By.xpath("//*[@id=\"housingSituation\"]/option[5]"));
		//Liabilities Section
		webdriverUtil.click(By.id("otherHomeLoansNo"));
		webdriverUtil.click(By.id("otherPersonalLoansNo"));
		webdriverUtil.click(By.id("otherCreditCardsNo"));
		webdriverUtil.click(By.xpath("//div[@id='otherLoans1']/input[@id='otherCreditCardsNo']"));
		webdriverUtil.setValue(By.id("rentOrBoardAmount"), rent);
		webdriverUtil.setValue(By.id("totalLivingExpensesSimpleCalc"), total);
		webdriverUtil.setValue(By.id("totalOtherRegExpesesSimpleCalc"), other);
		webdriverUtil.click(By.id("futureCircumstancesNo"));
		eyes.check(Target.window().fully().withName("Get started"));
		//self healing
//		webdriverUtil.executeJS("document.querySelector('#spbtnsubmit1').setAttribute('id', 'spbtnsubmit1_12345');");
		webdriverUtil.click(By.id("spbtnsubmit1"));

		//colour change of the Yes Button
		String blue = "var element = document.querySelector('body > div.modal.fade.ng-isolate-scope.in > div > div > div > div.modal-footer > div.col-sm-3.col-xs-12.pull-left > button');"
				+ "element.style.color = 'blue';";
		webdriverUtil.executeJS(blue);

		eyes.check(Target.window().fully().withName("Confirm & continue"));
		webdriverUtil.click(By.xpath("//*[@id=\"ng-app\"]/body/div[4]/div/div/div/div[3]/div[1]/button"));
		webdriverUtil.click(By.id("btnNext"));

		// Execute JavaScript to change the text
//		webdriverUtil.executeJS("var element = document.querySelector('#aboutYou > form > fieldset > div:nth-child(5) > div > p:nth-child(2)');"
//				+ "element.textContent = 'Please provide your Middle name.';");

		// Execute JavaScript to hide the icon
		webdriverUtil.executeJS("var element = document.querySelector('#employmentSection > form > fieldset > div.form-group.form-select.true.true.has-error > div > p');"
				+ "element.style.display = 'none';");

		eyes.check(Target.window().fully().withName("Credit Limit(Error)"));

		webdriverUtil.click(By.id("creditLimitType"));
		webdriverUtil.click(By.id("title"));
		webdriverUtil.click(By.id("title"));
		webdriverUtil.click(By.xpath("//*[@id=\"title\"]/option[2]"));
		webdriverUtil.setValue(By.id("lastName"), lastname);
		//date
		webdriverUtil.click(By.id("day"));
		webdriverUtil.click(By.xpath("//*[@id=\"day\"]/option[3]"));
		webdriverUtil.click(By.id("month"));
		webdriverUtil.click(By.xpath("//*[@id=\"month\"]/option[2]"));
		webdriverUtil.click(By.id("year"));
		webdriverUtil.click(By.xpath("//*[@id=\"year\"]/option[25]"));
		//self healing
		webdriverUtil.executeJS("document.querySelector('#mobileNumber').setAttribute('id', 'mobileNumber_123');");
		webdriverUtil.setValue(By.id("mobileNumber"), mobile);
		//residential address select
		WebElement address = webdriverUtil.click(By.id("residentialAddress"));
		address.sendKeys("Emirates House, 251-257 Collins Street, MELBOURNE  VIC  3000");
		webdriverUtil.click(By.cssSelector("ul.dropdown-menu.ng-isolate-scope li[id$='option-0']"));
		//address length
		webdriverUtil.click(By.id("adressTime"));
		webdriverUtil.click(By.xpath("//*[@id=\"adressTime\"]/option[11]"));
		webdriverUtil.click(By.xpath("//*[@id=\"residencyFlag1\"]"));
		//skip check box
		webdriverUtil.click(By.xpath("//*[@id=\"IDSection\"]/fieldset/form[4]/div/div/div/label"));
		//employment type
		webdriverUtil.click(By.id("employmentTypeInput"));
		webdriverUtil.click(By.xpath("//*[@id=\"employmentTypeInput\"]/option[6]"));
		//reward program
		webdriverUtil.click(By.id("rewards"));
		webdriverUtil.click(By.xpath("//*[@id=\"rewards\"]/option[3]"));
		webdriverUtil.click(By.id("branchLocatorId"));
		webdriverUtil.click(By.id("branchLocator_blInput")).sendKeys("3000");
		webdriverUtil.click(By.xpath("//*[@id=\"branchLocator_input-group\"]/span/button"));
		webdriverUtil.click(By.id("searchResult-37.816526:144.963763:wbc"));
		Thread.sleep(2000);
		eyes.check(Target.window().fully().withName("Credit Limit"));
		//continue button
		webdriverUtil.click(By.id("btnNext"));

		Thread.sleep(2000);
		// Execute JavaScript to change the text
		webdriverUtil.executeJS("var element = document.querySelector('#container > div > div > div.well.well-responsive.col-xs-12.no-bottom-margin-xs.bottom-margin5-xs.ng-scope > div > div.col-xs-12 > section > form > fieldset > div:nth-child(5) > legend:nth-child(7) > h5');element.textContent = 'Housing and relationship';");

		Thread.sleep(2000);
		// Execute JavaScript to change the text
		webdriverUtil.executeJS("var element = document.querySelector('#container > div > div > div.well.well-responsive.col-xs-12.no-bottom-margin-xs.bottom-margin5-xs.ng-scope > div > div.col-xs-12 > section > form > fieldset > div:nth-child(5) > div:nth-child(15) > label');element.textContent = 'Other Issues';");

		eyes.check(Target.window().fully().withName("Review and Submit"));
	}

}
