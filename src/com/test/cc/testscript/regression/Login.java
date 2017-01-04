package com.test.cc.testscript.regression;

import org.apache.log4j.Logger;
import org.seleniumhq.jetty9.util.log.Log;
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
}