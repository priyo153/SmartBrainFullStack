package smartbrain.exceptions;

public class NotFoundException extends RuntimeException {
	
	public NotFoundException(){
		super("\"not found\"");
	}

}
