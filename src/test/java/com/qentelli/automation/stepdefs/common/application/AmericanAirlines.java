package com.qentelli.automation.stepdefs.common.application;

import com.qentelli.automation.common.World;
import com.qentelli.automation.libraries.ConfigFileReader;
import com.qentelli.automation.pages.common.AirlineHomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class AmericanAirlines {

	private World world;
	

	public AmericanAirlines(World world) {
		this.world = world;
	}

	@Given("Navigate to American Airlines site")
	public void navigate_to_American_Airlines_site() {

		AirlineHomePage airHomePage = new AirlineHomePage(world);
		airHomePage.navigateToUrl(ConfigFileReader.getTestDataProperty("Airlines_URL"));
		airHomePage.windowmaxmize();

	}
	@Given("click on Search button")
	public void click_on_Search_button() {
		AirlineHomePage airHomePage = new AirlineHomePage(world);
		airHomePage.clicksearchbutton();
	}

	@Then("verify the mandatory messages {string} and {string}")
	public void verify_the_mandatory_messages_and(String departurecity, String returncity) {
		AirlineHomePage airHomePage = new AirlineHomePage(world);
		airHomePage.verifythemandatorymessages(departurecity, returncity);
		airHomePage.closeWindow();
	}
}
