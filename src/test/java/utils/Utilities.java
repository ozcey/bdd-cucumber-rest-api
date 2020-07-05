package utils;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Utilities {

	public static RequestSpecification requestSpecification;
	public static Response response;
	public static Map<String, Object> scenarioParams = new HashMap<>();
	public static Map<String, Object> properties = new HashMap<>();

	public RequestSpecification setRequestSpecification() {
		requestSpecification = new RequestSpecBuilder().setBaseUri(Constants.BASE_URL)
				.setContentType(ContentType.JSON).build();
		return requestSpecification;
	}

	public RequestSpecification getRequestSpec() {
		return RestAssured.given().spec(setRequestSpecification());
	}

	public void getRequestSpecWithPayload(DataTable userPayload) {
		requestSpecification = getRequestSpec().body(getUserPayload(userPayload));
		scenarioParams.put("requestSpecification", requestSpecification);
	}

	public void getRequestSpecWithId() {
		Map<String, Object> properties = (Map<String, Object>) Utilities.properties.get("properties");
		Integer id = (Integer) properties.get("id");
		requestSpecification = getRequestSpec().pathParam("id", id);
		if (scenarioParams.get("requestSpecification") != null) {
			scenarioParams.put("requestSpecification", requestSpecification);
		}
	}

	public void getRequestSpecWithEmailAndPass() {
		Map<String, Object> properties = (Map<String, Object>) Utilities.properties.get("properties");
		String email = properties.get("email").toString();
		String password = properties.get("password").toString();
		requestSpecification = getRequestSpec().header("email", email).header("password", password);
		if (scenarioParams.get("requestSpecification") != null) {
			scenarioParams.put("requestSpecification", requestSpecification);
		}
	}

	public void getRequestSpecWithAuth() {
		Map<String, Object> props = (Map<String, Object>) Utilities.properties.get("properties");
		Integer id = (Integer) props.get("id");
		String jwt = properties.get("jwt").toString();
		requestSpecification = getRequestSpec().header("Authorization", jwt).pathParam("id", id);
		if (scenarioParams.get("requestSpecification") != null) {
			scenarioParams.put("requestSpecification", requestSpecification);
		}
	}

	public void getRequestSpecWithIdAndPayload(DataTable userPayload) {
		String jwt = properties.get("jwt").toString();
		requestSpecification = getRequestSpec().header("Authorization", jwt).body(getUpdateUserPayload(userPayload));
		if (scenarioParams.get("requestSpecification") != null) {
			scenarioParams.put("requestSpecification", requestSpecification);
		}
	}

	public void getResponse(String endPoint, String method) {
		RequestSpecification requestSpecification = (RequestSpecification) scenarioParams.get("requestSpecification");
		Response response = null;
		switch (method) {
		case "POST":
			response = requestSpecification.when().post(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		case "GET":
			response = requestSpecification.when().get(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		case "PUT":
			response = requestSpecification.when().put(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		case "DELETE":
			response = requestSpecification.when().delete(EndPoints.valueOf(endPoint).getEndPoint());
			break;
		}
		scenarioParams.put("response", response);
	}

	public void validateStatus(String status) {
		Response response = (Response) scenarioParams.get("response");
		HttpStatus actualStatus = HttpStatus.valueOf(response.statusCode());
		HttpStatus expectedlStatus = HttpStatus.valueOf(status);
		assertEquals(actualStatus, expectedlStatus);
	}

	public void extractProperties(DataTable data) {
		List<String> propertyList = data.asList();
		Response response = (Response) scenarioParams.get("response");
		Map<String, Object> props = new HashMap<>();
		for (String prop : propertyList) {
			props.put(prop, getValFromResponse(response, prop));
		}
		properties.put("properties", props);
	}

	public void extractProperties(String jwt) {
		Response response = (Response) scenarioParams.get("response");
		properties.put(jwt, getStringValFromResponse(response, jwt));
	}

	public void validateProperties(DataTable data) {
		Response response = (Response) scenarioParams.get("response");
		List<Map<String, String>> userData = data.asMaps(String.class, String.class);
		for (Map<String, String> userProp : userData) {
			String property = userProp.get("Property");
			String val = getStringValFromResponse(response, property);
			assertEquals(val, userProp.get("Value"));
		}
	}

	public void validatePropertyNotNull(DataTable data) {
		Response response = (Response) scenarioParams.get("response");
		List<Map<String, String>> propertyList = data.asMaps(String.class, String.class);
		for (Map<String, String> prop : propertyList) {
			String property = prop.get("Property");
			String val = getStringValFromResponse(response, property);
			assertNotNull(val);
		}
	}

	private String getStringValFromResponse(Response response, String path) {
		return new JsonPath(response.asString()).getString(path);
	}

	private Object getValFromResponse(Response response, String path) {
		return new JsonPath(response.asString()).get(path);
	}

	public String getUserPayload(DataTable data) {
		Map<String, String> userData = data.asMap(String.class, String.class);
		return Payloads.addUserPayload(userData.get("firstName"), userData.get("lastName"), userData.get("username"),
				userData.get("email"), userData.get("password"), "ROLE_USER");
	}

	private String getUpdateUserPayload(DataTable data) {
		Map<String, Object> props = (Map<String, Object>) Utilities.properties.get("properties");
		Integer id = (Integer) props.get("id");
		Map<String, String> userData = data.asMap(String.class, String.class);
		return Payloads.updateUserPayload(id, userData.get("firstName"), userData.get("lastName"),
				userData.get("username"), userData.get("email"), userData.get("password"), "ROLE_USER");
	}

}
