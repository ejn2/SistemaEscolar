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

import br.com.escola.dto.AlunoDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.TurmaException;
import br.com.escola.models.AlunoModel;
import br.com.escola.services.AlunoService;

@RestController
@RequestMapping(path = "/api/v1/aluno")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;
	
	@GetMapping
	public List<AlunoDTO> list() {
		return this.alunoService.list();
	}
	
	
	@GetMapping(path="/{alunoId}")
	public AlunoModel findById(@PathVariable Long alunoId) throws NotFoundException {
		return this.alunoService.findById(alunoId);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AlunoModel save(@RequestBody AlunoDTO alunoDTO) throws AlreadyRegisteredException, NotFoundException, TurmaException {
		return this.alunoService.save(alunoDTO);
	}
	
	
	@PutMapping(path="/{alunoId}")
	public AlunoModel update(@PathVariable Long alunoId, @RequestBody AlunoDTO alunoDTO) throws NotFoundException, AlreadyRegisteredException {
		return this.alunoService.update(alunoId, alunoDTO);
		
	}
	
	
	@DeleteMapping(path="/{alunoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long alunoId) throws NotFoundException {
		this.alunoService.delete(alunoId);
	}
	
}
