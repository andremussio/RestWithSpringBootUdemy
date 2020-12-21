package br.com.andremussio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andremussio.exception.ResourceNotFoundException;
import br.com.andremussio.model.Person;
import br.com.andremussio.repository.PersonRepository;

/*
 * @Service marca a classe informando ao Spring que ela deverá ser gerenciada de forma a 
 * permitir que o Spring a injete automaticamente nos códigos que a utilizarem.
 * Os códigos que necessitarem dessa classe, deverão recebê-la por injeção de dependência, através o uso
 * da anotação @Autowired.
 */
@Service
public class PersonServices {

	@Autowired
	PersonRepository repository;

	public Person create(Person person) {
		return repository.save(person);
	}

	public Person update(Person person) {
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		entity.setPayDay(person.getPayDay());

		return repository.save(entity);
	}

	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.delete(entity);
	}

	public Person findById(Long id) {
		/*
		 * Método orElseThrow dispara uma exceção caso não seja encontrado o objeto
		 * informado no id. Será criada a exceção ResourceNotFoundException para tratar
		 * este caso, de forma a não retornar uma exceção genérica.
		 */

		/*
		 * Forma 1 de tratamento e retorno: Forma básica ResourceNotFoundException
		 * resourceNotFoundException = new
		 * ResourceNotFoundException("No records found for this ID"); Optional<Person>
		 * person = repository.findById(id); if(person.isEmpty()) throw
		 * resourceNotFoundException; return person.get();
		 */

		/*
		 * Forma 2 de tratamento e retorno: Com Supplier de exceção
		 * ResourceNotFoundException exceptionSupplier = new
		 * ResourceNotFoundException("No records found for this ID"); return
		 * repository.findById(id).orElseThrow((Supplier<? extends
		 * ResourceNotFoundException>) exceptionSupplier);
		 */

		/*
		 * Forma 3 de tratamento e retorno: Com Lambda
		 */
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	}

	public List<Person> findAll() {
		return repository.findAll();
	}

}
