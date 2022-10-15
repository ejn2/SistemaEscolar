package br.com.escola.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.models.MateriaModel;
import br.com.escola.repository.MateriaRepository;

@Service
public class MateriaService {

	@Autowired
	private MateriaRepository materiaRepo;
	
	public List<MateriaModel> list() {
		return this.materiaRepo.findAll();
	}
	
	
	public MateriaModel save(MateriaModel materia) throws AlreadyRegisteredException {
		this.checkIfMateriaAlreadyExists(materia.getTitle());
		
		materia.setTitle(materia.getTitle().toLowerCase());
		
		return this.materiaRepo.save(materia);
		
	}
	
	
	private void checkIfMateriaAlreadyExists(String materiaTitle) throws AlreadyRegisteredException {
		if(this.materiaRepo.findByTitle(materiaTitle.toLowerCase()).isPresent()) {
			throw new AlreadyRegisteredException("Matéria já cadastrada.");
		}
	}
	
	
}
