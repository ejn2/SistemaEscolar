package br.com.escola.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.escola.dto.TurmaDTO;
import br.com.escola.exceptions.AlreadyRegisteredException;
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
	
	
	
	//========================= [ Save ] =========================//
	
	public TurmaModel save(TurmaDTO turmaDTO) throws AlreadyRegisteredException {
		
		this.checkIfTurmaAlreadyExists(turmaDTO.getTitle());
		
		return this.turmaRepo.save(
			this.mapper.map(turmaDTO, TurmaModel.class)
				);
		
	}
	
	
	private void checkIfTurmaAlreadyExists(String turmaTitle) throws AlreadyRegisteredException {
		
		if(this.turmaRepo.findByTitle(turmaTitle).isPresent()) {
			throw new AlreadyRegisteredException("Turma já registrada.");
		}
		
	}
	
	
	
}
