package br.manocaio.rest.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import br.manocaio.rest.core.BaseTest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;

public class BarrigaTest extends BaseTest {
	private String TOKEN;
	
	@Before
	public void login() {
		// criando os dados para fazer o login 
		Map<String, String> login= new HashMap<>();
		login.put("email", "caiotjs@outlook.com");
		login.put("senha", "123456789");
		
	TOKEN =	given() //preciso enviar o dados de login e receber o token para trabalhar nas proximas requisisções
		
			.body(login)
		.when()
			.post("/signin")
		.then()
			.log().all()
			.statusCode(200) 
			.extract().path("token")
		
		;
	}
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		given()
		
		.when()
			.get("/contas")
		.then()
			.statusCode(401) //quando n sei quem é o usuário
		
		;
	}
	
	@Test
	public void DeveIncluirUmaCOntaCOmSucesso() {
	
	given()
		.header("Authorization","JWT" + TOKEN)
		.body("{\"nome\": \"conta do caio\"}")
	.when()
		.post("/contas")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(201)
	;
	}
	
	@Test
	public void DeveAlterarContaSucesso() {
	
	
	given()
		.header("Authorization","JWT" + TOKEN)
		.body("{\"nome\": \"conta alterada\"}")
	.when()
		.put("/contas/1114102")
	.then()
		.statusCode(200)
		.body("nome", is("conta alterada"))
	;
	}
	
	@Test
	public void InserirContaQueNaoDeveTercontasComMesmoNome() {
	
	given()
		.header("Authorization","JWT" + TOKEN)
		.body("{\"nome\": \"conta do caio\"}")
	.when()
		.post("/contas")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(400)
		.body("erro:", is("Já existe uma conta com esse nome! "))
		
	;
	}
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
	
		Movimentacao mov= GetMovimentacaoValida();
	given()
		.header("Authorization","JWT" + TOKEN)
		.body(mov)
	.when()
		.post("/transacoes")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(201)

	;
	
	
	}
	
	@Test
	public void deveValidarCamposObrigatóriosMovimentacao() {
	
	given()
		.header("Authorization","JWT" + TOKEN)
		.body("{}")
	.when()
		.post("/transacoes")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(400)
		.body("$",hasSize(8))//quero as mensagens 
		.body("msg", hasItems(
				//tenho que voltar aqui e pegar do meu console
				))
	;
	}
	
	@Test
	public void naoDeveInserirMovimentacaoComDataFutura() {
		Movimentacao mov= GetMovimentacaoValida();//fazendo movimentação valida para fazer uma futura
		mov.setData_transacao(""); //preciso refazer esse ponto
		given()
		.header("Authorization","JWT" + TOKEN)
		.body(mov)
	.when()
		.post("/transacoes")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(400)
		.body("$",hasSize(1))//quero as mensagens
		.body("msg", hasItems(""))//pega o valor
		 
	;
	}
	
	
	@Test
	public void naoDeveRemoverCOntaQueTemMovimentacao() {
	
		given()
		.header("Authorization","JWT" + TOKEN)
	
	.when()
		.delete("/contas/1114102")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(500)
		.body("constraint", is(""))//pegar o valor no console
	;
	
	}
	
	@Test
	public void deveCalcularSaldoCOntas() {
	
		given()
		.header("Authorization","JWT" + TOKEN)
	
	.when()
		.get("/saldo")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(200)
		.body("find{it.conta_id==pegaaa}.saldo", is("100.00"))//pegar o valor no console
	;
	
	}
	
	@Test
	public void removerMovimentacao() {
	
		given()
		.header("Authorization","JWT" + TOKEN)
	
	.when()
		.delete("transacoes/11588ouverconsole")//tenho que dizer oq vou enviar para salvar a conta
	.then()
		.statusCode(204)
		
	;
	
	}
	
	
	
	
	//metodo da movimentacao
	private Movimentacao GetMovimentacaoValida() {
		Movimentacao mov= new Movimentacao();
		mov.setConta_id(1114102);
		//mov.getUsuario_id(usuario_id);
		mov.setDescricao("Descriçaõ da movimentação");
		mov.setEnvolvido("Envolvido do Mov");
		mov.setTipo("REC");
		mov.setData_pagamento("10/05/2018");
		mov.setData_transacao("01/01/2020");
		mov.setValor(100f);
		mov.setStatus(true);
		return mov;
	}
	

	
}
