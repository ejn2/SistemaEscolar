package br.com.escola.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.dto.AlunoDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.TurmaException;
import br.com.escola.models.AlunoModel;
import br.com.escola.models.TurmaModel;
import br.com.escola.repository.AlunoRepository;
import br.com.escola.repository.TurmaRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepo;
	
	@Autowired
	private TurmaRepository turmaRepo;
	
	private ModelMapper mapper = new ModelMapper();
	
	
	//========================= [ List ] =========================//
	
	public List<AlunoDTO> list() {
		
		return this.alunoRepo.findAll()
				.stream()
				.map(a -> this.mapper.map(a, AlunoDTO.class))
				.collect(Collectors.toList());
		
	}
	
	
	//========================= [ Find by id ] =========================//
	
	
	public AlunoModel findById(Long alunoId) throws NotFoundException {
		Optional<AlunoModel> aluno = this.alunoRepo.findById(alunoId);
		
		aluno.orElseThrow(() -> new NotFoundException("Aluno não encontrado."));
		
		return aluno.get();
		
	}
	
	
	//========================= [ Save ] =========================//
	
	public AlunoModel save(AlunoDTO alunoDTO) throws AlreadyRegisteredException, NotFoundException, TurmaException {
		this.checkIfAlunoAlreadyExists(alunoDTO.getUsername());
		this.checkifTurmaExists(alunoDTO.getTurma().getId());
		this.checkTurmaAlunoLimit(alunoDTO.getTurma().getId());
		
		return this.alunoRepo.save(
				this.mapper.map(alunoDTO, AlunoModel.class)
			);
 
	}
	
	
	//========================= [ Update ] =========================//
	
	public AlunoModel update(Long alunoId, AlunoDTO alunoDTO) throws NotFoundException, AlreadyRegisteredException {
		
		AlunoModel foundAluno = this.findById(alunoId);
		
		Optional<TurmaModel> foundTurma = this.turmaRepo.findById(alunoDTO.getTurma().getId());
		foundTurma.orElseThrow(() -> new NotFoundException("Turma não registrada."));
		
		if(!foundAluno.getUsername().toLowerCase().equals(alunoDTO.getUsername())) {
			this.checkIfAlunoAlreadyExists(alunoDTO.getUsername());
		}
		
		alunoDTO.setId(alunoId);
		alunoDTO.setTurma(foundTurma.get());
		
		return this.alunoRepo.save(
			this.mapper.map(alunoDTO, AlunoModel.class)
				);
	}
	
	
	//========================= [ Delete ] =========================//
	
	public void delete(Long alunoId) throws NotFoundException {
		this.findById(alunoId);
		this.alunoRepo.deleteById(alunoId);
		
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
