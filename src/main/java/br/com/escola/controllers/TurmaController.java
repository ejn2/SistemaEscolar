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

import br.com.escola.dto.TurmaDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.ActionException;
import br.com.escola.models.TurmaModel;
import br.com.escola.services.TurmaService;

@RestController
@RequestMapping(path = "/api/v1/turma")
public class TurmaController {
	
	@Autowired
	private TurmaService turmaService;
	
	
	@GetMapping
	public List<TurmaDTO> show() {
		return this.turmaService.list();
	}
	
	
	@GetMapping(path="/{turmaId}")
	public TurmaDTO findById(@PathVariable Long turmaId) throws NotFoundException {
		return this.turmaService.findById(turmaId);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TurmaModel save(@RequestBody TurmaDTO turmaDTO) throws AlreadyRegisteredException {
		return this.turmaService.save(turmaDTO);
	}
	
	
	@PutMapping(path="/{turmaId}")
	public TurmaModel update(@PathVariable Long turmaId ,@RequestBody TurmaDTO turmaDTO) throws NotFoundException, AlreadyRegisteredException {
		return this.turmaService.update(turmaId, turmaDTO);
		
	}
	
	
	@DeleteMapping(path="/{turmaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long turmaId) throws NotFoundException, ActionException {
		this.turmaService.delete(turmaId);
	}
	
	
}
