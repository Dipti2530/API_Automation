package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import context.ServiceResponseContext;
import dto.SatelliteTLE_Response;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import util.Log;
import util.RestAPIMethods;
import util.RestEndpoints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Satellite_TLE {

    private ObjectMapper mapper;
    private Map<String, String> paramsMap;
    private Response response;
    private static final String GET_TLE_DATA_PATH = "{version}/satellites/{id}/tles";
    private static final String TEXT_RESPONSE = "ISS (ZARYA)\n" +
            "1 25544U 98067A   21207.21215938  .00002838  00000-0  59904-4 0  9995\n" +
            "2 25544  51.6425 149.7400 0001282 199.9245 305.2594 15.48867083294645";

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        paramsMap = new HashMap<>();
    }

    @After
    public void clear() {
        ServiceResponseContext.clear();
    }

    @When("a request is sent to get TLE data for satellite {string} on {string} endpoint")
    public void aRequestIsSentToGetTLEDataForSatellite(String satelliteId, String version) {
        response = getSatelliteTLEResponse(satelliteId, version);
        Log.info(response.getBody().asString());

    }

    @When("a request is sent to get TLE data for satellite {string} on {string} endpoint with")
    public void aRequestIsSentToGetTLEDataForSatelliteWith(String satelliteId, String version, List<Map<String, String>> parameters) {
        parameters.forEach(
                param -> paramsMap.put(param.get("paramName"), param.get("paramValue"))
        );
        aRequestIsSentToGetTLEDataForSatellite(satelliteId, version);
    }


    @And("expected response with TLE data is returned with")
    public void expectedResponseWithTLE_DataIsReturned(List<Map<String, String>> outputParam) throws JsonProcessingException {
        Map<String, String> output = outputParam.get(0);

        SatelliteTLE_Response TLEResponse = mapper
                .readValue(ServiceResponseContext.getResponse().getBody().asString(),
                        SatelliteTLE_Response.class);

        Assert.assertEquals(ServiceResponseContext.getResponse().getHeader("Content-Type"), output.get("Content-Type"));

        if (ServiceResponseContext.getResponse().getHeader("Content-Type").equalsIgnoreCase("application/json")) {
            Assert.assertEquals(TLEResponse.getId(), output.get("id"));
            Assert.assertEquals(TLEResponse.getName(), output.get("name"));
            Assert.assertNotNull(TLEResponse.getRequested_timestamp());
            Assert.assertNotNull(TLEResponse.getTle_timestamp());
            Assert.assertNotNull(TLEResponse.getHeader());
            Assert.assertNotNull(TLEResponse.getLine1());
            Assert.assertNotNull(TLEResponse.getLine2());
        }
    }

    @And("expected response with TLE data is returned with contentType as {string}")
    public void expectedResponseWithTLEDataIsReturnedWithContentTypeAsTextPlain(String contentType) throws Throwable {
        Assert.assertEquals(ServiceResponseContext.getResponse().getHeader("Content-Type"), contentType);
        Assert.assertTrue(ServiceResponseContext.getResponse().asString().contains(TEXT_RESPONSE));
    }

    public Response getSatelliteTLEResponse(String id, String version) {
        response = RestAPIMethods.getWithParams(
                RestEndpoints.getUrlWithPath(GET_TLE_DATA_PATH).replace("{version}", version).replace("{id}", id),
                paramsMap);
        ServiceResponseContext.setResponse(response);
        return response;
    }

}
