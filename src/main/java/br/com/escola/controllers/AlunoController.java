package br.com.escola.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PostMapping
	public AlunoDTO save(@RequestBody AlunoModel alunoModel) throws AlreadyRegisteredException, NotFoundException, TurmaException {
		return this.alunoService.save(alunoModel);
	}
	
}
