package com.qentelli.automation.stepdefs.common.application;

import org.openqa.selenium.By;

import com.qentelli.automation.common.World;
import com.qentelli.automation.pages.BasePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class SampleHtmlPage extends BasePage {

private World world;
private static final String filepath = System.getProperty("user.dir") + "/SamplePage/samplehtml.html";

	public SampleHtmlPage(World world)
	{
		super(world, world.driver);
		this.world = world;
	}
	
	@Given("Navigate to the sample html page")
	public void navigate_to_the_sample_html_page()
	{

		world.driver.get(filepath);
	}
	
	@Then("verify the link and click on the link")
	public void verify_the_link_and_click_on_the_link() 
	{

		boolean link= world.driver.findElement(By.xpath("//a[text()='Si qua in iis corrigere voluit, deteriora fecit.']")).isDisplayed();
		if (link)
		{
			world.driver.findElement(By.xpath("//a[text()='Si qua in iis corrigere voluit, deteriora fecit.']")).click();
		} else
		{
			System.out.println("fail the test");
		}
	}
	
	@Then("close the browser")
	public void close_the_browser() 
	{
		world.driver.close();
	}
}
