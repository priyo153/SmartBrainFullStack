package smartbrain.exceptions;

public class BadRequestException extends RuntimeException {
	
	public BadRequestException(){
		super("\"error\"");
	}

}
