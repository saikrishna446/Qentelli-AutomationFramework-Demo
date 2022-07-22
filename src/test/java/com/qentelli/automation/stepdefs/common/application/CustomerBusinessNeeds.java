package com.qentelli.automation.stepdefs.common.application;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.qentelli.automation.common.World;
import com.qentelli.automation.pages.BasePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CustomerBusinessNeeds extends BasePage {

	private World world;
	Logger logger;
	ResourceBundle qentelliPageElements;
	By username;
	By password;
	By submit;
	By programms;
	By landingPage;
	By programmstitle;
	By Platformprocess;
	By Platformtitle;
	By Predeliveryprocess;
	By Programexpand;
	By Customerneeds;
	By programmsbar;
	By Platformbar;

	public void InitElements() {
		qentelliPageElements = ResourceBundle.getBundle("com.qentelli.automation.Airlines.CueQ", Locale.US);
		logger = LogManager.getLogger(CustomerBusinessNeeds.class);
		username = By.xpath(qentelliPageElements.getString("CueQ.username"));
		password = By.xpath(qentelliPageElements.getString("CueQ.password"));
		submit = By.xpath(qentelliPageElements.getString("CueQ.submit"));
		programms = By.xpath(qentelliPageElements.getString("CueQ.programms"));
		landingPage = By.xpath(qentelliPageElements.getString("CueQ.landingPage"));
		programmstitle = By.xpath(qentelliPageElements.getString("CueQ.programmstitle"));
		Platformprocess = By.xpath(qentelliPageElements.getString("CueQ.Platformprocess"));
		Platformtitle = By.xpath(qentelliPageElements.getString("CueQ.Platformtitle"));
		Predeliveryprocess = By.xpath(qentelliPageElements.getString("CueQ.Predeliveryprocess"));
		Programexpand = By.xpath(qentelliPageElements.getString("CueQ.Programexpand"));
		Customerneeds = By.xpath(qentelliPageElements.getString("CueQ.Customerneeds"));
		programmsbar = By.xpath(qentelliPageElements.getString("CueQ.programmsbar"));
		Platformbar = By.xpath(qentelliPageElements.getString("CueQ.Platformbar"));

	}

	public CustomerBusinessNeeds(World world) {
		super(world, world.driver);
		this.world = world;
		InitElements();
	}

	@Given("user navigate to the CueQ Page")
	public void user_navigate_to_the_CueQ_Page() {
		// world.driver.get("https://qentelliorg.sharepoint.com/sites/CueQ/");
		//navigateToUrl("https://qentelliorg.sharepoint.com/sites/CueQ/");
		navigateToUrl("https://qentelliorg.sharepoint.com/sites/CueQ/");
		// world.driver.manage().window().maximize();
//		windowmaxmize();
	}

	@Given("user navigate to Landing Page under the PROGRAM TYPES Menu")
	public void user_navigate_to_Landing_Page_under_the_PROGRAM_TYPES_Menu() throws InterruptedException {
			Thread.sleep(5000);
			enterText(username, "sharepointdev@qentelliorg.onmicrosoft.com");
			click(submit);
			Thread.sleep(5000);
			enterText(password, "Not3%!&#*SQE7!.c%");
			click(submit);
			Thread.sleep(5000);
			click(submit);
			click(programms);
			click(landingPage);
	}

	@Then("Verify the Page title is {string} and click on Go to Process under the Platform Modernization section")
	public void verify_the_Page_title_is_and_click_on_Go_to_Process_under_the_Platform_Modernization_section(
			String Programmname) throws InterruptedException {
		verifyElementTextEquals(programmstitle, Programmname);
		scrollElementIntoViewJS(programmsbar, true);
		click(Platformprocess);
		Thread.sleep(5000);
	}

	@Then("Verify the Page title is {string} and click on Go to Process under the Pre-Delivery section")
	public void verify_the_Page_title_is_and_click_on_Go_to_Process_under_the_Pre_Delivery_section(
			String Platformname) {
		verifyElementTextEquals(Platformtitle, Platformname);
		scrollElementIntoViewJS(Platformbar, true);
		click(Predeliveryprocess);
	}

	@Then("Expand the PROGRAM MANAGEMENT section and click on the Customer Business Need link")
	public void expand_the_PROGRAM_MANAGEMENT_section_and_click_on_the_Customer_Business_Need_link() throws InterruptedException {
		click(Programexpand);
		click(Customerneeds);
		Thread.sleep(10000);
	}
}
