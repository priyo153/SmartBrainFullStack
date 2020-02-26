package smartbrain.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column
	@NotBlank(message = "name can not be blank")
	String name;
	@Column
	@NotBlank(message = "email can not be blank")
	@Email(message="email must be in valid format")
	String email;
	@Column
	@NotNull(message="entries can not be null")
	Integer entries;
	@Column
	@NotNull(message="joined can not be null")
	Date joined;

	

	public User() {
	}

	public User(Long id, @NotBlank(message = "name can not be blank") String name,
			@NotBlank(message = "email can not be blank") @Email String email, @NotNull Integer entries,
			@NotNull Date joined, Login login) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.entries = entries;
		this.joined = joined;
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getEntries() {
		return entries;
	}

	public void setEntries(Integer entries) {
		this.entries = entries;
	}

	public Date getJoined() {
		return joined;
	}

	public void setJoined(Date joined) {
		this.joined = joined;
	}



}
