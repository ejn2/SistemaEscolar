package br.com.escola.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escola.models.TurmaModel;

public interface TurmaRepository extends JpaRepository<TurmaModel, Long>{

	Optional<TurmaModel> findByTitle(String title);
	
}