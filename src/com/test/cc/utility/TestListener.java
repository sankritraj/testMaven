package com.test.cc.utility;

import java.util.List;

import org.apache.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.collections.Lists;
import org.testng.reporters.EmailableReporter;
import org.testng.xml.XmlSuite;

public class TestListener  extends EmailableReporter implements IReporter, ITestListener, ISuiteListener{
	private static Logger Log = Logger.getLogger(TestListener.class.getName());

	@Override
	public void onStart(ISuite suite) {
						
		if (suite.getParameter("browser") != null){
			Constants.browser = suite.getParameter("browser").toUpperCase(); 
			Log.info("Browser: " + Constants.browser);
		}

	}
	@Override
	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {						
		/*String recepient=null;														
		Utils.waitToProcess(40000);
		List<String> recepientList = Utils.getValueFromCsvFile(Constants.basePath+Constants.PATH_RECEPIENTS_CSV);
		for (int i = 0; i < recepientList.size(); i++)
		{
			recepient = recepientList.get(i);
			Log.info("Sending mail to:- "+recepient);
			try {
				Email.sendMailNow(recepient,"smtp.gmail.com","XXXXXX@gmail.comm","XXXXXX","465");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.info("Mail will be sent to:-"+recepient);

		}*/
	}
	@Override
	public void onTestStart(ITestResult result) {
		Logging.startTestCase(result.getMethod().getMethodName());
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		String className= result.getInstanceName().toString();
		/**Split the class and store in array**/
		String[] classNameString= className.split("\\.");
		String platform= System.getProperty("os.name");
		Log.info(" PASS " + result.getMethod().getDescription() + " " + result.getMethod().getMethodName() + " of " +  classNameString[classNameString.length-1] + " module on " + platform + " on " + Constants.browser+" browser");
	}
	@Override
	public void onTestFailure(ITestResult result) {

		String className= result.getInstanceName().toString();
		/**Split the class and store in array**/
		String[] classNameString= className.split("\\.");
		String platform= System.getProperty("os.name");
		Log.info(" FAIL " + result.getMethod().getDescription() + " " + result.getMethod().getMethodName() + " of " +  classNameString[classNameString.length-1] + " module on " + platform + " on " + Constants.browser+" browser");
		
		/**Capture screenshots for failure**/
		Utils.takeScreenshot(BaseClass.driver, result);


	}
	@Override
	public void onTestSkipped(ITestResult result) {
		String className= result.getInstanceName().toString();
		/**Split the class and store in array**/
		String[] classNameString= className.split("\\.");
		String platform= System.getProperty("os.name");
		Log.info(" SKIPPED " + result.getMethod().getDescription() + " " + result.getMethod().getMethodName() + " of " +  classNameString[classNameString.length-1] + " module on " + platform + " on " + Constants.browser+" browser");
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onStart(ITestContext context) {
	
	}
	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
