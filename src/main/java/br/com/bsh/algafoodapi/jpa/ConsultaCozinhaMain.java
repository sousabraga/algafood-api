package br.com.bsh.algafoodapi.jpa;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.bsh.algafoodapi.AlgafoodApiApplication;
import br.com.bsh.algafoodapi.domain.entity.Cozinha;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		//listarTeste(cadastroCozinha);
		
		buscarPorIdTeste(cadastroCozinha);
	}
	
	private static void listarTeste(CadastroCozinha cadastroCozinha) {
		cadastroCozinha.listar().forEach(cozinha -> {
			System.out.println(cozinha.getNome());
		});
	}
	
	private static void buscarPorIdTeste(CadastroCozinha cadastroCozinha) {
		var cozinha = cadastroCozinha.buscarPorId(1L);
		System.out.println(cozinha.getNome());
	}
}
