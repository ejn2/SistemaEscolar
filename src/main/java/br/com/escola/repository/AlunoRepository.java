package br.com.escola.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escola.models.AlunoModel;

public interface AlunoRepository extends JpaRepository<AlunoModel, Long>{
	
	Optional<AlunoModel> findByUsername(String username);
	
}
