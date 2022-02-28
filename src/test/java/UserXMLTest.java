import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UserXMLTest {
	
	public static RequestSpecification recSpec;
	public static ResponseSpecification resSpec;
	

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI= "https://restapi.wcaquino.me";
		//RestAssured.port=443;
		//RestAssured.basePath="/v2";
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.log(LogDetail.ALL);
		recSpec = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectStatusCode(200);
		resSpec = resBuilder.build();
		
		//definindo isso posso retirar tudo do given pois todos os tenstes vão herdar
		RestAssured.requestSpecification = recSpec;
		RestAssured.responseSpecification= resSpec;
		
	}
	
	@Test
	public void trabalhandoXML() {
		

		
		given()
			.spec(recSpec)
		.when()
			//.get("https://restapi.wcaquino.me/usersXML/3")
			.get("/usersXML/3")
		.then()
		//.statusCode(200)
		.spec(resSpec)
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
			.get("usersXML/")
		
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
			.get("usersXML/")
		
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
			.get("/usersXML/")
		
		.then()
			.body(hasXPath("count(/users/user)",is("3")))
			.body(hasXPath("/users/user[@id='1']")) //contem 1?
			.body(hasXPath("//user[@id='1']"))// vai descer até encontrar
			.body(hasXPath("//name[text()='Luizinho']/../../name", is("Ana Julia")))
			//.body(hasXPath("//name[text()='Ana Julia']", is("")))
			;
		
	}
	
}
