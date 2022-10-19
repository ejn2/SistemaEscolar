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
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.escola.dto.TurmaDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.exceptions.ActionException;
import br.com.escola.models.TurmaModel;
import br.com.escola.repository.TurmaRepository;
import br.com.escola.utils.ModelUtils;

@ExtendWith(MockitoExtension.class)
public class TurmaServiceTest {

	@Mock
	private TurmaRepository turmaRepo;
	
	@InjectMocks
	private TurmaService turmaService;
	
	private ModelMapper mapper = new ModelMapper();
	
	//====================[ Models - Test ]====================//
	
	TurmaModel turma = ModelUtils.turmaBuild();
	
	
	//====================[ List - Test ]====================//
	
	@Test
	void whenTheMethodListIsCalled_ThenAListOfTurmaIsReturned() {
		
		when(this.turmaRepo.findAll())
			.thenReturn(List.of(this.turma));
		
		List<TurmaDTO> turmaList = this.turmaService.list();
		
		assertEquals(this.turma.getTitle(), turmaList.get(0).getTitle());
		
	}
	
	//====================[ find by id - Test ]====================//
	
	@Test
	void whenTheMethodFindByIdIsCalled_ThenAturmaIsReturned() throws AlreadyRegisteredException, NotFoundException {
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		assertThat(this.mapper.map(this.turma, TurmaDTO.class))
			.isEqualTo(this.turmaService.findById(this.turma.getId()));
		
	}
	
	//====================[ Save - Test ]====================//
	
	@Test
	void whenTheMethodSaveIsCalled_ThenATurmaIsSaved() throws AlreadyRegisteredException {
		
		when(this.turmaRepo.save(any(TurmaModel.class)))
			.thenReturn(this.turma);
		
		TurmaModel savedTurma = this.turmaService.save(
				this.mapper.map(turma, TurmaDTO.class)
				);
		
		assertEquals(this.turma.getTitle(), savedTurma.getTitle());
		
	}
	
	
	//====================[ Update - Test ]====================//
	
	
	@Test
	void whenTheMethodUPDATEIsCalled_ThenAMateriaIsUpdated() throws NotFoundException, AlreadyRegisteredException {
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		when(this.turmaRepo.save(any(TurmaModel.class)))
			.thenReturn(this.turma);
		
		TurmaModel updatedTurma = this.turmaService.update(1L, this.mapper.map(turma, TurmaDTO.class));
		
		assertEquals(this.turma, updatedTurma);
		
	}
	
	
	//====================[ Delete - Test ]====================//
	
	@Test
	void whenTheMethodDELETEIsCalled_ThenATurmaIsDeleted() throws NotFoundException, ActionException {
		
		when(this.turmaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.turma));
		
		doNothing().when(this.turmaRepo).deleteById(anyLong());
		
		this.turmaService.delete(1L);
		
		verify(this.turmaRepo, times(1)).deleteById(anyLong());
		
		
	}
	
}
