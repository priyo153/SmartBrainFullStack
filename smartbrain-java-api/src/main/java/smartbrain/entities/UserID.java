package smartbrain.entities;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class UserID {
	
	@NotNull
	Long id;
	
	public UserID(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
