package br.com.escola.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TurmaException extends Exception {

	private static final long serialVersionUID = 1L;

	public TurmaException(String message) {
		super(message);
	}
	
}