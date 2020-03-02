package smartbrain.entities;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class Image {
	
	@NotBlank
	String input;
	
	public Image(){}
	
	

	public Image(String input) {
		super();
		this.input = input;
	}



	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	

}
