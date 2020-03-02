package smartbrain.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import smartbrain.entities.Image;
import smartbrain.entities.ImageResponse;
import smartbrain.entities.UserID;
import smartbrain.services.UserService;

@WebMvcTest(ImageController.class)
class ImageControllerTest {
	
	@MockBean
	UserService userService;

	@Autowired
	MockMvc mockmvc;
	
	@BeforeEach
	void setup() {
		
	}
	
	@AfterEach
	void teardown() {
		
		reset(userService);
		
	}

	@Test
	void testHandleApiCall() throws Exception {
		Image img=new Image();
		img.setInput("url");
		when(userService.faceDetectionApiService(any(Image.class))).thenReturn("json");
		mockmvc
			.perform(post("/imageurl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(img)))
			.andExpect(status().isOk())
			.andExpect(content().string("json"));
	}
	
	@Test
	void testHandleApiCallNotValid() throws Exception {
		Image img=new Image();
		img.setInput("");
		when(userService.faceDetectionApiService(any(Image.class))).thenReturn("json");
		mockmvc
			.perform(post("/imageurl")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(img)))
			.andExpect(status().isBadRequest())
			.andExpect(content().string("\"error\""));
	}
	
	
	@Test
	void testHandleImage() throws Exception {
		
		UserID userid=new UserID();
		userid.setId(1L);
		
		
		ImageResponse imgresponse = new ImageResponse();
		when(userService.updateCount(anyLong())).thenReturn(imgresponse);
		
		mockmvc.perform(post("/image")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(userid)))
				.andExpect(status().isOk())
				.andExpect(content().json(new Gson().toJson(imgresponse)));
			
	}
	
	@Test
	void testHandleImageNotValid() throws Exception {
		
		UserID userid=new UserID();
		userid.setId(null);
		
		
		ImageResponse imgresponse = new ImageResponse();
		when(userService.updateCount(anyLong())).thenReturn(imgresponse);
		
		mockmvc.perform(post("/image")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(userid)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("\"error\""));
			
	}

}
