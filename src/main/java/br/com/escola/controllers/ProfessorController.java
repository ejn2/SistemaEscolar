package br.com.escola.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.escola.dto.ProfessorDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.TurmaException;
import br.com.escola.models.ProfessorModel;
import br.com.escola.services.ProfessorService;

@RestController
@RequestMapping(path = "/api/v1/professor")
public class ProfessorController {

	@Autowired
	ProfessorService professorService;
	
	@GetMapping
	public List<ProfessorDTO> list() {
		return this.professorService.list();
	}
	
	
	@GetMapping(path="/{professorId}")
	public ProfessorModel findById(@PathVariable Long professorId) throws NotFoundException {
		return this.professorService.findById(professorId);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProfessorModel save(@RequestBody ProfessorDTO professorDTO) throws AlreadyRegisteredException, NotFoundException, TurmaException {
		return this.professorService.save(professorDTO);
	}
	
	// **** Concertar BUG ao atualizar a materia no aluno. ****
	
	@PutMapping(path="/{professorId}")
	public ProfessorModel update(@PathVariable Long professorId, @RequestBody ProfessorDTO professorDTO) throws NotFoundException, TurmaException, AlreadyRegisteredException {
		return this.professorService.update(professorId, professorDTO);
	}
	
	
	@DeleteMapping(path="/{professorId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long professorId) throws NotFoundException {
		this.professorService.delete(professorId);
	}
	
}