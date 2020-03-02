package smartbrain.controllers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

import smartbrain.entities.ImageResponse;
import smartbrain.entities.LoginPage;
import smartbrain.entities.RegisterPage;
import smartbrain.services.LoginService;
import smartbrain.services.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@MockBean
	LoginService loginService;
	
	@MockBean
	UserService userService;
	
	@Autowired
	MockMvc mockmvc;
	
	@AfterEach
	void teardown() {
		
		reset(loginService);
		reset(userService);
		
	}

	@Test
	void testCheckProfile() throws Exception {
		
		mockmvc
			.perform(get("/profile/{id}",1))
			.andExpect(status().isOk())
			.andExpect(content().string("found"));
	}
	

	
	
	@Test
	void testRegister() throws Exception {
		
		RegisterPage registerpage=new RegisterPage("bob","bob","bob@bob.com");
		
		mockmvc
		.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(registerpage)))
		.andExpect(status().isOk())
		.andExpect(content().string("ok"));
		
	}
	
	@Test
	void testRegisterNotValid() throws Exception {
		
		RegisterPage registerpage=new RegisterPage();
		
		mockmvc
		.perform(post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(registerpage)))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("\"error\""));
		
	}

	@Test
	void testSignin() throws Exception {
		
		LoginPage loginpage=new LoginPage("bob@bob.com","bobby");
		ImageResponse res=new ImageResponse();
		
		when(loginService.signin(any(LoginPage.class))).thenReturn(res);
		
		mockmvc
		.perform(post("/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(loginpage)))
		.andExpect(status().isOk())
		.andExpect(content().json(new Gson().toJson(res)));
				
				
		
	}
	
	@Test
	void testSigninNotValid() throws Exception {
		
		LoginPage loginpage=new LoginPage();
		ImageResponse res=new ImageResponse();
		
		when(loginService.signin(any(LoginPage.class))).thenReturn(res);
		
		mockmvc
		.perform(post("/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(loginpage)))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("\"not found\""));
				
				
		
	}

}
