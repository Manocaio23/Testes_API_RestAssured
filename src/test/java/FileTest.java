import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

public class FileTest {
	
	@Test
	public void ObrigarEnviarArquivo() {
		
		given()
		 	.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) 
			.body("error", is("Arquivo não enviado"))
		;
	}

	
	@Test
	public void FazerUploadDoArquivo() {
		
		given()
		 	.log().all()
		 	.multiPart("arquivo", new File("src/main/resources/s.png"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)//sucesso 
			.body("name", is("s.png"))
		;
	}
	
	@Test
	public void NaoFazerUploadDoArquivoGrande() {
		
		given()
		 	.log().all()
		 	.multiPart("arquivo", new File("src/main/resources/sd.pdf"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(5000L))// passar de cinco da erro 
			.statusCode(413) 
			
		;
	}
	
	
	@Test
	public void DeveBaixarArquivo() throws IOException {
		
		byte[] image = given() //array de byte representando
		 	.log().all()
		 	
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()
			.statusCode(200) 
			.extract().asByteArray() //deve ir para uma variavel byte
		;
		
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		Assert.assertThat(imagem.length(), lessThan(10000L));//verificar que o tamanho da imagem é menor que 10000
		
	}
}
