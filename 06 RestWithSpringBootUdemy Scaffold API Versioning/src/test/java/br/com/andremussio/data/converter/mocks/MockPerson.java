package br.com.andremussio.data.converter.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.andremussio.data.model.Person;
import br.com.andremussio.data.vo.PersonVOv1;

public class MockPerson {

	public Person mockEntity() {
		return mockEntity(0);
	}
	
	public PersonVOv1 mockVO() {
		return mockVO(0);
	}
	
	public List<Person> mockEntityList() {
		List<Person> persons = new ArrayList<Person>();
		for (int i = 0; i< 14; i++) {
			persons.add(mockEntity(i));
		}
		return persons;
	}
	
	public List<PersonVOv1> mockVOList() {
		List<PersonVOv1> persons = new ArrayList<PersonVOv1>();
		for (int i = 0; i< 14; i++) {
			persons.add(mockVO(i));
		}
		return persons;
	}
	
	private Person mockEntity(Integer number) {
		Person person = new Person();
		person.setAddress("Address test " + number);
		person.setFirstName("First Name " + number);
		person.setLastName("Last Name " + number);
		person.setGender(((number % 2) == 0 ? "Male" : "Female"));
		person.setId(number.longValue());
		
		return person;
	}

	private PersonVOv1 mockVO(Integer number) {
		PersonVOv1 person = new PersonVOv1();
		person.setAddress("Address test " + number);
		person.setFirstName("First Name " + number);
		person.setLastName("Last Name " + number);
		person.setGender(((number % 2) == 0 ? "Male" : "Female"));
		person.setId(number.longValue());
		
		return person;
	}
}
