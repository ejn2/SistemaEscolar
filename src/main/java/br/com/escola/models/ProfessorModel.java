package br.com.escola.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity(name="professor")
@Getter @Setter
public class ProfessorModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonIgnore
	private String password;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private TurmaModel turma;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private MateriaModel materia;
	//private List<RoleModel> roles = new ArrayList<>();
}
