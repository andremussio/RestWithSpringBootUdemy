package br.com.andremussio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andremussio.data.converter.custom.PersonConverter;
import br.com.andremussio.data.model.Person;
import br.com.andremussio.data.vo.PersonVOv2;
import br.com.andremussio.repository.PersonRepository;

/*
 * @Service marca a classe informando ao Spring que ela deverá ser gerenciada de forma a 
 * permitir que o Spring a injete automaticamente nos códigos que a utilizarem.
 * Os códigos que necessitarem dessa classe, deverão recebê-la por injeção de dependência, através o uso
 * da anotação @Autowired.
 */
@Service
public class PersonServicesv2 {

	@Autowired
	PersonRepository repository;

	@Autowired
	PersonConverter personConverter;
	
	/*
	 * Create services
	 */
	// v2
	public PersonVOv2 createv2(PersonVOv2 person) {
		/*
		 * A partir do Java 11, foi implementada a declaração de variáveis com
		 * inferência de tipo (var). Com isso, não é necessário declarar a variável do
		 * tipo do objeto que ela vai receber. Com o uso do "var", o Java
		 * automaticamente reconhece o tipo. Exemplo de como ficaria com o "var":
		 * 
		 * var entity = DozerConverter.parseObject(person, Person.class); var vo =
		 * DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		 */
		Person entity = personConverter.convertVOtoEntity(person);
		PersonVOv2 vo = personConverter.convertEntityToVO(repository.save(entity));
		return vo;
	}
}
