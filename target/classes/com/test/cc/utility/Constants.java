package com.test.cc.utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * The Constants class has values for test-framework
 * @version 1.0
 * @date 07-DEC-2016
 * @author niraj.kumar
 */

public class Constants {
	private static Logger Log = Logger.getLogger(Constants.class.getName());

	public static String       basePath = new File("").getAbsolutePath(); 
	public static String       fileSeparator = System.getProperty("file.separator");
	public static final String Path_Config = basePath + Constants.fileSeparator
			+ "testConfig" + Constants.fileSeparator + "config.properties";	

	public static int imlicitWaitTime= 15;
	public static String url= getValueFromConfigFile("testURL");	   
	public static final String TESTNG_ATTACHMENT_FILE = "emailable-report.html";

	public static String testResultFolderName = "";

	public static String TEST_OUTPUT_FOLDER = "test-output";

	public static String ProjectPath = basePath + Constants.fileSeparator;
	
	public static String       browser = null;

	public static final String PATH_RECEPIENTS_CSV = Constants.fileSeparator+"testConfig"+Constants.fileSeparator+"mail-recipients.csv";

	/**Path of Chrome Driver**/
	public static String chromePath = basePath + Constants.fileSeparator+ "driver" + Constants.fileSeparator+ "chromedriver";

	/**Path of FireFox Driver **/

	public static String fireFoxPath =basePath + Constants.fileSeparator+ "driver" + Constants.fileSeparator+ "geckodriver.exe";

	/**Path of IE Driver **/
	public static String InternetExplorerEPath =basePath + Constants.fileSeparator+ "driver" + Constants.fileSeparator+ "IEDriverServer.exe";

	public static String getValueFromConfigFile(String strProp) {
		File file = new File(Path_Config);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			Log.error(e.getMessage());
			System.exit(-1);
		}
		Properties prop = new Properties();
		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			Log.error(e.getMessage());
			System.exit(-1);
		}
		return prop.getProperty(strProp).toString();
	}
	
}
