package br.com.escola.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.escola.dto.ProfessorDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
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
	
	@PostMapping
	public ProfessorDTO save(@RequestBody ProfessorModel professorModel) throws AlreadyRegisteredException, NotFoundException {
		return this.professorService.save(professorModel);
	}
	
}
