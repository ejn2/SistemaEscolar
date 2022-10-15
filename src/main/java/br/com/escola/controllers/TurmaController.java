package br.com.escola.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.escola.dto.TurmaDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
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
	
	@PostMapping
	public TurmaModel save(@RequestBody TurmaDTO turmaDTO) throws AlreadyRegisteredException {
		return this.turmaService.save(turmaDTO);
	}
	
}
