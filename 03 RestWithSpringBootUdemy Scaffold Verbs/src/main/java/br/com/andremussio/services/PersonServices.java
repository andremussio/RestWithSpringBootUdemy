package br.com.andremussio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.com.andremussio.model.Person;

/*
 * @Service marca a classe informando ao Spring que ela deverá ser gerenciada de forma a 
 * permitir que o Spring a injete automaticamente nos códigos que a utilizarem.
 * Os códigos que necessitarem dessa classe, deverão recebê-la por injeção de dependência, através o uso
 * da anotação @Autowired.
 */
@Service
public class PersonServices {

	private final AtomicLong counter = new AtomicLong();

	public Person create(Person person) {
		person.setId(counter.incrementAndGet());
		return person;
	}

	public Person update(Person person) {
		return person;
	}

	public void delete(String id) {
	}

	public Person findById(String id) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("André");
		person.setLastName("Mussio");
		person.setAddress("São Paulo - SP");
		person.setGender("Male");

		return person;
	}

	public List<Person> findAll() {
		List<Person> listPerson = new ArrayList<Person>();

		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			listPerson.add(person);
		}

		return listPerson;
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Person Name " + i);
		person.setLastName("Last Name " + i);
		person.setAddress("Address in Brazil " + i);
		person.setGender("Male");

		return person;
	}
}
