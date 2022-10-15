package br.com.escola.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Entity(name="turma")
@Getter @Setter
public class TurmaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonInclude(Include.NON_NULL)
	@Column(nullable = false, unique = true)
	private String title;
	
	@JsonIgnore
	@OneToMany(mappedBy = "turma")
	private List<ProfessorModel> professores = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "turma")
	private List<AlunoModel> alunos = new ArrayList<>();
	
}