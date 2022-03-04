import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jdk.internal.net.http.common.Log;

public class VerbosTest {
	
	@Test
	public void deveSalvarUser() {
		given()
		.log().all()
		.contentType("application/json") //onteudo que estou enviando para dizer que é um objeto json
		.body("{ \"name\": \"Mano caio\",\"age\": 25}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Mano caio"))
			.body("age", is(25))
		
		;
		
	}
	
	@Test
	public void NaodeveSalvarUserSemNOme() {//estou testando que nao deve aceitar
		given()
		.log().all()
		.contentType("application/json") //onteudo que estou enviando para dizer que é um objeto json
		.body("{ \"age\": 25}")
		.when()
			.post("https://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400) //não quero que ele insira
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"))
		;
	}
	
	@Test
	public void deveSalvarUserXML() {
		given()
		.log().all()
		.contentType(ContentType.XML) //onteudo que estou enviando para dizer que é um objeto json
		.body("	<user><name>Mano caio</name><age>25</age></user>")
		.when()
			.post("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Mano caio"))
			.body("user.age", is("25"))
		
		;
		

	}
	@Test
	public void deveAlterarUser() {// o post deve ser passado na generica
		given()
		.log().all()
		.contentType("application/json") 
		.body("{ \"name\": \"Mano caio\",\"age\": 25}")
		.when()
			.put("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Mano caio"))
			.body("age", is(25))
			
			
		
		;
		
	}
	
	@Test
	public void deveCustomizarURL() {// o post deve ser passado na generica
		given()
		.log().all()
		.contentType("application/json") 
		.body("{ \"name\": \"Mano caio\",\"age\": 25}")
		.pathParam("entidade", "users")
		.pathParam("userId", 1)
		.when()
			.put("https://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Mano caio"))
			.body("age", is(25))
		;
		
	}
	
	@Test
	public void removerUser() {
		given()
			.log().all()
			
		.when()
			.delete("https://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(204)
		;

		
	}
	
	@Test
	public void NãoremoverUserInexistente() {
		given()
			.log().all()
			
		.when()
			.delete("https://restapi.wcaquino.me/users/1000")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
			
		;

		
	}
	
}



