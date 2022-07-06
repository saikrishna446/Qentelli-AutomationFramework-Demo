package com.qentelli.automation.pages.common;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.qentelli.automation.common.World;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.utilities.CommonUtilities;

public class AssessmentPage extends BasePage {

	public String mail;
	Logger logger;
	ResourceBundle qentelliPageElements;
	By homepage;
	By BeginAssessment;
	By demographictitle;
	By Name;
	By CompanyName;
	By Email;
	By Jobtitle;
	By selectJob;
	By getstart;
	By agreecheckbox;
	By methodology;
	By production;
	By backlog;
	By primarystruture;
	By approach;
	By next;
	By versioncontrol;
	By branching;
	By automate;
	By build;
	By TestingProcess;
	By next1;
	By Prerelease;
	By stage;
	By Infastructue;
	By Yes1;
	By none;
	By poor;
	By yes2;
	By sometime;
	By yes3;
	By bugs;
	By viewresult;
	By results;
	By thankyou;
	By next2;
	

	public void InitElements() {
		qentelliPageElements = ResourceBundle.getBundle("com.qentelli.automation.Airlines.CompanyAssessment",
				Locale.US);
		logger = LogManager.getLogger(AirlineHomePage.class);
		homepage = By.xpath(qentelliPageElements.getString("QentelliAssessment.hompage"));
		BeginAssessment = By.xpath(qentelliPageElements.getString("QentelliAssessment.BeginAssessment"));
		demographictitle= By.xpath(qentelliPageElements.getString("QentelliAssessment.demographictitle"));
		Name= By.xpath(qentelliPageElements.getString("QentelliAssessment.Name"));
		CompanyName= By.xpath(qentelliPageElements.getString("QentelliAssessment.CompanyName"));
		Email= By.xpath(qentelliPageElements.getString("QentelliAssessment.Email"));
		Jobtitle= By.xpath(qentelliPageElements.getString("QentelliAssessment.Jobtitle"));
		selectJob= By.xpath(qentelliPageElements.getString("QentelliAssessment.selectJob"));
		agreecheckbox= By.xpath(qentelliPageElements.getString("QentelliAssessment.agreecheckbox"));
		getstart= By.xpath(qentelliPageElements.getString("QentelliAssessment.getstart"));
		methodology=By.xpath(qentelliPageElements.getString("QentelliAssessment.methodology"));
		production=By.xpath(qentelliPageElements.getString("QentelliAssessment.production"));
		backlog=By.xpath(qentelliPageElements.getString("QentelliAssessment.No"));
		primarystruture=By.xpath(qentelliPageElements.getString("QentelliAssessment.struture"));
		approach=By.xpath(qentelliPageElements.getString("QentelliAssessment.infastruture"));
		next=By.xpath(qentelliPageElements.getString("QentelliAssessment.next"));
		versioncontrol=By.xpath(qentelliPageElements.getString("QentelliAssessment.versioncontrol"));
		branching=By.xpath(qentelliPageElements.getString("QentelliAssessment.branching"));
		automate=By.xpath(qentelliPageElements.getString("QentelliAssessment.automate"));
		build=By.xpath(qentelliPageElements.getString("QentelliAssessment.build"));
		TestingProcess=By.xpath(qentelliPageElements.getString("QentelliAssessment.TestingProcess"));
		next1=By.xpath(qentelliPageElements.getString("QentelliAssessment.next1"));
		Prerelease=By.xpath(qentelliPageElements.getString("QentelliAssessment.Prerelease"));
		stage=By.xpath(qentelliPageElements.getString("QentelliAssessment.stage"));
		Infastructue=By.xpath(qentelliPageElements.getString("QentelliAssessment.Infastructue"));
		Yes1=By.xpath(qentelliPageElements.getString("QentelliAssessment.Yes1"));
		none=By.xpath(qentelliPageElements.getString("QentelliAssessment.none"));
		poor=By.xpath(qentelliPageElements.getString("QentelliAssessment.poor"));
		yes2=By.xpath(qentelliPageElements.getString("QentelliAssessment.yes2"));
		sometime=By.xpath(qentelliPageElements.getString("QentelliAssessment.sometime"));
		yes3=By.xpath(qentelliPageElements.getString("QentelliAssessment.yes3"));
		bugs=By.xpath(qentelliPageElements.getString("QentelliAssessment.bugs"));
		viewresult=By.xpath(qentelliPageElements.getString("QentelliAssessment.viewresult"));
		results=By.xpath(qentelliPageElements.getString("QentelliAssessment.results"));
		thankyou=By.xpath(qentelliPageElements.getString("QentelliAssessment.thankyou"));
		next2=By.xpath(qentelliPageElements.getString("QentelliAssessment.next2"));



	}

	public AssessmentPage(World world) {
		super(world, world.driver);
		InitElements();
	}

	public void verifyHomePage() {
		verifyElementTextEquals(homepage, "DevOps Maturity Self-Assessment");
	}

	public void beginAssessment() {
		click(BeginAssessment);
	}
	
	public void entertheDemoGraphicDetails() {
		
		// Enter the demographic details
		verifyElementTextEquals(demographictitle, "Demographics");
		enterText(Name, "QentelliUser");
		enterText(CompanyName, "Qentelli"+CommonUtilities.randomString(3));
		enterText(Email, CommonUtilities.randomEmail());
		click(Jobtitle);
		click(selectJob);
		//clickByJS(agreecheckbox);
		click(agreecheckbox);
		click(getstart);

	}
	
	public void otherDemographics() {
		
		// Select the Agility details
		click(methodology);
		click(production);
		click(backlog);
		click(primarystruture);
		click(approach);
		click(next);
		// Select the Technology details
		click(versioncontrol);
		click(branching);
		click(automate);
		click(build);
		click(TestingProcess);
		click(next1);
		// Enter the Technology details
		click(Prerelease);
		click(stage);
		click(Infastructue);
		click(Yes1);
		click(next1);
		// Select the Culture details
		click(none);
		click(poor);
		click(yes2);
		click(sometime);
		click(next2);
		// Select the Operation details
		click(yes3);
		click(bugs);
		click(viewresult);

	}
	
	public void verifytheresults() {
		
		verifyElementTextEquals(results, "HERE ARE YOUR RESULTS!");
		verifyElementTextEquals(thankyou, "Thank you");

	}

}
