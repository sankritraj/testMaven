package com.test.cc.utility;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

public class Utils extends Logging{
	 public static FluentWait<WebDriver> wait;
	
	private static Logger Log = Logger.getLogger(Utils.class.getName());
	
	public static void waitToProcess(int Seconds)
	{
		try {
			Log.info("Waiting for element to be visible");
			Thread.sleep(Seconds);
			Log.info(Seconds+" wait is applied to diaply all elements properly");
		} catch (InterruptedException e) {
			
			Log.info("Exception occured "+e.getLocalizedMessage());
			
		}
	}
	
	 public static boolean waitForElementToBeVisible(WebDriver driver, WebElement element) {

	        boolean webElement = false;

	        try {	            
	            wait = new WebDriverWait(driver, 60)
	                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
	                    .pollingEvery(12, TimeUnit.SECONDS);
	            wait.until(ExpectedConditions.visibilityOf(element));
	            Log.info("Element is visible");
	            webElement = true;
	        } catch (Exception e) {
	            Log.error("Element is not visible | " + new Object() {
	            }.getClass().toString() + " | method " + new Object() {
	            }.getClass().getEnclosingMethod().getName());
	            Log.error(e.getMessage());
	            webElement = false;
	        } 
	        return webElement;
	    }
	 
	 public static boolean waitForElementToBeClickable(WebDriver driver, WebElement element) {

	        boolean webElement = false;

	        try {
	            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	            wait = new WebDriverWait(driver, 60)
	                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
	                    .pollingEvery(12, TimeUnit.SECONDS);
	            wait.until(ExpectedConditions.elementToBeClickable(element));
	            Log.info("Element is clickable");
	            webElement = true;
	        } catch (Exception e) {
	            Log.error("Element is not clickable | " + new Object() {
	            }.getClass().toString() + " | method " + new Object() {
	            }.getClass().getEnclosingMethod().getName());
	            Log.error(e.getMessage());
	            webElement = false;
	        } finally {
	            driver.manage().timeouts().implicitlyWait(Constants.imlicitWaitTime, TimeUnit.SECONDS);
	        }
	        return webElement;
	    }
	 
	
	 
	 public static void waitForTextToNotBePresent(WebDriver driver, By locator, String text) {
	        try {
	            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	            wait = new WebDriverWait(driver, 60)
	                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
	                    .pollingEvery(12, TimeUnit.SECONDS);
	            wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
	            Log.info(text + " is not present");
	        } catch (Exception e) {
	            Log.error(text + " is present | " + new Object() {
	            }.getClass().toString() + " | method " + new Object() {
	            }.getClass().getEnclosingMethod().getName());
	            Log.error(e.getMessage());
	            Assert.fail(text + " present");
	        } finally {
	            driver.manage().timeouts().implicitlyWait(Constants.imlicitWaitTime, TimeUnit.SECONDS);
	        }
	    }
	 
	  public static boolean waitForElementToBeNotVisible(WebDriver driver, By locator) {

	        boolean webElement = false;

	        try {
	            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	            wait = new WebDriverWait(driver, 60)
	                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
	                    .pollingEvery(12, TimeUnit.SECONDS);
	            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	            Log.info("Element is not visible");
	            webElement = true;
	        } catch (Exception e) {
	            Log.error("Element is visible | " + new Object() {
	            }.getClass().toString() + " | method " + new Object() {
	            }.getClass().getEnclosingMethod().getName());
	            Log.error(e.getMessage());
	            webElement = false;
	        } finally {
	            driver.manage().timeouts().implicitlyWait(Constants.imlicitWaitTime, TimeUnit.SECONDS);
	        }
	        return webElement;
	    }
	 
	   
	   public static List<String> getValueFromCsvFile(String Path_Config)
	    {
	        final File csvFile = new File(Path_Config);
	        List<String> tempList=null;

	        try
	        {
	            tempList = FileUtils.readLines(csvFile);
	        }
	        catch (NumberFormatException nfe)
	        {
	              Log.info("Error during parsing of csv file.\n Check File:" + csvFile.getAbsolutePath() + "Exception:" + nfe);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        	Log.info("Error during reading of file.\n Check File:" + csvFile.getAbsolutePath() + "Exception:" + e);
	            e.printStackTrace();
	        }

	        return tempList;

	    }
	   
	   /** This method is used to capture screenshot for each failure**/
	    public static void takeScreenshot(WebDriver driver,ITestResult result) //Where ITestResult is an Interface.
	    {
	        String methodName=result.getName(); // Capture the TestName
	        DateFormat dateFormat = new SimpleDateFormat("MMMMM d yyyy, K.mm a");
	        Date date = new Date();

	        /**capture the date**/
	        String current_Time = dateFormat.format(date);

	        /**Capture the class**/
	        String className= result.getInstanceName().toString();

	        /**Split the class and store in array**/
	        String[] classNameString= className.split("\\.");

	        if(result.getStatus()==ITestResult.FAILURE) //Check failure conditions
	        {
	            File scr= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

	            try {

	                /** Store file in Class name folder with method and date **/
	                FileUtils.copyFile(scr, new File(Constants.basePath+Constants.fileSeparator+"Screenshots"+Constants.fileSeparator+classNameString[classNameString.length-1]+Constants.fileSeparator+methodName+current_Time+".png"));
	                Log.info("Screenshots is captured and stored "+Constants.basePath+Constants.fileSeparator+"Screenshots"+Constants.fileSeparator+classNameString[classNameString.length-1]+ "folder");
	            } catch (IOException e) {
	                System.out.println("Exception message");
	            }
	        }

	    }

}
