package br.com.escola.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.escola.models.AlunoModel;
import br.com.escola.models.ProfessorModel;
import lombok.Data;

@Data
public class TurmaDTO {

	private Long id;
	private String title;
	private List<ProfessorModel> professores = new ArrayList<>();
	private List<AlunoModel> alunos = new ArrayList<>();
}