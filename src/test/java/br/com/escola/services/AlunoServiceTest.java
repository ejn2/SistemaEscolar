package br.com.escola.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.escola.dto.AlunoDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.TurmaException;
import br.com.escola.models.AlunoModel;
import br.com.escola.models.TurmaModel;
import br.com.escola.repository.AlunoRepository;
import br.com.escola.repository.TurmaRepository;
import br.com.escola.utils.ModelUtils;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

	@Mock
	private AlunoRepository alunoRepo;
	
	@Mock
	private TurmaRepository turmaRepo;
	
	@InjectMocks
	private AlunoService alunoService;
	
	
	private ModelMapper mapper = new ModelMapper();
	
	
	//====================[ Models ]====================//
	
	AlunoModel aluno = ModelUtils.alunoBuild();
	
	TurmaModel turma = ModelUtils.turmaBuild();
	
	
	
	//====================[ List - Test ]====================//
	
	@Test
	void whenTheMethodListIsCalled_ThenAListOfAlunosIsReturned() {
		
		when(this.alunoRepo.findAll())
			.thenReturn(Collections.singletonList(this.aluno));
		
		List<AlunoDTO> alunoList = this.alunoService.list();
		
		assertEquals(this.aluno.getName(), alunoList.get(0).getName());
		assertEquals(this.aluno.getUsername(), alunoList.get(0).getUsername());
		
		
	}
	
	
	
	//========================= [ Find by id - Test ] =========================//
	
	@Test
	void whenTheMethodFindByIdIsCalled_ThenAnAlunoIsReturned() throws NotFoundException {
		when(this.alunoRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.aluno));
			
		AlunoModel foundAluno = this.alunoService.findById(1L);
		
		assertEquals(this.aluno,  foundAluno);
		
	}
	
	
	
	//====================[ Save - Test ]====================//
	
	@Test
	void whenTheMethodSaveIsCalled_ThenAnAlunoIsSaved() throws AlreadyRegisteredException, NotFoundException, TurmaException {
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		when(this.alunoRepo.save(any(AlunoModel.class)))
			.thenReturn(this.aluno);
		
		AlunoModel savedAluno = this.alunoService.save(
				this.mapper.map(this.aluno, AlunoDTO.class)
			);
	
		assertEquals(this.aluno.getUsername(), savedAluno.getUsername());
		
	}
	
	
	
	//====================[ Update - Test ]====================//
	
	@Test
	void whenTheMethodUPDATEIsCalled_ThenAnAlunoIsUpdated() throws NotFoundException, AlreadyRegisteredException {
		when(this.alunoRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.aluno));
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		when(this.alunoRepo.save(any(AlunoModel.class)))
			.thenReturn(this.aluno);
		
		AlunoModel alunoUpdated = this.alunoService.update(1L, this.mapper.map(this.aluno, AlunoDTO.class));
		
		assertEquals(this.aluno, alunoUpdated);
		
		
	}
	
	
	
	//====================[ Delete - Test ]====================//
	
	@Test
	void whenTheMethodDELETEIsCalled_ThenAnAlunoIsDeleted() throws NotFoundException {
		when(this.alunoRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.aluno));
		
		doNothing().when(this.alunoRepo).deleteById(anyLong());
		
		this.alunoService.delete(1L);
		
		verify(this.alunoRepo, times(1)).deleteById(anyLong());
		
	}
	
	
}
