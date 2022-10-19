package br.com.escola.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.exceptions.ActionException;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.models.MateriaModel;
import br.com.escola.repository.MateriaRepository;

@Service
public class MateriaService {

	@Autowired
	private MateriaRepository materiaRepo;
	
	
	//====================[ List ]====================//
	
	public List<MateriaModel> list() {
		return this.materiaRepo.findAll();
	}
	
	
	//====================[ Save ]====================//
	
	public MateriaModel save(MateriaModel materia) throws AlreadyRegisteredException {
		this.checkIfMateriaAlreadyExists(materia.getTitle());
		
		materia.setTitle(materia.getTitle().toLowerCase());
		
		return this.materiaRepo.save(materia);
		
	}
	
	
	
	//====================[ Update ]====================//
	
	public MateriaModel update(Long materiaId, MateriaModel materiaModel) throws NotFoundException, AlreadyRegisteredException {
		
		MateriaModel foundMateria = this.findById(materiaId);
		
		if(!foundMateria.getTitle().toLowerCase().equals(materiaModel.getTitle().toLowerCase())) {
			this.checkIfMateriaAlreadyExists(materiaModel.getTitle());
		}
		
		foundMateria.setTitle(materiaModel.getTitle());
		
		return this.materiaRepo.save(foundMateria);
	}
	
	
	
	//====================[ Delete ]====================//
	
	public void delete(Long materiaId) throws NotFoundException, ActionException {
		MateriaModel foundMateria = this.findById(materiaId);
		
		if(!foundMateria.getProfessores().isEmpty()) {
			throw new ActionException("Erro: A matéria ainda está sendo usada por alguns professores.");
		}
		
		this.materiaRepo.deleteById(materiaId);
		
	}
	
	
	private MateriaModel findById(Long materiaId) throws NotFoundException {
		return this.materiaRepo.findById(materiaId)
			.orElseThrow(() -> new NotFoundException("Matéria não registrada."));
	}
	
	private void checkIfMateriaAlreadyExists(String materiaTitle) throws AlreadyRegisteredException {
		if(this.materiaRepo.findByTitle(materiaTitle.toLowerCase()).isPresent()) {
			throw new AlreadyRegisteredException("Matéria já cadastrada.");
		}
	}
	
	
}
