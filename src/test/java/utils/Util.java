package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Util {

    public static RequestSpecification requestSpecification;

    public RequestSpecification setRequestSpecification(){
        SessionFilter sessionFilter = new SessionFilter();
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_URL)
                .addFilter(sessionFilter)
                .setContentType(ContentType.JSON)
                .build();
        return requestSpecification;
    }

    public String getJsonPath(Response response, String path){
        return new JsonPath(response.asString()).getString(path);
    }
}
