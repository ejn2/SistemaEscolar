package br.com.escola.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.dto.TurmaDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.ActionException;
import br.com.escola.models.TurmaModel;
import br.com.escola.repository.TurmaRepository;

@Service
public class TurmaService {

	@Autowired
	private TurmaRepository turmaRepo;
	
	private ModelMapper mapper = new ModelMapper();
	
	
	//========================= [ List ] =========================//
	
	public List<TurmaDTO> list() {
		List<TurmaModel> turmaList = this.turmaRepo.findAll();
		
		return turmaList.stream().map((e) -> this.mapper.map(e, TurmaDTO.class)).collect(Collectors.toList());
	}
	
	
	
	//========================= [ Find by id ] =========================//
	
	public TurmaDTO findById(Long turmaId) throws NotFoundException {

		Optional<TurmaModel> foundTurma = this.turmaRepo.findById(turmaId);
		
		foundTurma.orElseThrow(
				() -> new NotFoundException("Turma não encontrada.")
			);
		
		return this.mapper.map(foundTurma.get(), TurmaDTO.class);
		
	}
	
	
	
	//========================= [ Save ] =========================//
	
	public TurmaModel save(TurmaDTO turmaDTO) throws AlreadyRegisteredException {
		
		this.checkIfTurmaAlreadyExists(turmaDTO.getTitle());
		
		return this.turmaRepo.save(
			this.mapper.map(turmaDTO, TurmaModel.class)
				);
		
	}
	
	
	
	//========================= [ Update ] =========================//
	
	public TurmaModel update(Long turmaId, TurmaDTO turmaDTO) throws NotFoundException, AlreadyRegisteredException {
		TurmaDTO foundTurma = this.findById(turmaId);
		
		if(!foundTurma.getTitle().toLowerCase().equals(turmaDTO.getTitle().toLowerCase())) {
			this.checkIfTurmaAlreadyExists(turmaDTO.getTitle());
		}
		
		TurmaModel updatedTurma = this.mapper.map(foundTurma, TurmaModel.class);
		updatedTurma.setTitle(turmaDTO.getTitle());
		
		return this.turmaRepo.save(updatedTurma);
		
	}
	
	
	
	//====================[ Delete - Test ]====================//
	
	public void delete(Long turmaId) throws NotFoundException, ActionException {
		TurmaDTO turmaDTO = this.findById(turmaId);
		
		if(!turmaDTO.getAlunos().isEmpty() || !turmaDTO.getProfessores().isEmpty()) {
			throw new ActionException("Transfira os Alunos e Professores antes de excluir a turma.");
		}
		
		this.turmaRepo.deleteById(turmaId);
		
	}
	
	
	private void checkIfTurmaAlreadyExists(String turmaTitle) throws AlreadyRegisteredException {
		
		if(this.turmaRepo.findByTitle(turmaTitle).isPresent()) {
			throw new AlreadyRegisteredException("Turma já registrada.");
		}
		
	}
	
	
	
}
