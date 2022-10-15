package br.com.escola.utils;

import br.com.escola.models.AlunoModel;
import br.com.escola.models.MateriaModel;
import br.com.escola.models.ProfessorModel;
import br.com.escola.models.TurmaModel;

public class ModelUtils {
	

	//========================= [ Turma ] =========================//
	
	public static TurmaModel turmaBuild() {
		
		TurmaModel turma = new TurmaModel();
		turma.setId(1L);
		turma.setTitle("Turma-1");
		
		return turma;
	}
	
	
	
	//========================= [ Professor ] =========================//
	
	public static ProfessorModel professorBuild() {
		
		ProfessorModel professor = new ProfessorModel();
		professor.setId(1L);
		professor.setName("Prof-1");
		professor.setUsername("prof-1");
		professor.setPassword("12345");
		professor.setMateria(ModelUtils.materiaBuild());
		professor.setTurma(ModelUtils.turmaBuild());
		
		return professor;
	}
	
	
	//========================= [ Aluno ] =========================//
	
	public static AlunoModel alunoBuild() {
		AlunoModel aluno = new AlunoModel();
		aluno.setId(1L);
		aluno.setName("Aluno-1");
		aluno.setUsername("aluno-1");
		aluno.setPassword("12345");
		aluno.setTurma(ModelUtils.turmaBuild());
		
		
		return aluno;
	}
	
	
	//========================= [ Materia ] =========================//
	
	public static MateriaModel materiaBuild() {
		MateriaModel materia = new MateriaModel();
		materia.setId(1L);
		materia.setTitle("Math");
		
		return materia;
	}
}
