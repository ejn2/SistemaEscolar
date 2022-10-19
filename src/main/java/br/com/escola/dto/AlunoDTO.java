package br.com.escola.dto;

import br.com.escola.models.TurmaModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlunoDTO {

	private Long id;
	private String name;
	private String username;
	private String password;
	private TurmaModel turma;
	
}
