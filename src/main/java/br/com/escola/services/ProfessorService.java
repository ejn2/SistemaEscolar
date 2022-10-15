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
import br.com.escola.models.MateriaModel;
import br.com.escola.models.ProfessorModel;
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
	
	
	//========================= [ check if professor already exists ] =========================//
	
	private void checkIfProfessorAlreadyExists(String username) throws AlreadyRegisteredException {
		Optional<ProfessorModel> professor = this.professorRepo.findByUsername(username);
		
		if(professor.isPresent()) {
			throw new AlreadyRegisteredException("Nome de usuário inisponível");
		}
		
	}
	
	
	//========================= [ Save ] =========================//
	
	public ProfessorDTO save(ProfessorModel professorModel) throws AlreadyRegisteredException, NotFoundException {
		
		
		this.checkIfProfessorAlreadyExists(professorModel.getUsername());
		this.checkifTurmaExists(professorModel.getTurma().getId());
		
		this.checkIfMateriaExists(professorModel.getMateria().getId());
		this.checkIfProfessorMateriaAlreadyExists(professorModel.getTurma().getId(), professorModel.getMateria().getId());
		
		ProfessorModel savedProfessor = this.professorRepo.save(professorModel);
		
		return this.mapper.map(savedProfessor, ProfessorDTO.class);
		
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
