package util;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.Map;

public class RestAPIMethods {

    public static Response getWithParams(String url, Map<String,String> queryParams){
        return given().log().all()
                .queryParams(queryParams)
                .get(url)
                .thenReturn();
    }

    public static Response getResponse(String url){
        return given().log().all()
                .get(url)
                .thenReturn();
    }
}
