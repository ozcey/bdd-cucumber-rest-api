package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Constants;
import utils.Util;

public class UserStepDefinitions {

	RequestSpecification requestSpecification;
	Response response;
	Util util = new Util();

	@Given("^he\\/she adds user payload to request$")
	public void he_she_adds_user_payload(DataTable userPayload) {
		requestSpecification = util.getRequestSpec().body(util.getUserPayload(userPayload));
	}

	@When("^he/she calls \"([^\"]*)\" with \"([^\"]*)\" request$")
	public void heshe_calls_something_with_something_request(String endPoint, String method) {
		response = util.getResponse(requestSpecification, endPoint, method);
	}

	@Then("^he/she gets good response \"([^\"]*)\"$")
	public void heshe_gets_good_response_something(String status) {
		util.validateStatusCode(response, status);
	}

	@Then("^he/she extracts properties from response$")
	public void he_she_extracts_properties_from_response() {
		Constants.USER_ID = util.getLongValFromResponse(response, "id");
		Constants.EMAIL = util.getStringValFromResponse(response, "email");
		Constants.PASSWORD = util.getStringValFromResponse(response, "password");
	}

	@Then("^he\\/she asserts that response has following values$")
	public void he_she_asserts_that_response_has_following_values(DataTable data) {
		util.validateProperties(response, data);
	}

	@Given("^he/she adds user id to request$")
	public void heshe_adds_user_id_to_request() {
		requestSpecification = util.getRequestSpec().pathParam("userId", Constants.USER_ID);
	}

	@Given("^he/she gets authorization using email and password$")
	public void he_she_gets_authorization_using_email_and_password() {
		requestSpecification = util.getRequestSpec().header("email", Constants.EMAIL).header("password",
				Constants.PASSWORD);
	}

	@Then("^he/she asserts that properties not null$")
	public void he_she_asserts_that_properties_not_null(io.cucumber.datatable.DataTable dataTable) {
		util.validateNotNull(response, dataTable);
	}

	@Then("he\\/she extracts {string} from response")
	public void he_she_extracts_from_response(String jwt) {
		Constants.JWT = util.getStringValFromResponse(response, jwt);
	}

	@Given("he\\/she adds user payload with id to request")
	public void he_she_adds_user_payload_with_id_to_request(DataTable dataTable) {
		requestSpecification = util.getRequestSpec().header("Authorization", Constants.JWT)
				.body(util.getUserPayload(Constants.USER_ID, dataTable));
	}

	@Given("he\\/she deletes an user using id")
	public void he_she_deletes_an_user_using_id() {
		requestSpecification = util.getRequestSpec().header("Authorization", Constants.JWT).pathParam("userId",
				Constants.USER_ID);
	}

}
