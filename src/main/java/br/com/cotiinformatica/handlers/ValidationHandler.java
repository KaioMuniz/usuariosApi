package br.com.cotiinformatica.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<String> errorHandler(MethodArgumentNotValidException e) {

		var errors = new ArrayList<String>();
		
		for(var error : e.getBindingResult().getFieldErrors())
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		
		for(var error : e.getBindingResult().getGlobalErrors())
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		
		Collections.sort(errors);
		
		return errors;
	}

}
