package smartbrain.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import smartbrain.entities.ImageResponse;
import smartbrain.entities.LoginPage;
import smartbrain.entities.RegisterPage;
import smartbrain.exceptions.BadRequestException;
import smartbrain.exceptions.NotFoundException;
import smartbrain.services.LoginService;
import smartbrain.services.UserService;

@RestController
@RequestMapping
@CrossOrigin
public class UserController {

	@Autowired
	LoginService loginService;

	@Autowired
	UserService userService;
	

	@GetMapping("/profile/{id}")
	public ResponseEntity<String> checkProfile(@PathVariable Long id) {

		userService.getUser(id);
		return new ResponseEntity<>("found", HttpStatus.OK);

	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterPage registerpage, BindingResult result) {

		if (result.hasErrors()) {
			throw new BadRequestException();

		}

		loginService.registerUser(registerpage);

		return new ResponseEntity<>("ok", HttpStatus.OK);

	}

	@PostMapping("/signin")
	public ResponseEntity<ImageResponse> signin(@Valid @RequestBody LoginPage loginpage, BindingResult result) {

		if (result.hasErrors()) {
		
			throw new NotFoundException();

		}

		ImageResponse res = loginService.signin(loginpage);

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
