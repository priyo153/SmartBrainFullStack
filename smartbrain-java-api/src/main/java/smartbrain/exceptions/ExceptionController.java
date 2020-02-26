package smartbrain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler
	public ResponseEntity<String> notFoundException(NotFoundException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<String> apiDownException(ApiDownException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> badRequestException(BadRequestException e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<String> genericError(Exception e){
		System.out.println(e);
		return new ResponseEntity<>("\"error\"",HttpStatus.BAD_REQUEST);
	}

}
