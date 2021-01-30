package br.com.andremussio.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.andremussio.exception.ExceptionResponse;
import br.com.andremussio.exception.InvalidJwtAuthenticationException;
import br.com.andremussio.exception.ResourceNotFoundException;

/*
 * @ControllerAdvice é uma especialização de @Component que permite manipular exceções 
 * através da aplicação inteira de forma global.
 * Pode ser entendida como um interceptador de exceções lançadas por métodos anotados com @RequestMapping.
 */
@ControllerAdvice

/*
 * @RestController é uma especialização de @Controller que indica se tratar de um controller que inclui um ResponseBody.
 * Internamente, esta anotação inclui @Controller e @ResponseBody.
 */
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class) //ExceptionHandler é um filter que, neste caso, está configurado para interceptar todas as exceções do tipo Exception
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class) //ExceptionHandler é um filter que, neste caso, está configurado para interceptar todas as exceções do tipo ResourceNotFoundException
	public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidJwtAuthenticationException.class) //ExceptionHandler é um filter que, neste caso, está configurado para interceptar todas as exceções do tipo InvalidJwtAuthenticationException
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
