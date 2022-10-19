package br.com.escola.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.dto.ProfessorDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.TurmaException;
import br.com.escola.models.MateriaModel;
import br.com.escola.models.ProfessorModel;
import br.com.escola.models.TurmaModel;
import br.com.escola.repository.MateriaRepository;
import br.com.escola.repository.ProfessorRepository;
import br.com.escola.repository.TurmaRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepo;
	
	@Autowired
	private TurmaRepository turmaRepo;
	
	@Autowired
	private MateriaRepository materiaRepo;
	
	private ModelMapper mapper = new ModelMapper();
	
	
	//========================= [ List ] =========================//
	
	public List<ProfessorDTO> list() {
		List<ProfessorModel> professorList = this.professorRepo.findAll();
		
		return professorList.stream().map((e) -> this.mapper.map(e, ProfessorDTO.class)).collect(Collectors.toList());
		
	}
	
	
	//========================= [ Find by id ] =========================//
	
	
	public ProfessorModel findById(Long professorId) throws NotFoundException {
		Optional<ProfessorModel> professor = this.professorRepo.findById(professorId);
		
		professor.orElseThrow(() -> new NotFoundException("Professor não encontrado."));
		
		return professor.get();
		
	}
	
	
	
	//========================= [ Save ] =========================//
	
	public ProfessorModel save(ProfessorDTO professorDTO) throws NotFoundException, TurmaException, AlreadyRegisteredException {
		
		this.checkSaveAndUpdate(professorDTO);
		this.checkIfProfessorAlreadyExists(professorDTO.getUsername());
		this.checkIfProfessorMateriaAlreadyExists(professorDTO.getTurma().getId(), professorDTO.getMateria().getId());
		
		return this.professorRepo.save(
			this.mapper.map(professorDTO, ProfessorModel.class)
				);
	}
	
	
	//========================= [ Update ] =========================//
	
	public ProfessorModel update(Long professorId, ProfessorDTO professorDTO) throws NotFoundException, TurmaException, AlreadyRegisteredException {
		
		this.checkSaveAndUpdate(professorDTO);
		ProfessorModel foundProfessor = this.findById(professorId);
		
		Optional<TurmaModel> foundTurma = this.turmaRepo.findById(professorDTO.getTurma().getId());
		foundTurma.orElseThrow(() -> new NotFoundException("Turma não registrada."));
		
		Optional<MateriaModel> foundMateria = this.materiaRepo.findById(professorDTO.getMateria().getId());
		foundMateria.orElseThrow(() -> new NotFoundException("Matéria não registrada."));
		
		if(!foundProfessor.getUsername().toLowerCase().equals(professorDTO.getUsername().toLowerCase())) {
			this.checkIfProfessorAlreadyExists(professorDTO.getUsername());
		}
		
		if(!foundProfessor.getMateria().getTitle().toLowerCase().equals(foundMateria.get().getTitle().toLowerCase())) {
			this.checkIfProfessorMateriaAlreadyExists(professorDTO.getTurma().getId(), professorDTO.getMateria().getId());
		}
		
		professorDTO.setId(professorId);
		professorDTO.setTurma(foundTurma.get());
		professorDTO.setMateria(foundMateria.get());
		
		return this.professorRepo.save(
			this.mapper.map(professorDTO, ProfessorModel.class)
				);
	}
	
	
	//========================= [ Delete ] =========================//
	
	public void delete(Long professorId) throws NotFoundException {
		this.findById(professorId);
		this.professorRepo.deleteById(professorId);
		
	}
	
	
	
	//========================= [ check if professor already exists ] =========================//
	
	private void checkIfProfessorAlreadyExists(String username) throws AlreadyRegisteredException {
		Optional<ProfessorModel> professor = this.professorRepo.findByUsername(username);
		
		if(professor.isPresent()) {
			throw new AlreadyRegisteredException("Nome de usuário inisponível");
		}
		
	}
	
	
	
	private void checkSaveAndUpdate(ProfessorDTO professorDTO) throws NotFoundException, TurmaException {
		
		this.checkifTurmaExists(professorDTO.getTurma().getId());
		
		this.checkTurmaProfessorLimit(professorDTO.getTurma().getId());
		
		this.checkIfMateriaExists(professorDTO.getMateria().getId());
	
	}
	
	
	private void checkTurmaProfessorLimit(Long professorId) throws TurmaException {
		
		if(this.turmaRepo.findById(professorId).get().getProfessores().size() >= 8) {
			throw new TurmaException("Não é possível registrar mais de 8 professores em uma turma.");
		}
	}
	
	
	
	private void checkifTurmaExists(Long turmaId) throws NotFoundException {
	
		this.turmaRepo.findById(turmaId)
			.orElseThrow(() -> new NotFoundException("Turma não registrada."));
		
	}
	
	
	
	private void checkIfMateriaExists(Long materiaId) throws NotFoundException {
		this.materiaRepo.findById(materiaId)
			.orElseThrow(() -> new NotFoundException("Matéria não registrada."));
	}
	
	
	
	private void checkIfProfessorMateriaAlreadyExists(Long turmaId, Long materiaId) throws AlreadyRegisteredException {
		
		List<ProfessorModel> professoresTurma = this.turmaRepo.findById(turmaId).get().getProfessores();
		
		Optional<MateriaModel> materia = this.materiaRepo.findById(materiaId);
		
		for(int i = 0; i< professoresTurma.size(); i++) {
			if(professoresTurma.get(i).getMateria().getTitle().toLowerCase().equals(materia.get().getTitle().toLowerCase())) {
				throw new AlreadyRegisteredException("Não é possível cadastrar dois professores com a mesma matéria em uma turma.");
			}
		}
		
		
	}
	
	
}
