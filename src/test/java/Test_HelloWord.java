import org.junit.Test;

import io.restassured.RestAssured;

public class Test_HelloWord {
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		// fiz um requisi��o do tipo get
		//Response request = RestAssured.request(Method.GET, "https://restapi.wcaquino.me/ola");
		//obitive o objeto de avilada��o
		//ValidatableResponse validacao = request.then();
		//validacao.statusCode(200); //pedi poara verificar se o status � 200
		
		RestAssured.get("https://restapi.wcaquino.me/ola").then().statusCode(200);// pega direto
	}

}
