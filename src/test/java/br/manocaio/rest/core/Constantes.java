package br.manocaio.rest.core;

import io.restassured.http.ContentType;

public interface Constantes {
	String APP_BASE_URL ="http://barrirest.wcaquino.me";
	Integer APP_PORT= 80;
	String APP_BASE_PATH="";
	
	ContentType APP_CONTENTE_TYPE = ContentType.JSON;
	
	Long MAX_TIMEOUT= 5000l;
	
	
	
}
