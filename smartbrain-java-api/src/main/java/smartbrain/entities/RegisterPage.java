package smartbrain.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class RegisterPage {
	
	@NotBlank(message = "*name can not be blank")
	String name;
	@NotBlank(message="password can not be blank")
	String password;
	@NotBlank(message = "email can not be blank")
	@Email(message="email must be in valid format")
	String email;
	
	public RegisterPage(){}

	public RegisterPage(@NotBlank String name, @NotBlank String password, @NotBlank @Email String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}