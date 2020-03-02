package smartbrain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.prediction.Detection;
import smartbrain.entities.Image;
import smartbrain.entities.ImageResponse;
import smartbrain.entities.User;
import smartbrain.exceptions.ApiDownException;
import smartbrain.exceptions.NotFoundException;
import smartbrain.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User saveUser(User theUser) {
		
		return userRepository.save(theUser);
		
	}

	public ImageResponse updateCount(Long id) {
		
		User theUser=userRepository.getById(id);
		
		if(theUser==null) {
			throw new NotFoundException();
		}
		
		theUser.setEntries(theUser.getEntries()+1);
		userRepository.save(theUser);
		
		ImageResponse res=new ImageResponse();
		res.setId(theUser.getId());
		res.setEntries(theUser.getEntries());
		res.setName(theUser.getName());
		
		return res;
	}
	
	
	
	public ImageResponse getUserInfo(String email) {
		
		User theUser=userRepository.findByEmail(email);
		
		if(theUser==null) {
			throw new NotFoundException();
		}
		
		ImageResponse res=new ImageResponse();
		res.setId(theUser.getId());
		res.setEntries(theUser.getEntries());
		res.setName(theUser.getName());
		
		return res;
	}
	

	public User getUser(Long id) {
		User theUser=userRepository.getById(id);
		if(theUser==null) {
			throw new NotFoundException();
		}
		return theUser;
		
	}

public String faceDetectionApiService(Image url) {
		
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

}
