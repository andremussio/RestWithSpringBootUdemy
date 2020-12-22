package br.com.andremussio.data.converter.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.andremussio.data.model.Person;
import br.com.andremussio.data.vo.PersonVOv2;

@Service
public class PersonConverter {

	public PersonVOv2 convertEntityToVO(Person person) {
		PersonVOv2 vo = new PersonVOv2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setBirthDate(new Date());
		vo.setFirstName(person.getFirstName());
		vo.setGender(person.getGender());
		vo.setLastName(person.getLastName());
		vo.setPayDay(person.getPayDay());
		
		return vo;
	}
	
	public Person convertVOtoEntity(PersonVOv2 person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setGender(person.getGender());
		entity.setLastName(person.getLastName());
		entity.setPayDay(person.getPayDay());
		
		return entity;
	}
}
