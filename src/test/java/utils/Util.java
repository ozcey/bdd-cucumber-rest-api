package utils;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Util {

    public static RequestSpecification requestSpecification;

    public RequestSpecification setRequestSpecification(){
        SessionFilter sessionFilter = new SessionFilter();
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_URL)
//                .addFilter(sessionFilter)
                .setContentType(ContentType.JSON)
                .build();
        return requestSpecification;
    }

    public RequestSpecification getRequestSpec(){
        return RestAssured.given().spec(setRequestSpecification());
    }

    public String getStringValFromJsonPath(Response response, String path){
        return new JsonPath(response.asString()).getString(path);
    }

    public Long getLongValFromJsonPath(Response response, String path){
        return new JsonPath(response.asString()).getLong(path);
    }

    public Response getResponse(RequestSpecification requestSpec, String endPoint, String method){
        Response response = null;
        switch (method) {
            case "POST":
                response = requestSpec
                        .when().post(EndPoints.valueOf(endPoint).getEndPoint());
                break;
            case "GET":
                response = requestSpec
                        .when().get(EndPoints.valueOf(endPoint).getEndPoint());
                break;
        }
        return response;
    }

    public void validateStatusCode(Response response, String status){
        HttpStatus actualStatus = HttpStatus.valueOf(response.statusCode());
        HttpStatus expectedlStatus = HttpStatus.valueOf(status);
        assertEquals(actualStatus, expectedlStatus);
    }

    public void validateProperties(Response response, DataTable data){
        List<Map<String, String>> userData = data.asMaps(String.class, String.class);
        for (Map<String, String> userProp : userData) {
            String property = userProp.get("Property");
            String val = getStringValFromJsonPath(response, property);
            assertEquals(val, userProp.get("Value"));
        }
    }

    public String getUserPayload(DataTable data){
        Map<String, String> userData = data.asMap(String.class, String.class);
        return Payloads.addUserPayload(userData.get("firstName"),
                userData.get("lastName"), userData.get("username"),
                userData.get("email"), userData.get("password"), "ROLE_USER");
    }
}
