package smartbrain.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.prediction.Detection;
import smartbrain.entities.Image;
import smartbrain.entities.ImageResponse;
import smartbrain.entities.UserID;
import smartbrain.exceptions.ApiDownException;
import smartbrain.exceptions.BadRequestException;
import smartbrain.services.UserService;

@RestController
@CrossOrigin
public class ImageController {
	
	@Autowired
	UserService userService;

	@PostMapping("/imageurl")
	public String handleApiCall(@Valid @RequestBody Image url, BindingResult result) throws JsonProcessingException {

		if (result.hasErrors()) {
			throw new BadRequestException();
		}
		
		String data;
		
		try {

		final ClarifaiClient client = new ClarifaiBuilder("fa2ae7587782434c88412335ddbef736").buildSync();	
		Model<Detection> facemodel = client.getDefaultModels().faceDetectionModel();
		PredictRequest<Detection> request = facemodel.predict().withInputs(ClarifaiInput.forImage(url.getInput()));
		 data = request.executeSync().rawBody();
		}
		catch(Exception e) {
			throw new ApiDownException();
			
		}
	
		return data;

	}
	
	@PostMapping("/image")
	public ResponseEntity<ImageResponse> handleImage(@Valid @RequestBody UserID userid,BindingResult result){
		
		if (result.hasErrors()) {
			throw new BadRequestException();
		}
		System.out.println(userid.getId());
		ImageResponse res=userService.updateCount(userid.getId());
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
		
	}

}
