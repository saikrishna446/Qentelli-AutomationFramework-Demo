package com.qentelli.automation.pages.common;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.qentelli.automation.common.World;
import com.qentelli.automation.pages.BasePage;

public class AirlineHomePage extends BasePage {

	
	public String mail;
	Logger logger;
	ResourceBundle amazonPageElements;
	By From;
	By Fromlist;
	By To;
	By Tolist;
	By depart;
	By returnflight;
	By search;
	By departuremessage;
	By returnmessage;
	By samplehtml;

	public void InitElements() {
		amazonPageElements = ResourceBundle.getBundle("com.qentelli.automation.Airlines.AmericanAirlines", Locale.US);
		logger = LogManager.getLogger(AirlineHomePage.class);
		From = By.id(amazonPageElements.getString("Airlines.From"));
		Fromlist = By.xpath(amazonPageElements.getString("Airlines.From.list"));
		To = By.id(amazonPageElements.getString("Airlines.To"));
		Tolist = By.xpath(amazonPageElements.getString("Airlines.To.list"));
		depart = By.id(amazonPageElements.getString("Airline.depart"));
		returnflight = By.id(amazonPageElements.getString("Airline.return"));
		search = By.id(amazonPageElements.getString("Airline.search"));
		departuremessage = By.xpath(amazonPageElements.getString("departure.message"));
		returnmessage = By.xpath(amazonPageElements.getString("return.message"));

	}

	public AirlineHomePage(World world) {
		super(world, world.driver);
		InitElements();
	}

	public void enterthedataAirlinePage(String from, String to, String fromdate, String todate) {
		
		enterText(From, "NYC");
		click(Fromlist);
		enterText(To, "wash");
		click(Tolist);
		click(search);
	}

	public void clicksearchbutton() {
		click(search);
	}

	public void verifythemandatorymessages(String departurename, String returnname) {
		verifyElementTextEquals(departuremessage, departurename);
		verifyElementTextEquals(returnmessage, returnname);
	}

	

	public void clickonhtmllink() {
		click(samplehtml);
	}

}
