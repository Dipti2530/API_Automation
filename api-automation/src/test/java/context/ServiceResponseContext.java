package context;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ServiceResponseContext {

    public static final ThreadLocal<Response> GET_RESPONSE_CONTEXT = new ThreadLocal<>();

    public static void setResponse(Response response) {
        GET_RESPONSE_CONTEXT.set(response);
    }

    public static Response getResponse() {
        return GET_RESPONSE_CONTEXT.get();
    }

    public static void clear(){
        GET_RESPONSE_CONTEXT.remove();
    }
}
