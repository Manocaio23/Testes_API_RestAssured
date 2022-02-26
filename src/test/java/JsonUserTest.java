import static io.restassured.RestAssured.*;
import static junit.framework.Assert.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

public class JsonUserTest {
	
	@Test
	public void PrimeiroNivel() {
		given()
		
		.when()
			.get("https://restapi.wcaquino.me/users/1")
		.then()
		.statusCode(200)
		.body("id", is(1))
		.body("name", containsString("Silva"))
		.body("age", greaterThan(18))
		
		;
		
	}
	
	@Test
	public void PrimeiroNivelOutrasFormas() {
		Response response = RestAssured.request(Method.GET,"https://restapi.wcaquino.me/users/1");
		
		//path - extrai o id de dentro do jason
		System.out.println(response.path("id"));
		//transformar o primitivo em um objeto inteiro
		assertEquals(new Integer(1),response.path("id"));
		
		//jason - passando no contrutor o corpo inteiro
		JsonPath jpath = new JsonPath(response.asString());
		assertEquals(1,jpath.getInt("id")); // pegando o valor da propriedade id
		
		// from 
		int id = JsonPath.from(response.asString()).getInt("id");
		assertEquals(1,id);
		
	}
	}
