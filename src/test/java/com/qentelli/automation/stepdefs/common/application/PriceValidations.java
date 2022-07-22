package com.qentelli.automation.stepdefs.common.application;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import com.qentelli.automation.services.APIResources;
import com.qentelli.automation.services.TestDataBuild;
import com.qentelli.automation.services.Utils;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PriceValidations extends Utils {

	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	Utils utils;

	@Given("Add Place Payload with {string}  {string} {string}")
	public void add_Place_Payload_with(String string, String string2, String string3) throws IOException {
		System.out.println(given().spec(requestSpecification()).body(data.addPlacePayLoad(string, string2, string3)));
		res = given().spec(requestSpecification()).body(data.addPlacePayLoad(string, string2, string3));
		System.out.println(res);
	}
	@Given("Add Place Payloadssssss with {string}  {string} {string}")
	public void add_Place_Payloads_with(String string, String string2, String string3) throws IOException {
		System.out.println(given().spec(requestSpecification()).body(data.addPlacePayLoad(string, string2, string3)));
		res = given().spec(requestSpecification()).body(data.addPlacePayLoad(string, string2, string3));
		System.out.println(res);
	}


	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {

		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {

		assertEquals(response.getStatusCode(), 200);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
		assertEquals(utils.getJsonPath(response, keyValue), Expectedvalue);

	}

	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {

		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	}
}

