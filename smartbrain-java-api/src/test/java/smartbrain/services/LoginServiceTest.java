package smartbrain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import smartbrain.entities.ImageResponse;
import smartbrain.entities.Login;
import smartbrain.entities.LoginPage;
import smartbrain.entities.RegisterPage;
import smartbrain.entities.User;
import smartbrain.exceptions.BadRequestException;
import smartbrain.exceptions.NotFoundException;
import smartbrain.repositories.LoginRepository;
import smartbrain.repositories.UserRepository;
@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
	
	@Mock
	LoginRepository loginRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	UserService userService;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	
	@InjectMocks
	LoginService loginService;

	@Test
	void testRegisterUser() {
		RegisterPage registerpage=new RegisterPage();
		loginService.registerUser(registerpage);
		
		verify(loginRepository).save(any(Login.class));
		verify(userRepository).save(any(User.class));
	}
	
	@Test
	void testRegisterUserNotValid() {
		RegisterPage registerpage = new RegisterPage();

		when(loginRepository.save(any(Login.class))).thenThrow(new RuntimeException());

		Exception e = assertThrows(BadRequestException.class, () -> {
			loginService.registerUser(registerpage);

		});

		assertEquals(e.getMessage(), "\"error\"");

	}

	@Test
	void testSignin() {
		
		LoginPage loginpage=new LoginPage("bob@bob.com","password");
		Login login=new Login();
		login.setEmail(loginpage.getEmail());
		login.setHash("mno");
		
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn("mno");
		when(loginRepository.findByEmail(anyString())).thenReturn(login);
		when(bCryptPasswordEncoder.matches(anyString(),anyString())).thenReturn(true);
		when(userService.getUserInfo(anyString())).thenReturn(new ImageResponse());
		
		ImageResponse res=loginService.signin(loginpage);
		
		verify(userService).getUserInfo(anyString());
		assertNotNull(res);

	}
	
	@Test
	void testSigninNotValidUserNotFound() {
		
		LoginPage loginpage=new LoginPage("abc@abc.com","abc");
		

		when(bCryptPasswordEncoder.encode(anyString())).thenReturn("mno");
		
		Exception e=assertThrows(NotFoundException.class,()->{
			
			loginService.signin(loginpage);
		});
		
		assertEquals(e.getMessage(),"\"not found\"");
		

	}
	
	@Test
	void testSigninNotValidPasswordIncorrect() {
		
		LoginPage loginpage=new LoginPage("abc@abc.com","abc");
		Login login=new Login();
		login.setHash("xyz");

		when(bCryptPasswordEncoder.encode(anyString())).thenReturn("mno");
		when(loginRepository.findByEmail(anyString())).thenReturn(login);
		when(bCryptPasswordEncoder.matches(anyString(),anyString())).thenReturn(false);
		
		Exception e=assertThrows(NotFoundException.class,()->{
			
			loginService.signin(loginpage);
		});
		
		assertEquals(e.getMessage(),"\"not found\"");
		

	}

}
