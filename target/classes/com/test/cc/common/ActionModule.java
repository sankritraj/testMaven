package com.test.cc.common;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import com.test.cc.utility.Utils;
public class ActionModule {

	private static Logger Log = Logger.getLogger(ActionModule.class.getName());

	public static void click (WebDriver driver, WebElement webElement, String webElementName, String activityName){	        
		try{
			if (!Utils.waitForElementToBeClickable(driver, webElement)){
				Log.error(webElement + " is not clickable");
				Reporter.log(webElement + " is not clickable");
				Assert.fail(webElement + " is not clickable");
			}	
			webElement.click();		
			Log.info(webElementName + " is successfully clicked on " + activityName + " activity");
		} 	       
		catch (Exception e){
			Log.error(e.getLocalizedMessage());
			Reporter.log(e.getLocalizedMessage());
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public static void isDisplayed (WebDriver driver, WebElement webElement, String webElementName, String activityName){

		if (webElement != null){
			if (! webElement.isDisplayed()){
				Log.error(webElementName +  " is not displayed on the " + activityName + " activity");
				Reporter.log(webElementName +  " is not displayed on the " + activityName + " activity");
				Assert.fail(webElementName +  " is not displayed on the " + activityName + " activity");
			}
			else
				Log.info(webElementName +  " is displayed on the " + activityName + " activity");
		}
		else{
			Log.error(webElementName +  " is not present on the " + activityName + " activity");
			Reporter.log(webElementName +  " is not present on the " + activityName + " activity");
			Assert.fail(webElementName +  " is not present on the " + activityName + " activity");
		}
	}

	public static void isNotNull (WebDriver driver, WebElement webElement, String webElementName, String activityName){

		if (webElement != null){
			Log.info(webElementName +  " is not null on the " + activityName + " activity");            
		}
		else{
			Log.error(webElementName +  " is null on the " + activityName + " activity");
			Reporter.log(webElementName +  " is null on the " + activityName + " activity");
			Assert.fail(webElementName +  " is null on the " + activityName + " activity");
		}
	}

	public static void clear(WebDriver driver, WebElement webElement, String webElementName, String activityName) {

		ActionModule.isDisplayed(driver, webElement, webElementName, activityName);

		try {
			if (! Utils.waitForElementToBeClickable(driver, webElement)) {
				Log.error(webElementName + " is not clickable");
				Reporter.log(webElementName + " is not clickable");
				Assert.fail(webElementName + " is not clickable");
			}
			webElement.clear();
			Log.info(webElementName + " is cleared");
		}  catch (Exception e) {
			Log.error(webElementName + " is not cleared. " + e.getLocalizedMessage());
			Reporter.log(webElementName + " is not cleared");
			Assert.fail(webElementName + " is not cleared");
		}
	}

	public static void clickByJavascript (WebDriver driver, WebElement webElement, String webElementName, String activityName){

		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", webElement);     
			Log.info(webElementName + " on " + activityName + " is clicked");
		} catch (Exception e){
			Log.error(e.getLocalizedMessage());
			Reporter.log(webElementName + " on " + activityName + " cannot be clicked");
			Assert.fail(e.getLocalizedMessage());
		}
	}

	public static void sendKeys(WebDriver driver, WebElement webElement, String value, String webElementName, String activityName){

		ActionModule.isDisplayed(driver, webElement, webElementName, activityName);

		try{
			webElement.clear();	            
			webElement.sendKeys(value);	          
			Log.info(value + " is entered in " + webElementName);

		} catch (Exception e){
			Log.error(webElement + " is not visible on the " +activityName + " activity. " + e.getLocalizedMessage());
			Reporter.log(webElement + " is not visible on the " +activityName + " activity");
			Assert.fail(webElement + " is not visible on the " +activityName + " activity");
		}
	}

	public static void scrollToViewElement(WebDriver driver,WebElement element)
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	




}


