package br.com.escola.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.escola.models.MateriaModel;

public interface MateriaRepository extends JpaRepository<MateriaModel, Long>{

	Optional<MateriaModel> findByTitle(String title);
	
}
