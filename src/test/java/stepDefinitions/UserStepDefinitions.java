package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

public class UserStepDefinitions {

    @Given("he\\/she adds user payload")
    public void he_she_adds_user_payload(DataTable userPayload) {

    }

    @When("he\\/she calls {string} with {string} request")
    public void he_she_calls_with_request(String endPoint, String method) {

    }

    @Then("he\\/she gets good response {string}")
    public void he_she_gets_good_response(String status) {

    }

    @Then("he\\/she asserts that response has following values")
    public void he_she_asserts_that_response_has_following_values(DataTable dataTable) {

    }
}
