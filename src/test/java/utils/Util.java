package utils;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Util {

	public static RequestSpecification requestSpecification;

	public RequestSpecification setRequestSpecification() {
		requestSpecification = new RequestSpecBuilder().setBaseUri(EndPoints.valueOf("BaseUrl").getEndPoint())
				.setContentType(ContentType.JSON).build();
		return requestSpecification;
	}

	public RequestSpecification getRequestSpec() {
		return RestAssured.given().spec(setRequestSpecification());
	}

	public String getStringValFromResponse(Response response, String path) {
		return new JsonPath(response.asString()).getString(path);
	}

	public Long getLongValFromResponse(Response response, String path) {
		return new JsonPath(response.asString()).getLong(path);
	}

	public Response getResponse(RequestSpecification requestSpec, String endPoint, String method) {
		Response response = null;
		switch (method) {
		case "POST":
			response = requestSpec.when().post(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		case "GET":
			response = requestSpec.when().get(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		case "PUT":
			response = requestSpec.when().put(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		case "DELETE":
			response = requestSpec.when().delete(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		}
		return response;
	}

	public void validateStatusCode(Response response, String status) {
		HttpStatus actualStatus = HttpStatus.valueOf(response.statusCode());
		HttpStatus expectedlStatus = HttpStatus.valueOf(status);
		assertEquals(actualStatus, expectedlStatus);
	}

	public void validateProperties(Response response, DataTable data) {
		List<Map<String, String>> userData = data.asMaps(String.class, String.class);
		for (Map<String, String> userProp : userData) {
			String property = userProp.get("Property");
			String val = getStringValFromResponse(response, property);
			assertEquals(val, userProp.get("Value"));
		}
	}

	public void validateNotNull(Response response, DataTable data) {
		List<Map<String, String>> userData = data.asMaps(String.class, String.class);
		for (Map<String, String> userProp : userData) {
			String property = userProp.get("Property");
			assertNotNull(property);
		}

	}

	public String getUserPayload(DataTable data) {
		Map<String, String> userData = data.asMap(String.class, String.class);
		return Payloads.addUserPayload(userData.get("firstName"), userData.get("lastName"), userData.get("username"),
				userData.get("email"), userData.get("password"), "ROLE_USER");
	}
	
	public String getUserPayload(Long userId, DataTable data) {
		Map<String, String> userData = data.asMap(String.class, String.class);
		return Payloads.updateUserPayload(userId, userData.get("firstName"), userData.get("lastName"), userData.get("username"),
				userData.get("email"), userData.get("password"), "ROLE_USER");
	}
	
    public String getCustomerPayload(DataTable data) {
        Map<String, String> customerData = data.asMap(String.class, String.class);
        return Payloads.addCustomerPayload(customerData.get("firstName"),customerData.get("lastName"),
                customerData.get("email"), customerData.get("password"), customerData.get("phone"),
                customerData.get("street"), customerData.get("city"), customerData.get("state"),
                customerData.get("zipcode"), customerData.get("country")
                );
    }
}
