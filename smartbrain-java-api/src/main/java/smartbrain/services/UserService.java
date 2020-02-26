package smartbrain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smartbrain.entities.ImageResponse;
import smartbrain.entities.User;
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

}
