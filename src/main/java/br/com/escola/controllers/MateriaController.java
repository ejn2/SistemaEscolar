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

import br.com.escola.exceptions.ActionException;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.models.MateriaModel;
import br.com.escola.services.MateriaService;

@RestController
@RequestMapping(path="/api/v1/materia")
public class MateriaController {

	@Autowired
	private MateriaService materiaService;
	
	@GetMapping
	public List<MateriaModel> list() {
		return this.materiaService.list();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MateriaModel save(@RequestBody MateriaModel materia) throws AlreadyRegisteredException { 
		return this.materiaService.save(materia);
	}
	
	
	@PutMapping(path = "/{materiaId}")
	public MateriaModel update(@PathVariable Long materiaId, @RequestBody MateriaModel materiaModel) throws NotFoundException, AlreadyRegisteredException {
		return this.materiaService.update(materiaId, materiaModel);
	}
	
	
	@DeleteMapping(path="/{materiaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long materiaId) throws NotFoundException, ActionException {
		this.materiaService.delete(materiaId);
	}
	
}
