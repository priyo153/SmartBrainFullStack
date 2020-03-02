package smartbrain.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import smartbrain.entities.User;
import smartbrain.exceptions.NotFoundException;
import smartbrain.repositories.LoginRepository;
import smartbrain.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	LoginRepository loginRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	LoginService loginService;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	UserService userService;

	@Test
	void testSaveUser() {
		User theUser = new User();
		userService.saveUser(theUser);

		verify(userRepository).save(any(User.class));
	}

	@Test
	void testUpdateCount() {

		User theUser = new User();
		theUser.setId(1L);
		theUser.setEntries(1);
		theUser.setName("bob");

		when(userRepository.getById(anyLong())).thenReturn(theUser);
		ImageResponse res = userService.updateCount(1L);
		verify(userRepository).save(theUser);
		assertNotNull(res);

	}

	@Test
	void testUpdateCountNotValid() {

		User theUser = null;

		when(userRepository.getById(anyLong())).thenReturn(theUser);
		assertThrows(NotFoundException.class, () -> {
			userService.updateCount(1L);
		});

	}

	@Test
	void testGetUserInfo() {
		User theUser = new User();
		theUser.setId(1L);
		theUser.setEntries(1);
		theUser.setName("bob");
		when(userRepository.findByEmail(anyString())).thenReturn(theUser);
		ImageResponse res = userService.getUserInfo("em@em.com");
		assertNotNull(res);

	}

	@Test
	void testGetUserInfoNotValid() {
		User theUser = null;
		when(userRepository.findByEmail(anyString())).thenReturn(theUser);
		assertThrows(NotFoundException.class, () -> {
			userService.getUserInfo("em@em.com");
		});

	}

	@Test
	void testGetUser() {
		when(userRepository.getById(anyLong())).thenReturn(new User());
		User theUser = userService.getUser(1L);
		assertNotNull(theUser);
	}

	@Test
	void testGetUserNotValid() {
		when(userRepository.getById(anyLong())).thenReturn(null);
		assertThrows(NotFoundException.class, () -> {
			userService.getUser(1L);
		});
	}

}
