import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; //given is inside static package(need to write manually)

import static org.hamcrest.Matchers.*; //for equalTo method

import org.testng.Assert;

import files.payload;
import files.ReusableMethod;

public class AddPlaces {

	public static void main(String[] args) {
		// Validate if AddPlaces is working as expected
		
		//given - all input details, header in given acts as input validation
		//when - Submit the API (resourse, http method inside when)
		//then - validate the response, hearder in then acts as output validation
		//log.all - to see everything in the console panel
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Add Place
		String response=given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type","application/json")
				.body(payload.AddPlace())
				.when().post("maps/api/place/add/json")
				.then().log().all()
				.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.18 (Ubuntu)")
				.extract().asString();
		
		System.out.println(response);
		
		//assertthat - is for asserting things(for validation)
		
		//Add Place > Update Place with New Address > Get place to validate New Address is present in response or not	
		JsonPath js=new JsonPath(response); //for parsing Json
		String placeid=js.getString("place_id");
		
		System.out.println("placeid");
		
		//Automate update the place using PUT method
		
		String new_address = "Chowk Bazar, Golaghat";
		
		given().log().all()
		.queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\""+new_address+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all()
		.assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		//Get place response
		String response1 = given().log().all()
				.queryParam("key", "qaclick123")
				.queryParam("place_id",placeid)
				.when().get("maps/api/place/get/json")
				.then().log().all()
				.assertThat().statusCode(200)
				.extract().response().asString();
		
		//This is created on Java, for Rest Assured use 'body' method
		JsonPath js1 = ReusableMethod.rawToJson(response1);
		String actual_address = js1.getString("address");
		System.out.println(actual_address);
		
		//TestNG comparing
		Assert.assertEquals(actual_address, new_address);
		
	}

}
