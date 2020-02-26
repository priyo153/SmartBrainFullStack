package smartbrain.exceptions;

public class ApiDownException extends RuntimeException {

	public ApiDownException() {
		super("unable to work with API");
	}

}
