import static io.restassured.RestAssured.*;
import static junit.framework.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;

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
	
	@Test
	public void SegundoNivel() {
given()
		
		.when()
			.get("https://restapi.wcaquino.me/users/2")
		.then()
		.statusCode(200)
		.body("id", is(2))
		.body("name", containsString("Joaquina"))
		.body("endereco.rua", is("Rua dos bobos"))
		
		;
	}
	
	@Test 
	public void verificarLista() {
		given()
	
		.when()
			.get("https://restapi.wcaquino.me/users/3")
		.then()
			.statusCode(200)
			.body("id", is(3))
			.body("name", containsString("J�lia"))
			.body("filhos", hasSize(2))//2objetos dentro ve3rificar tamanho
			.body("filhos[0].name", is("Zezinho"))// indecsado por array
			.body("filhos[1].name", is("Luizinho"))
			.body("filhos.name",hasItems("Zezinho"))
		;
	}
	
	@Test
	public void verificaErro() {
		given()
		
		.when()
			.get("https://restapi.wcaquino.me/users/4")
		.then()
			.statusCode(404)
			.body("error",is("Usu�rio inexistente"));
			
		;
	}
	
	@Test
	public void verificarListaRaiz() {
		given()
		
		.when()
			.get("https://restapi.wcaquino.me/users/")
		.then()
			.statusCode(200)
			.body("$",hasSize(3))
			.body("name",hasItems("Jo�o da Silva","Maria Joaquina","Ana J�lia"))
			.body("age[1]", is(25))
			.body("filhos.name",hasItems(Arrays.asList("Zezinho","Luizinho")))//uma lista contendo o zezinho
			
			
			
			;
	}
	
	@Test
	public void verificacoesAvanadas() {
	given()
		
		.when()
			.get("https://restapi.wcaquino.me/users/")
		.then()
			.statusCode(200)
			.body("$",hasSize(3))
			.body("age.findAll{it <= 25}.size()", is(2))//quantos usuario existem at� 25 anos
			.body("age.findAll{it <= 25 && it > 20}.size()", is(1))//usuaruio que tem mais de 20 e at� 25 
			.body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
			.body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina","Ana J�lia"))//verificar todos os elementos que contem n
			.body("age.collect{it*2}", hasItems(60,50,40))
			.body("id.max()", is(3))
			.body("salary.min()", is(1234.5678f))
			.body("salary.findAll{it != null}.sum()", is(closeTo(3734.5678f, 0.001)))
			.body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d),lessThan(5000d)))
			
			;
	}
	
	@Test
	public void unirJsonPathComJava() {
		ArrayList<String> name=
		given()
		
		.when()
			.get("https://restapi.wcaquino.me/users/")
		.then()
			.statusCode(200)
			.extract().path("name.findAll{it.startsWith('Maria')}")//vai me dar uma lsita de string
			;
		Assert.assertEquals(1,name.size());
		Assert.assertTrue(name.get(0).equalsIgnoreCase("Maria Joaquina"));
		Assert.assertEquals(name.get(0).toUpperCase(),"maria joaquina".toUpperCase());
	}
	}
