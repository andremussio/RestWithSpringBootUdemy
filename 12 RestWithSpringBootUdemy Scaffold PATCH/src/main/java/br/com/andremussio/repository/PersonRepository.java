package br.com.andremussio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.andremussio.data.model.Person;

/*
 * A anotação @Repository e a extensão da classe JpaRepository fazem com que o JPA automaticamente disponibilize as operações básicas sobre a respectiva tabela:
 * - find
 * - save
 * - delete
 * 
 * JpaRepository<T, ID> - <T>: Tipo do repositório; <ID>: Tipo da chave do repositório
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Modifying
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
	void disablePerson(@Param("id") Long id);
	
}
