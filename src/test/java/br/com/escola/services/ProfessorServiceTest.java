package br.com.escola.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.escola.dto.ProfessorDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.models.MateriaModel;
import br.com.escola.models.ProfessorModel;
import br.com.escola.models.TurmaModel;
import br.com.escola.repository.MateriaRepository;
import br.com.escola.repository.ProfessorRepository;
import br.com.escola.repository.TurmaRepository;
import br.com.escola.utils.ModelUtils;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

	@Mock
	private ProfessorRepository professorRepo;
	
	@Mock
	private TurmaRepository turmaRepo;
	
	@Mock
	private MateriaRepository materiaRepo;
	
	@InjectMocks
	private ProfessorService professorService;
	
	
	//=====================[Models]=====================//
	
	ProfessorModel professor = ModelUtils.professorBuild();
	TurmaModel turma = ModelUtils.turmaBuild();
	MateriaModel materia = ModelUtils.materiaBuild();
	
	
	//=====================[ List - Test ]=====================//
	
	
	@Test
	void whenTheMethodListIsCalled_ThenAListOfProfessorIsReturned() {
		
		when(this.professorRepo.findAll())
			.thenReturn(List.of(this.professor));
		
		List<ProfessorDTO> professorList = this.professorService.list();
		
		assertEquals(this.professor.getUsername(), professorList.get(0).getUsername());
		
	}
	
	
	//=====================[ Save - Test ]=====================//
	
	@Test
	void whenTheMethodSaveIsCalled_ThenAProfessorIsSaved() throws AlreadyRegisteredException, NotFoundException {
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		when(this.materiaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.materia));
		
		when(this.professorRepo.save(Mockito.any(ProfessorModel.class)))
			.thenReturn(this.professor);
		
		ProfessorDTO savedProfessor = this.professorService.save(this.professor);
		
		assertEquals(this.professor.getUsername(), savedProfessor.getUsername());
		
	}
	
}
