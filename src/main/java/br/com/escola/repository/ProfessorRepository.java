package br.com.escola.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escola.models.ProfessorModel;

public interface ProfessorRepository extends JpaRepository<ProfessorModel, Long>{
	
	Optional<ProfessorModel> findByUsername(String username);
	
}
