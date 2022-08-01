package com.qentelli.automation.runners;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateTest {
	
		public void runTestNGTest(Map<String,String> testngParams, String className) {
			
			

		    //Create an instance on TestNG
		     TestNG myTestNG = new TestNG();

		    //Create an instance of XML Suite and assign a name for it.
		     XmlSuite mySuite = new XmlSuite();
		     mySuite.setName("MySuite");
		     mySuite.setParallel("Test");
		     mySuite.setThreadCount(5);
		     mySuite.setDataProviderThreadCount(10);
		     mySuite.addListener("com.qentelli.automation.listeners.ExecutionListener");

		    //Create an instance of XmlTest and assign a name for it.
		     XmlTest myTest = new XmlTest(mySuite);
		     myTest.setName("MyTest chrome");
		     myTest.setGroupByInstances(true);
		     
		     
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
             System.out.println(testngParams);
		    //Add any parameters that you want to set to the Test.
		     myTest.setParameters(testngParams);
//
		    //Create a list which can contain the classes that you want to run.
		     List<XmlClass> myClasses = new ArrayList<XmlClass> ();
		   //  myClasses.add(new XmlClass("com.testautomation.grid.SampleClass"));
		     myClasses.add(new XmlClass(className));

//		    Assign that to the XmlTest Object created earlier.
		     myTest.setXmlClasses(myClasses);

		    //Create a list of XmlTests and add the Xmltest you created earlier to it.
		     List<XmlTest> myTests = new ArrayList<XmlTest>();
		     myTests.add(myTest);

		    //add the list of tests to your Suite.
		     mySuite.setTests(myTests);

		    //Add the suite to the list of suites.
		     List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
		     mySuites.add(mySuite);
		     System.out.println(mySuite);


		    //Set the list of Suites to the testNG object you created earlier.
		     myTestNG.setXmlSuites(mySuites);

		    TestListenerAdapter tla = new TestListenerAdapter();
		    
		    myTestNG.addListener(tla);

		    //invoke run() - this will run your class.
		     myTestNG.run();
		    }

		public static void main (String args[])
		{
			GenerateTest dt = new GenerateTest();

		    //This Map can hold your testng Parameters.
		     Map<String,String> testngParams = new HashMap<String,String> ();
		     testngParams.put("browser", "chrome");
		     dt.runTestNGTest(testngParams,"com.qentelli.automation.runners.DefaultRunner3");
		}
}
