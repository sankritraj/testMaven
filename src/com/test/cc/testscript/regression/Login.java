package com.test.cc.testscript.regression;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.test.cc.utility.BaseClass;


public class Login extends BaseClass{

	private static Logger Log = Logger.getLogger(Login.class.getName());

	/** Test case to verify login page **/
	@Test(description="TC_1")
	public static void verifyLoginPage() throws InterruptedException
	{	
		Log.info("Pass");
	
		
	}
	
	/** Test case to verify login page **/
	@Test(description="TC_2")
	public static void verifyLoginPage1() throws InterruptedException
	{	
		Log.info("Inside verifyLoginPage1");
		Assert.fail();
	
		
	}
}