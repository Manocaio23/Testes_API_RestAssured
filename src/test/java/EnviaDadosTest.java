import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.http.ContentType;

public class EnviaDadosTest {

	@Test
	public void deveEnviarValorViaQuery() {
		
		given()
			.log().all()
	
		.when()
			.get("https://restapi.wcaquino.me/v2/users/?format=xml")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			
		
			;
		
	
	}
	
	@Test
	public void deveEnviarValorViaQueryViaParamentro() {
		
		given()
			.log().all()
			.queryParam("format", "xml")
	
		.when()
			.get("https://restapi.wcaquino.me/v2/users/")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML)
			.contentType(containsString("utf-8"))
		
			;
		
	
	}
	
	@Test
	public void deveEnviarValorViaQueryViaHeader() {
		
		given()
			.log().all()
			.accept(ContentType.JSON)//só aceita valor do tipo jason
	
		.when()
			.get("https://restapi.wcaquino.me/v2/users/")
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON)//pediu para verifcar a resposta
			
		
			;
		
	
	}
	
	

}