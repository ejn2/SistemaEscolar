package br.com.escola.dto;

import br.com.escola.models.MateriaModel;
import br.com.escola.models.TurmaModel;
import lombok.Data;


@Data
public class ProfessorDTO {
	private Long id;
	private String name;
	private String username;
	private String password;
	private TurmaModel turma;
	private MateriaModel materia;
}