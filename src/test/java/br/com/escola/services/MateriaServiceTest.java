package br.com.escola.services;

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

import br.com.escola.exceptions.ActionException;
import br.com.escola.exceptions.AlreadyRegisteredException;
import br.com.escola.exceptions.NotFoundException;
import br.com.escola.models.MateriaModel;
import br.com.escola.repository.MateriaRepository;
import br.com.escola.utils.ModelUtils;

@ExtendWith(MockitoExtension.class)
public class MateriaServiceTest {

	@Mock
	private MateriaRepository materiaRepo;
	
	@InjectMocks
	private MateriaService materiaService;
	
	
	//====================[ Models - Test ]====================//
	
	MateriaModel materia = ModelUtils.materiaBuild();
	
	
	
	//====================[ List - Test ]====================//
	
	@Test
	void whenTheMethodListIsCalled_ThenAListOfMateriaIsReturned() {
		
		when(this.materiaRepo.findAll())
			.thenReturn(List.of(this.materia));
		
		List<MateriaModel> materiaList = this.materiaService.list();
		
		assertEquals(List.of(this.materia), materiaList);
		
		
	}
	
	
	
	//====================[ Save - Test ]====================//
	
	@Test
	void whenTheMethodSaveIsCalled_ThenAMateriaIsSaved() throws AlreadyRegisteredException {
				
		when(this.materiaRepo.save(Mockito.any(MateriaModel.class)))
			.thenReturn(this.materia);
		
		MateriaModel savedMateria = this.materiaService.save(this.materia);
		
		assertEquals(this.materia.getTitle(), savedMateria.getTitle());
			
	}
	
	
	//====================[ Update - Test ]====================//
	
	@Test
	void whenTheMethodUPDATEIsCalled_ThenAMateriaIsUpdated() throws NotFoundException, AlreadyRegisteredException {
		when(this.materiaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.materia));
		
		when(this.materiaRepo.save(any(MateriaModel.class)))
			.thenReturn(this.materia);
		
		MateriaModel updatedMateria = this.materiaService.update(1L, this.materia);
		
		assertEquals(this.materia, updatedMateria);
		
		
	}
	
	
	//====================[ Delete - Test ]====================//
	
	@Test
	void whenTheMethodDELETEIsCalled_ThenAMateriaIsDeleted() throws NotFoundException, ActionException {
		
		when(this.materiaRepo.findById(anyLong()))
			.thenReturn(Optional.of(this.materia));
		
		doNothing().when(this.materiaRepo).deleteById(anyLong());
		
		this.materiaService.delete(1L);
		
		verify(this.materiaRepo, times(1)).findById(anyLong());
			
		
		
	}
	
}
