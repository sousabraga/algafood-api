package br.com.bsh.algafoodapi.jpa;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.bsh.algafoodapi.AlgafoodApiApplication;
import br.com.bsh.algafoodapi.domain.entity.Cozinha;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		Cozinha cozinha1 = new Cozinha("Brasileira");
		Cozinha cozinha2 = new Cozinha("Japonesa");
	
		cadastroCozinha.adicionar(cozinha1);
		cadastroCozinha.adicionar(cozinha2);
	}
}
