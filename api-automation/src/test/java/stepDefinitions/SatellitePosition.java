package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import context.ServiceResponseContext;
import dto.SatellitePositionResponse;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import util.Log;
import util.RestAPIMethods;
import util.RestEndpoints;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SatellitePosition {
    private ObjectMapper mapper;
    private Map<String, String> paramsMap;
    private Response response;
    private static final String GET_POSITIONS_PATH = "/{version}/satellites/{id}/positions";

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        paramsMap = new HashMap<>();
    }

    @After
    public void clear() {
        ServiceResponseContext.clear();
    }


    @When("a request is sent to get Satellite position of {string} on {string} endpoint with")
    public void aRequestIsSentToGetSatellitePositionForWith(String satelliteId, String version, List<Map<String, String>> parameters) {
        parameters.forEach(
                param -> paramsMap.put(param.get("paramName"), param.get("paramValue"))
        );
        response = getSatellitePositionsResponse(satelliteId, version);
        Log.info(response.getBody().asString());
    }


    @Then("success response is received with HTTP status code {int}")
    public void successResponseIsReceivedWithStatusCode(int statusCode) {
        Assert.assertEquals(ServiceResponseContext.getResponse().getStatusCode(), statusCode);
    }

    @And("response contains satellite position details as expected")
    public void responseContainsSatellitePositionDetailsAsExpected(List<Map<String, String>> outputParam) throws JsonProcessingException {
        Map<String, String> responseParam = outputParam.get(0);

        SatellitePositionResponse[] satellitePositionsResponse = mapper
                .readValue(ServiceResponseContext.getResponse().getBody().asString(), SatellitePositionResponse[].class);

        Assert.assertEquals(ServiceResponseContext.getResponse().getHeader("Content-Type"),"application/json");
        Assert.assertEquals(satellitePositionsResponse.length, numberOfTimestampsPassedInRequest());

        for (SatellitePositionResponse positionResponse : satellitePositionsResponse) {
            Assert.assertEquals(positionResponse.getName(), responseParam.get("name"));
            Assert.assertEquals(positionResponse.getId(), responseParam.get("id"));
            Assert.assertNotNull(positionResponse.getAltitude());
            Assert.assertNotNull(positionResponse.getVelocity());
            Assert.assertNotNull(positionResponse.getVisibility());
            Assert.assertNotNull(positionResponse.getFootprint());
            Assert.assertNotNull(positionResponse.getTimestamp());
            Assert.assertNotNull(positionResponse.getDaynum());
            Assert.assertNotNull(positionResponse.getSolar_lat());
            Assert.assertNotNull(positionResponse.getSolar_lon());
            Assert.assertEquals(positionResponse.getUnits(), responseParam.get("units"));
        }

    }

    @Then("an error should be returned as {string} with HTTP status code {int}")
    public void anErrorShouldBeReturnedAsWithHTTPStatusCode(String message, int statusCode) {
        Assert.assertEquals(ServiceResponseContext.getResponse().getStatusCode(), statusCode);
        Assert.assertEquals(ServiceResponseContext.getResponse().jsonPath().get("error"), message);
    }

    public Response getSatellitePositionsResponse(String id, String version) {
        response = RestAPIMethods.getWithParams(
                RestEndpoints.getUrlWithPath(GET_POSITIONS_PATH).replace("{version}", version).replace("{id}", id),
                paramsMap);
        ServiceResponseContext.setResponse(response);
        return response;
    }

    private int numberOfTimestampsPassedInRequest() {
        List<String> values = Arrays.asList(paramsMap.get("timestamps").split(","));
        return values.size();
    }
}
