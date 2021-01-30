package br.com.andremussio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.andremussio.data.converter.DozerConverter;
import br.com.andremussio.data.model.Person;
import br.com.andremussio.data.vo.v1.PersonVO;
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
		Person entity = repository.findById(person.getKey())
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

	public Page<PersonVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		
		return page.map(this::convertToPersonVO);
		
		/* Exemplo de paginação sem HATEOAS. O método findAll retorna List<PersonVO>
		// findAll com parâmetro Pageable do Spring Data retorna Page<Person>. Então, usamos o método getContent para transformar em List<Person>.
		var entities = repository.findAll(pageable).getContent();
		
		List<PersonVO> listVO = DozerConverter.parseListObjects(entities, PersonVO.class);
		return listVO;
		*/
	}

	private PersonVO convertToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	/*
	// Os métodos padrão do repositório já têm o controle transacional aplicado pelo Spring Data.
	// Novos métodos de operação no banco de dados, que não são os padrões do repositório, precisam da anotação @Transactional do Spring Data para ativar o controle transacional.
	// A anotação @Transactional pode ser usada também na classe.
	// A anotação @Transactional faz com que o Spring Data controle as operações, aplicando o conceito de ACID:
	// - A = Atomicidade  = Todas as operações efetuadas na sessão devem ser finalizadas com sucesso para que os dados sejam efetivados no BD;
	// - C = Consistência = Após a efetivação dos dados no BD, o BD deve ficar consistente;
	// - I = Isolamento   = Sessões concorrentes da aplicação não podem interferir uma na outra;
	// - D = Durabilidade = Quando a transação for persistida, os dados devem ser gravados no BD, não podendo falhar em virtudo de alguma falha de rede ou no servidor de aplicação.
	 */
	@Transactional
	public PersonVO disablePerson(Long id) {
		// Chama método do repositório para UPDATE que desabilita a pessoa
		repository.disablePerson(id);
		
		// Busca a entidade atualizada e transforma em VO para retornar (poderia optar por fazer este método não retornar nada!)
		Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		PersonVO vo = DozerConverter.parseObject(entity, PersonVO.class);
		return vo;
	}

}
