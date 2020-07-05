package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import utils.Utilities;

public class UserStepDefinitions {

	Utilities utilities = new Utilities();

	@Given("^he\\/she adds user payload to request$")
	public void he_she_adds_user_payload(DataTable userPayload) {
		utilities.getRequestSpec(userPayload, "POST");
	}

	@When("^he/she calls \"([^\"]*)\" with \"([^\"]*)\" request$")
	public void heshe_calls_something_with_something_request(String endPoint, String method) {
		utilities.getResponse(endPoint, method);
	}

	@Then("^he/she gets good response \"([^\"]*)\"$")
	public void heshe_gets_good_response_something(String status) {
		utilities.validateStatus(status);
	}

	@Then("^he/she extracts properties from response$")
	public void he_she_extracts_properties_from_response(DataTable data) {
		utilities.extractProperties(data);
	}

	@Then("^he\\/she asserts that response has following values$")
	public void he_she_asserts_that_response_has_following_values(DataTable data) {
		utilities.validateProperties(data);
	}

	@Given("^he/she adds user id to request$")
	public void heshe_adds_user_id_to_request() {
		utilities.getRequestSpec(null, "GET");
	}

	@Given("^he/she gets authorization using email and password$")
	public void he_she_gets_authorization_using_email_and_password() {
		utilities.getRequestSpec(null, "LOGIN");
	}

	@And("^he/she extracts \"([^\"]*)\" from response$")
	public void he_she_extracts_from_response(String jwt) {
		utilities.extractProperties(jwt);
	}

	@Then("^he/she asserts that properties not null$")
	public void he_she_asserts_that_properties_not_null(DataTable dataTable) {
		utilities.validatePropertyNotNull(dataTable);
	}

	@Given("^he/she adds user payload with id to request$")
	public void he_she_adds_user_payload_with_id_to_request(DataTable userPayload) {
		utilities.getRequestSpec(userPayload, "PUT");
	}

	@Given("^he/she deletes an user using id$")
	public void he_she_deletes_an_user_using_id() {
		utilities.getRequestSpec(null, "DELETE");
	}
}
