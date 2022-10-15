package br.com.escola.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.dto.AlunoDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.TurmaException;
import br.com.escola.models.AlunoModel;
import br.com.escola.repository.AlunoRepository;
import br.com.escola.repository.TurmaRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepo;
	
	@Autowired
	private TurmaRepository turmaRepo;
	
	private ModelMapper mapper = new ModelMapper();
	
	public List<AlunoDTO> list() {
		
		return this.alunoRepo.findAll()
				.stream()
				.map(a -> this.mapper.map(a, AlunoDTO.class))
				.collect(Collectors.toList());
		
	}
	
	
	public AlunoDTO save(AlunoModel alunoModel) throws AlreadyRegisteredException, NotFoundException, TurmaException {
		this.checkIfAlunoAlreadyExists(alunoModel.getUsername());
		this.checkifTurmaExists(alunoModel.getTurma().getId());
		this.checkTurmaAlunoLimit(alunoModel.getTurma().getId());
		
		AlunoModel savedAluno = this.alunoRepo.save(alunoModel);
		
		return this.mapper.map(savedAluno, AlunoDTO.class);

	}
	
	private void checkTurmaAlunoLimit(Long turmaId) throws TurmaException {
		if(this.turmaRepo.findById(turmaId).get().getAlunos().size() >= 30) {
			throw new TurmaException("Não é possível registrar mais de 30 alunos em uma turma.");
		}
	}
	

	private void checkIfAlunoAlreadyExists(String username) throws AlreadyRegisteredException {
		if(this.alunoRepo.findByUsername(username).isPresent()) {
			throw new AlreadyRegisteredException("Nome de usuario indisponível.");
		}
	}
	
	
	private void checkifTurmaExists(Long turmaId) throws NotFoundException {
		
		this.turmaRepo.findById(turmaId)
			.orElseThrow(() -> new NotFoundException("Turma não registrada."));
		
	}
	
}
