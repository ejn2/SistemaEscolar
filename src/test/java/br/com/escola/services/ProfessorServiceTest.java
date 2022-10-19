package br.com.escola.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
	
	private ModelMapper mapper = new ModelMapper();
	
	
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
	
	
	
	//========================= [ Find by id - Test ] =========================//
	
	@Test
	void whenTheMethodFindByIdIsCalled_ThenAProfessorIsReturned() throws NotFoundException {
		when(this.professorRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.professor));
			
		
		ProfessorModel foundProfessor = this.professorService.findById(1L);
		
		assertThat(this.professor).isEqualTo(foundProfessor);
		
	}
	
	
	
	//=====================[ Save - Test ]=====================//
	
	@Test
	void whenTheMethodSaveIsCalled_ThenAProfessorIsSaved() throws AlreadyRegisteredException, NotFoundException, TurmaException {
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		when(this.materiaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.materia));
		
		when(this.professorRepo.save(Mockito.any(ProfessorModel.class)))
			.thenReturn(this.professor);
		
		ProfessorModel savedProfessor = this.professorService.save(
			this.mapper.map(this.professor, ProfessorDTO.class)
				);
		
		assertEquals(this.professor.getUsername(), savedProfessor.getUsername());
		
	}
	
	
	
	//=====================[ Update - Test ]=====================//
	
	@Test
	void whenTheMethodUPDATEIsCalled_TheAprofessorIsUpdated() throws NotFoundException, TurmaException, AlreadyRegisteredException {
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		when(this.materiaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.materia));
		
		when(this.professorRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.professor));
		
		when(this.professorRepo.save(any(ProfessorModel.class)))
			.thenReturn(this.professor);
		
		
		assertEquals(this.professor,
			this.professorService.update(1L, this.mapper.map(this.professor, ProfessorDTO.class))
				);
		
		
	}
	
	
	
	//=====================[ delete - Test ]=====================//
	
	@Test
	void whenTheMethodDELETEIsCalled_ThenAprofessorIsDeleted() throws NotFoundException {
		
		when(this.professorRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.professor));
		
		doNothing().when(this.professorRepo).deleteById(anyLong());
		
		this.professorService.delete(1L);
		
		verify(this.professorRepo, times(1)).deleteById(anyLong());
	}
}
