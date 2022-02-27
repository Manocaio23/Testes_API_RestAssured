import static io.restassured.RestAssured.*;
import static junit.framework.Assert.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.Test;

import io.restassured.RestAssured;
import junit.framework.Assert;

public class UserXMLTest {

	@Test
	public void trabalhandoXML() {
		given()
		
		.when()
			.get("https://restapi.wcaquino.me/usersXML/3")
		
		.then()
		.statusCode(200)
		.rootPath("user")// caminho da raiz principal 
		.body("name", is("Ana Julia"))
		.body("@id", is("3"))//referenciando um atributo
		.body("filhos.name.size()",is(2))//pedindo o tamanho
		.body("filhos.name[0]", is("Zezinho"))
		.body("filhos.name[1]", is("Luizinho"))
		.body("filhos.name", hasItems("Zezinho"))//contem esse cara?
		.body("filhos.name", hasItems("Zezinho","Luizinho"))
		
		;
	}
	
	@Test
	public void PesquisasAvançadas() {
		given()
		
		.when()
			.get("https://restapi.wcaquino.me/usersXML/")
		
		.then()
		.statusCode(200)
		.body("users.user.size()", is(3))
		.body("users.user.findAll{it.age.toInteger()<=25}.size()", is(2))
		.body("users.user.@id",hasItems("1","2","3"))
		.body("users.user.find{it.age == 25}.name", is("Maria Joaquina"))
		
		;
	}
	

	@Test
	public void UnindoJavaComXML() {
		Object nome = given()
		
		.when()
			.get("https://restapi.wcaquino.me/usersXML/")
		
		.then()
			.extract().path("users.user.name.findAll{it.toString().startsWith('Maria')}");//vai pegar o valor e colocar dentro de uma variavel 
			//.extract().path("users.user.name.findAll{it.toString().contains('n')}"); 
		;
		
		System.out.println(nome.toString());
		assertEquals("Maria Joaquina", nome);
		
	}
	
	@Test
	public void PesquisaComXPath() {
		given()
		
		.when()
			.get("https://restapi.wcaquino.me/usersXML/")
		
		.then()
			.body(hasXPath("count(/users/user)",is("3")))
			.body(hasXPath("/users/user[@id='1']")) //contem 1?
			.body(hasXPath("//user[@id='1']"))// vai descer até encontrar
			.body(hasXPath("//name[text()='Luizinho']/../../name", is("Ana Julia")))
			;
		
	}
	
}
