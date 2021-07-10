package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethod {
	
	public static JsonPath rawToJson(String response1)
	{
		JsonPath js1 = new JsonPath(response1);
		return js1;
	}

}
