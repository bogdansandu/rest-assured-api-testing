package googleApiTests;

import googleApi.Payload;
import googleApi.Resource;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;

/**
 * Created by bogdansandu on 2/26/18.
 */
public class GoogleMapsTestSuite {
    private static Logger log = LogManager.getLogger(GoogleMapsTestSuite.class.getName());
    Properties properties = new Properties();
    @BeforeTest
    public void getData() throws IOException
    {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//environment.properties");
        properties.load(fis);
    }

    @Test
    public void AddandDeletePlace()
    {

        //Task 1- Grab the response
        log.info("Host information"+properties.getProperty("HOSTGoogle"));
        RestAssured.baseURI = properties.getProperty("HOSTGoogle");
        Response res = given().

                queryParam("key",properties.getProperty("KEY")).
                body(Payload.createPlaceData()).
                when().
                post(Resource.createPlaceData()).
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status",equalTo("OK")).
                extract().response();
        // Task 2- Grab the Place ID from response

        String responseString = res.asString();
        log.info(responseString);
        JsonPath js = new JsonPath(responseString);
        String placeid = js.get("place_id");
        log.info(placeid);


        //Task 3 place this place id in the Delete request
        given().
                queryParam("key",properties.getProperty("KEY")).
                body("{"+
                        "\"place_id\": \""+placeid+"\""+
                        "}").
                when().
                post("/maps/api/place/delete/json").
                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status",equalTo("OK"));
    }
}
