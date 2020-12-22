package br.com.andremussio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.andremussio.data.converter.DozerConverter;
import br.com.andremussio.data.model.Person;
import br.com.andremussio.data.vo.PersonVO;
import br.com.andremussio.exception.ResourceNotFoundException;
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

	public PersonVO create(PersonVO person) {
		/*
		 * A partir do Java 11, foi implementada a declaração de variáveis com inferência de tipo (var).
		 * Com isso, não é necessário declarar a variável do tipo do objeto que ela vai receber.
		 * Com o uso do "var", o Java automaticamente reconhece o tipo.
		 * Exemplo de como ficaria com o "var":
		 * 
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		*/
		Person entity = DozerConverter.parseObject(person, Person.class);
		PersonVO vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public PersonVO update(PersonVO person) {
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		entity.setPayDay(person.getPayDay());

		PersonVO vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.delete(entity);
	}

	public PersonVO findById(Long id) {
		/*
		 * Método orElseThrow dispara uma exceção caso não seja encontrado o objeto
		 * informado no id. Será criada a exceção ResourceNotFoundException para tratar
		 * este caso, de forma a não retornar uma exceção genérica.
		 */

		/*
		 * Forma 1 de tratamento e retorno: Forma básica ResourceNotFoundException
		 * resourceNotFoundException = new
		 * ResourceNotFoundException("No records found for this ID"); Optional<PersonVO>
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
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		PersonVO vo = DozerConverter.parseObject(entity, PersonVO.class);
		return vo;
	}

	public List<PersonVO> findAll() {
		List<PersonVO> listVO = DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
		return listVO;
	}

}
