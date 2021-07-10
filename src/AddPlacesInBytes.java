import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;

public class AddPlacesInBytes {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Adding Place to server using System path, without saving the Json body inside code
		String response=given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type","application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\malli\\OneDrive\\Desktop\\APIs\\Addplaces.json"))))
				.when().post("maps/api/place/add/json")
				.then().log().all()
				.assertThat()
				.statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.18 (Ubuntu)")
				.extract().asString();
		
		System.out.println(response);

	}

}
