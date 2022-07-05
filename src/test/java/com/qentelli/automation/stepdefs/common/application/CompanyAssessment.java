package com.qentelli.automation.stepdefs.common.application;

import com.qentelli.automation.common.World;
import com.qentelli.automation.libraries.ConfigFileReader;
import com.qentelli.automation.pages.common.AssessmentPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CompanyAssessment {

	private World world;

	public CompanyAssessment(World world) {
		this.world = world;
	}

	@Given("User navigates to Home Page and starts the Assessment")
	public void user_navigates_to_Home_Page_and_starts_the_Assessment() {
		AssessmentPage assesment= new AssessmentPage(this.world);
		assesment.navigateToUrl(ConfigFileReader.getTestDataProperty("Qentelli.Assesment.URL"));
		assesment.windowmaxmize();
		assesment.verifyHomePage();
		assesment.beginAssessment();
	}

	@Then("Enter the Demographic details")
	public void enter_the_Demographic_details() {
		System.out.println("Enter the Demographic details");
		AssessmentPage assesment= new AssessmentPage(this.world);
		assesment.entertheDemoGraphicDetails();
	}

	@Then("Completes the Assessment")
	public void completes_the_Assessment() {
		System.out.println("Completes the Assessment");
		AssessmentPage assesment= new AssessmentPage(this.world);
		assesment.otherDemographics();


	}

	@Then("Validates the results")
	public void validates_the_results() {
		System.out.println("Validates the results");
		AssessmentPage assesment= new AssessmentPage(this.world);
		assesment.verifytheresults();

	}
}
