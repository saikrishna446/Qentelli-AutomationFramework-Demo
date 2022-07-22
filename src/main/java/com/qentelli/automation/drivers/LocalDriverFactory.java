package com.qentelli.automation.drivers;

import com.qentelli.automation.common.World;
import com.qentelli.automation.utilities.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * Based on the LocalDriverFactory found at: onrationaleemotions.wordpress.com
 * @author: Confusions Personified
 * @src: http://rationaleemotions.wordpress.com/2013/07/31/parallel-webdriver-executions-using-testng/
 */

public class LocalDriverFactory {
    static Logger logger = LogManager.getLogger(LocalDriverFactory.class);
    private World world;
    private ResourceBundle configLib;
    private WebDriver driver;
    int profileCnt = 0;

    public LocalDriverFactory(World world) {
        this.world = world;
    }


    public static final String USERNAME = "bhargav_DwI1FG";
	  public static final String AUTOMATE_KEY = "fSzkg1rxsCq2WeHu722d";
	  public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.BrowserStack.com/wd/hub";
	  public  WebDriver createInstance(String browserName) throws MalformedURLException {
          browserName="IPHONE";
        WebDriver driver;
        DesiredCapabilities caps = new DesiredCapabilities();
        browserName = (browserName != null) ? browserName : "firefox";

        switch (browserName) {
            case "FIREFOX":
            	caps.setCapability("browser", "Firefox");
            	caps.setCapability("browser_version", "101");
            	caps.setCapability("os", "Windows");
            	caps.setCapability("os_version", "11");
            	caps.setCapability("resolution", "1024x768");
            	driver = new RemoteWebDriver(new URL(URL), caps);
                driver.get("https://google.com");
                driver.close();
//            	System.setProperty("webdriver.gecko.driver","D:\\Softwares\\geckodriver.exe");
//
//                driver = new  FirefoxDriver();
                break;
            case "IPHONE":
            	caps.setCapability("browserName", "iPhone");
            	caps.setCapability("platform", "MAC");
            	caps.setCapability("device", "iPhone 11");
            	driver = new RemoteWebDriver(new URL(URL), caps);
            	break;

            case "IE":
                driver = new InternetExplorerDriver();
                break;
            case "CHROME":
                System.setProperty("webdriver.chrome.driver", "E:\\CucumberProjects\\cucumber-testng-parallel-selenium-master\\Drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
//            case HTMLUNIT:
//                driver = new HtmlUnitDriver();
//                break;
//            case HTMLUNITJS:
//                driver = new HtmlUnitDriver(true);
//                break;
            default:
                driver = new FirefoxDriver();
                break;
        }
        // maximize browser's window on start
        driver.manage().window().maximize();
        return driver;
    }

    public  WebDriver getDriver() throws MalformedURLException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        MutableCapabilities browserOptions;
        DesiredCapabilities caps = new DesiredCapabilities();
        if (world.isMobile()) {
            browserOptions = new DesiredCapabilities();
            browserOptions.setCapability("platform", world.getMobilePlatformName());
            browserOptions.setCapability("browserName", world.getMobileBrowser());
            browserOptions.setCapability("os_version", world.getMobilePlatformVersion());
            browserOptions.setCapability("device", world.getMobileDeviceName());
            browserOptions.setCapability("real_mobile", "true");

           // browserOptions.setCapability("appium:platformVersion", world.getMobilePlatformVersion());
            // browserOptions.setCapability("appium:app",""); // for an app test, add app
            // location, (remote or sauce storage)
            browserOptions.setCapability("appium:deviceOrientation", world.getMobileDeviceOrientation());
            browserOptions.setCapability("unicodeKeyboard", true);
            browserOptions.setCapability("resetKeyboard", true);


                    //          caps.setCapability("browserName", "iPhone");
//            caps.setCapability("platform", "MAC");
//            caps.setCapability("device", "iPhone 11");
//            driver = new RemoteWebDriver(new URL(URL), browserOptions);
        } else {

            switch (world.getBrowser()) {
                case FIREFOX:
                    browserOptions = new FirefoxOptions();
                    FirefoxProfile firefoxProfile = new FirefoxProfile();
                    firefoxProfile.setPreference("browser.download.dir", "C:\\Users\\Administrator\\Downloads");
                    firefoxProfile.setPreference("browser.download.folderList", 2);
                    firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                            "text/plain, text/csv, application/octet-stream,image/jpeg, video/mp4,video/quicktime, application/binary");
                    firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
                    firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
                            "text/plain, text/csv, application/octet-stream,image/jpeg, application/binary");
                    firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
                    firefoxProfile.setPreference("browser.download.manager.useWindow", false);
                    firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
                    firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                    firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
                    firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);

                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setCapability("unhandledPromptBehavior", "accept");
                    firefoxOptions.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
                    browserOptions.merge(firefoxOptions);
                    break;
                case CHROME:
                    browserOptions = new ChromeOptions();
                    LoggingPreferences logPrefs = new LoggingPreferences();
                    logPrefs.enable(LogType.BROWSER, Level.ALL);
                    browserOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                    ((ChromeOptions) browserOptions).setAcceptInsecureCerts(true);
                    ((ChromeOptions) browserOptions).setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);

                    String chromeProfile = System.getenv("TEMP");
                    chromePrefs.put("--user-data-dir", chromeProfile);
                    chromePrefs.put("--profile-directory",
                            System.getenv("HOSTNAME") + "_" + Integer.valueOf(++profileCnt));
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setExperimentalOption("prefs", chromePrefs);
                    browserOptions.merge(chromeOptions);
                    break;
                case SAFARI:
                    browserOptions = new SafariOptions();
                    break;
                case IEXPLORER:
                    browserOptions = new InternetExplorerOptions();
                    break;
                case EDGE:
                    browserOptions = new EdgeOptions();
                    break;
                default:
                    System.out.println("chrome2");
                    browserOptions = new ChromeOptions();
                    ((ChromeOptions) browserOptions).setAcceptInsecureCerts(true);
                    ((ChromeOptions) browserOptions).setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
            }
           // browserOptions.setCapability("platformName", world.getBrowserPlatform());
           // browserOptions.setCapability("platformName", "WINDOWS");
            browserOptions.setCapability("os",  world.getBrowserPlatform());
            browserOptions.setCapability("os_version", world.getOsVersion());
            browserOptions.setCapability("browser", world.getBrowser());
