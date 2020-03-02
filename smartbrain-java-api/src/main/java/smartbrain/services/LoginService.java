package smartbrain.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import smartbrain.entities.ImageResponse;
import smartbrain.entities.Login;
import smartbrain.entities.LoginPage;
import smartbrain.entities.RegisterPage;
import smartbrain.entities.User;
import smartbrain.exceptions.BadRequestException;
import smartbrain.exceptions.NotFoundException;
import smartbrain.repositories.LoginRepository;
import smartbrain.repositories.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	LoginRepository loginRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void registerUser(RegisterPage registerpage){
		
		Login login=new Login();
		User user=new User();
		String hash=bCryptPasswordEncoder.encode(registerpage.getPassword());
		
		login.setEmail(registerpage.getEmail());
		login.setHash(hash);
		
		user.setEmail(registerpage.getEmail());
		user.setName(registerpage.getName());
		user.setEntries(0);
		user.setJoined(new Date(System.currentTimeMillis()));
		try {
		
		Login theLogin= loginRepository.save(login);
		User theUser=userRepository.save(user);
		}
		catch(Exception e){
			throw new BadRequestException();
		}
		
	}

	public ImageResponse signin(LoginPage loginpage) {
		String password = loginpage.getPassword();
		Login login = loginRepository.findByEmail(loginpage.getEmail());
		
		if (login == null) {

			throw new NotFoundException();
		}
		
		boolean pw_validate = bCryptPasswordEncoder.matches(password, login.getHash());
		
		if (!pw_validate) {

			throw new NotFoundException();
		}

		ImageResponse res = userService.getUserInfo(login.getEmail());

		return res;
	}
	
	
	

}
