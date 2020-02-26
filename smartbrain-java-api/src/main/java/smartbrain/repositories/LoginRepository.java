package smartbrain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import smartbrain.entities.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

	Login save(Login login);

	Login getById(Long id);

	Login findByEmail(String email);

}
