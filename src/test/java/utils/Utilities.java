package utils;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
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
		requestSpecification = RestAssured.given()
				.baseUri(EndPoints.valueOf("BaseUrl").getEndPoint())
				.contentType(ContentType.JSON);
		return requestSpecification;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getProperties() {
		return ((Map<String, Object>) properties.get("properties"));
	}

	public void getRequestSpec(DataTable dataTable, String operation) {
		Map<String, Object> props = getProperties(); 

		switch (operation) {
		case "POST":
			requestSpecification = setRequestSpecification().body(getUserPayload(dataTable));
			break;
		case "GET":
			requestSpecification = setRequestSpecification().pathParam("id", (Integer) props.get("id"));
			break;
		case "LOGIN":
			requestSpecification = setRequestSpecification().header("email", props.get("email").toString())
					.header("password", props.get("password").toString());
			break;
		case "PUT":
			requestSpecification = setRequestSpecification().header("Authorization", properties.get("jwt").toString())
					.body(getUpdateUserPayload(dataTable));
			break;
		case "DELETE":
			requestSpecification = setRequestSpecification().header("Authorization", properties.get("jwt").toString())
					.pathParam("id", (Integer) props.get("id"));
			break;

		}

		scenarioParams.put("requestSpecification", requestSpecification);
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
		List<Map<String, String>> propertyList = data.asMaps(String.class, String.class);
		for (Map<String, String> prop : propertyList) {
			String property = prop.get("Property");
			String val = getStringValFromResponse(response, property);
			assertEquals(val, prop.get("Value"));
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

	private String getUserPayload(DataTable data) {
		Map<String, String> userData = data.asMap(String.class, String.class);
		return Payloads.addUserPayload(userData.get("firstName"), userData.get("lastName"), userData.get("username"),
				userData.get("email"), userData.get("password"), "ROLE_USER");
	}

	private String getUpdateUserPayload(DataTable data) {
		Map<String, Object> props = getProperties();
		Integer id = (Integer) props.get("id");
		Map<String, String> userData = data.asMap(String.class, String.class);
		return Payloads.updateUserPayload(id, userData.get("firstName"), userData.get("lastName"),
				userData.get("username"), userData.get("email"), userData.get("password"), "ROLE_USER");
	}

}