//            browserOptions.setCapability("browserVersion", world.getBrowserVersion());
            browserOptions.setCapability("browser_version", world.getBrowserVersion());



        }
        browserOptions.setCapability("unhandledPromptBehavior", "accept");
        browserOptions.setCapability("name", world.getScenarioName());
        ;
        /// ********************************************************
        // this sc proxy tunnel is needed for yopmail and jenkins
        // removing this will brake yopmail tests in ci/cd
        /// ********************************************************
        sauceOptions.setCapability("tunnelIdentifier", "sc-proxy-tunnel"); // also, if something is wrong here. You
        // need to run a saucelabs connect
        // tunnel
        // here's an example: bin/sc --pidfile /tmp/sc.pid1 -u "SAUCE_USER" -k
        // "SAUCEKEY" -i sc-proxy-tunnel
        // --no-remove-colliding-tunnels
        /// ********************************************************
        //browserOptions.setCapability("sauce:options", sauceOptions);
        System.out.print("Loading browser ....");

    this.driver = new RemoteWebDriver(new URL(URL), browserOptions);
   // this.driver = new RemoteWebDriver(new URL(URL), caps);

        String sessionId = ((RemoteWebDriver) this.driver).getSessionId().toString();
       // String tempWebLink = sauceRest.getPublicJobLink(sessionId);
        world.setSessionId(sessionId);
      //  world.setSauceWebLink(tempWebLink);
        System.out.print(" done! @ ");
        return driver;
    }



}