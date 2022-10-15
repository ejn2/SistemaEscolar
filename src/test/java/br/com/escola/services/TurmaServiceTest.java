package br.com.escola.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.escola.dto.TurmaDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
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
	
	//====================[ List - Test ]====================//
	
	@Test
	void whenTheMethodSaveIsCalled_ThenATurmaIsSaved() throws AlreadyRegisteredException {
		
		when(this.turmaRepo.save(Mockito.any(TurmaModel.class)))
			.thenReturn(this.turma);
		
		TurmaModel savedTurma = this.turmaService.save(
				this.mapper.map(turma, TurmaDTO.class)
				);
		
		assertEquals(this.turma.getTitle(), savedTurma.getTitle());
		
	}
	
	
}
