package smartbrain.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import smartbrain.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
	
	public User save(User u);

	public User getById(Long id);
	
	public User findByEmail(String email);
	
	

}
