import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;



public class Test_HelloWord {
	
	private String url = "https://reqres.in/api/users";
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		// fiz um requisição do tipo get
		//Response request = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		//obitive o objeto de aviladação
		//ValidatableResponse validacao = request.then();
		//validacao.statusCode(200); //pedi poara verificar se o status é 200
		
		//verifique se o status code é 200
		get("https://restapi.wcaquino.me/ola").then().statusCode(200);// pega direto
		
		//metodo fluente
		given()
		// pre-condições
		.when() //ações
			.get("https://restapi.wcaquino.me/ola").
		then() //acertivas 
		
			.statusCode(200);
		
	}
	@Test
	public void MatcheresHamcrest() {
		
		assertThat("Maria", Matchers.is("Maria"));
		//verificando o tipo
		assertThat(128,Matchers.isA(Integer.class));
		
		//criando lista 
		List<Integer> impares= Arrays.asList(1,3,5,7,9);
		//verificando o tamanho
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, containsInAnyOrder(1,9,5,7,3));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(5,3));
		
	}
	
	@Test
	public void validarBody() {
		// metodo fluente
		given()
			
		.when() // ações
				.get("https://restapi.wcaquino.me/ola")
		.then() // acertivas e coloco sempre na frente os mais critcos
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
		
		
	}


}
