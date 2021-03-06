package com.test.cc.utility;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import net.anthavio.phanbedder.Phanbedder;

public class BaseClass {
	private static Logger Log = Logger.getLogger(BaseClass.class.getName());

	private static String userName;
	private static String Password;

	/*public static void initializeLog4j() {
		DOMConfigurator.configure("log4j.xml");
		Log.info("Initializing log4j");
	}*/

	public static WebDriver driver;
	public static boolean isRunningAfterMethod = true;

	@Parameters({"browser"})
	@BeforeClass(alwaysRun=true)		
	public static void setUp(String browser)
	{
		String OS = System.getProperty("os.name");
		System.out.println("OS name is "+OS);
		switch(browser.toLowerCase())
		{	
		case "firefox":
			DesiredCapabilities desiredCapabilities= DesiredCapabilities.firefox();
			desiredCapabilities.setBrowserName("firefox");
			desiredCapabilities.setCapability("marionette", true);
			desiredCapabilities.setPlatform(Platform.WINDOWS);
			System.setProperty("webdriver.gecko.driver", Constants.fireFoxPath);
			driver= new FirefoxDriver(desiredCapabilities);	
			break;

		case "chrome":
			System.setProperty("webdriver.chrome.driver",Constants.chromePath);
			System.out.println(Constants.chromePath);
			driver = new ChromeDriver();
			Log.info("Chrome browser is launched");
			break;

		case "ie":
			System.setProperty("webdriver.ie.driver",Constants.InternetExplorerEPath);
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("ignoreZoomSetting", true);
			caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);  
			caps.setCapability("unexpectedAlertBehaviour", "accept");
			caps.setCapability("ignoreProtectedModeSettings", true);
			caps.setCapability("disable-popup-blocking", true);
			caps.setCapability("enablePersistentHover", true);
			driver = new InternetExplorerDriver(caps);
			break;		

		case "phantom":
			if (OS.startsWith("Windows")) {
				DesiredCapabilities DesireCaps = new DesiredCapabilities();
				DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, Constants.phantomjsPath);
				driver=new PhantomJSDriver(DesireCaps);
			}else if (OS.startsWith("Linux")) {
				File phantomjs = Phanbedder.unpack();
				DesiredCapabilities DesireCaps = new DesiredCapabilities();
				DesireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
				driver=new PhantomJSDriver(DesireCaps);
			}
			break;
		}

		driver.manage().window().maximize();
		Log.info("Window is maximized");
		driver.manage().timeouts().implicitlyWait(Constants.imlicitWaitTime, TimeUnit.SECONDS);
		Log.info("Implicit wait is applied for "+Constants.imlicitWaitTime+" seconds");
		driver.get(Constants.url);
		Log.info("Url '"+Constants.url+ "' is sucesffuly inputted in browser");		
	}

	@BeforeMethod(alwaysRun = true)
	public void setFlagforAfterMethod() throws InterruptedException {

		isRunningAfterMethod = true;
	}

	@AfterMethod
	public void AfterMethod(ITestResult result)
	{
		if(isRunningAfterMethod)
		{

		}
		Logging.endTestCase(result.getMethod().getDescription());

	}

	@AfterClass(alwaysRun=true)




	@AfterSuite	
	public static void sendEmail() throws Exception
	{
		driver.quit();
	}

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		BaseClass.userName = userName;
	}

	public static String getPassword() {
		return Password;
	}

	public static void setPassword(String password) {
		Password = password;
	}

}
