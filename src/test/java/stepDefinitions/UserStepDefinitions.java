package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.Util;

public class UserStepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStepDefinitions.class);
    RequestSpecification requestSpecification;
    Response response;
    Util util = new Util();

    @Given("^he\\/she adds user payload to request$")
    public void he_she_adds_user_payload(DataTable userPayload){
        requestSpecification = util.getRequestSpec()
                .body(util.getUserPayload(userPayload));
    }

    @When("^he/she calls \"([^\"]*)\" with \"([^\"]*)\" request$")
    public void heshe_calls_something_with_something_request(String endPoint, String method){
        response = util.getResponse(requestSpecification, endPoint, method);
    }

    @Then("^he/she gets good response \"([^\"]*)\"$")
    public void heshe_gets_good_response_something(String status){
        util.validateStatusCode(response, status);
    }

    @Then("^he\\/she asserts that response has following values$")
    public void he_she_asserts_that_response_has_following_values(DataTable data){
        util.validateProperties(response, data);
        Constants.USER_ID = util.getLongValFromJsonPath(response, "id");
    }

    @Given("^he/she adds user id to request$")
    public void heshe_adds_user_id_to_request(){
        requestSpecification = util.getRequestSpec()
                .pathParam("userId", Constants.USER_ID);
    }

}
