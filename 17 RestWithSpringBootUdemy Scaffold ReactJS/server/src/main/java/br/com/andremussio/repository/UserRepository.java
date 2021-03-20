package br.com.andremussio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.andremussio.data.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/*
	 * Esta classe está sendo criada com uso de JPQL, para demonstrar que há outra forma de fazer além do uso direto do Spring Data.
	 */
	
	@Query("SELECT u FROM User u WHERE u.userName = :userName")
	User findByUsername(@Param("userName") String userName);
}
