package br.com.escola.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TurmaDTO {

	private Long id;
	private String title;
	private List<ProfessorDTO> professores = new ArrayList<>();
	private List<AlunoDTO> alunos = new ArrayList<>();
}
