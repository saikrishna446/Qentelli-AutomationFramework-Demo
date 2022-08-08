package com.qentelli.automation.runners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class GenerateTest {

	static final String DB_URL = "jdbc:mysql://172.16.12.35:3306/Mobe_Dev_Local?characterEncoding=utf8";
	static final String USER = "mobeuser";
	static final String PASS = "M0B@KenT1i!2O@2";


	public void runTestNGTest(ResultSet rs, String className) {
		//This Map can hold your testng Parameters.

		//Create an instance on TestNG
		TestNG myTestNG = new TestNG();

		//Create an instance of XML Suite and assign a name for it.
		XmlSuite mySuite = new XmlSuite();
		mySuite.setFileName("Test");
		mySuite.setName("MySuite");
		mySuite.setThreadCount(5);
		mySuite.setParallel(XmlSuite.ParallelMode.TESTS);
		mySuite.setDataProviderThreadCount(10);
		mySuite.addListener("com.qentelli.automation.listeners.ExecutionListener");
		List<XmlTest> myTests = new ArrayList<XmlTest>();

		try {
			while (rs.next()) {
				String  ao;
				ao = rs.getString("BrowserInfo");

				// Parser returns an object, we need an explicit cast to covert it into a JSONArray
				JSONArray array = new JSONArray(ao);

				for (int i = 0; i < array.length(); i++)
				{
					//Create an instance of XmlTest and assign a name for it.
					XmlTest myTest = new XmlTest(mySuite);

					myTest.setName("MyTest"+i);
					myTest.setGroupByInstances(true);
					Map<String,String> testngParams = new HashMap<String,String> ();

					//Testing parameters
					testngParams.put("driverType", "local");
					testngParams.put("debugMode", "false");
					testngParams.put("locale", "en_US");
					testngParams.put("overrideLocale", "false");
					testngParams.put("tunnel", "no");
					testngParams.put("tunnel_id", "sc-proxy-tunnel");
					testngParams.put("environment", "UAT");
					testngParams.put("testId", "111");
					testngParams.put("user", "Mobe_Dev_Local");
					testngParams.put("database", "STABILITY");
					testngParams.put("browserVersion", "latest");
					testngParams.put("browserPlatform", "Windows");
					testngParams.put("osversion", "11");
					testngParams.put("isMobile", "false");
					testngParams.put("mobileDeviceName", "Samsung Galaxy S20");
					testngParams.put("mobileDeviceOrientation", "portrait");
					testngParams.put("mobilePlatformVersion", "10.0");
					testngParams.put("browser", "chrome");

					JSONObject jsonObj = array.getJSONObject(i);
					@SuppressWarnings("static-access")
					String names[] = jsonObj.getNames(jsonObj);
					for(int j = 0 ; j <names.length ; j++) {
						testngParams.put(names[i],jsonObj.getString(names[i]).equals(null)?"null":jsonObj.getString(names[i]));
					}
					//Add any parameters that you want to set to the Test.
					myTest.setParameters(testngParams);
					//Create a list which can contain the classes that you want to run.
					List<XmlClass> myClasses = new ArrayList<XmlClass> ();
					//  myClasses.add(new XmlClass("com.testautomation.grid.SampleClass"));
					myClasses.add(new XmlClass(className));

					//Assign that to the XmlTest Object created earlier.
					myTest.setXmlClasses(myClasses);

					//Create a list of XmlTests and add the Xmltest you created earlier to it.
					myTests.add(myTest);

				}


			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		mySuite.setTests(myTests);
		System.out.println(mySuite.toXml());

		//Add the suite to the list of suites.
		List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
		mySuites.add(mySuite);
//

		//Set the list of Suites to the testNG object you created earlier.
		myTestNG.setXmlSuites(mySuites);


		//invoke run() - this will run your class.
		myTestNG.run();
	}

	public static void main (String args[])
	{
		//final String QUERY = "SELECT *from tb_TestSuite_Execution_Results where TestExecution_Result_Id="+args[0];
		final String QUERY = "SELECT *from tb_TestSuite_Execution_Results where TestExecution_Result_Id=17";
		GenerateTest dt = new GenerateTest();

		// Open a connection
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY);) {
			dt.runTestNGTest(rs,"com.qentelli.automation.runners.DefaultRunner3");
			// Extract data from result set
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
