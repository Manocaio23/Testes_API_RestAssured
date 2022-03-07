import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.hamcrest.xml.HasXPath;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class HTML {
	
	@Test
	public void deveFazerBuscasHTML() {
		given()
			.log().all()
			
		.when()
			.get("https://restapi.wcaquino.me/v2/users")
		
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML) //é pra vim um html
			.body("html.body.div.table.tbody.tr.size()", is(3))//verificando a quantidade de linhas na tabela 
			.body("html.body.div.table.tbody.tr[1].td[2]", is("25"))
			.appendRootPath("html.body.div.table.tbody")// ja estou nesse caminho
			.body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"))//verificar se essa linha começa com 2
			
			;
	}
	
	@Test
	public void deveFazerBuscasComXpathEMHTML() {
		given()
			.log().all()
			
		.when()
			.get("https://restapi.wcaquino.me/v2/users?format=clean")
		
		.then()
			.log().all()
			.statusCode(200)
			.body(hasXPath("count(//table/tr)",is("4")))
			;
	}
}
