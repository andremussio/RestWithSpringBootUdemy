package br.com.andremussio.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andremussio.data.vo.v1.PersonVO;
import br.com.andremussio.services.PersonServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/*
 * CORS - Cross Origin Resource Sharing
 * É um recurso que permite compartilhar o uso de APIs para outras origens (domínios web).
 * É possível configurar de 3 formas:
 * - @CrossOrigin globalmente em cada controller;
 * - @CrossOrigin localmente em cada método;
 * - Configuração centralizada na aplicação.
 */

//@CrossOrigin(origins = {"http://localhost:8080", "www.erudio.com.br"}) //Configuração CORS global no controller todo
@Api(value = "Person endpoint", description = "Persons management", tags = {"endpoint /person"})
@RestController
@RequestMapping(value = "/api/person/v1")
public class PersonController {
	@Autowired
	private PersonServices personServices;

	/*
	 * Method GET é default, não precisa ser informado.
	 * MediaType.APPLICATION.JSON_VALUE é default, não precisa ser informado.
	 * 
	 * @GetMapping é uma abreviação para @RequestMapping do verbo GET.
	 */
	// @RequestMapping(method = RequestMethod.GET, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// Ao configurar a aplicação para usar mais de um tipo de serialização, o
	// atributo "produces" passa a ser obrigatório!
	@ApiOperation(value = "Lista todas as pessoas cadastradas")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
//	@CrossOrigin(origins = "http://localhost:8080") //Configuração CORS local no método
	public List<PersonVO> findAll() {
		List<PersonVO> persons = personServices.findAll();		
		//Adiciona links de auto-relacionamento para cada elemento da lista
		persons
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()
				)
			);
		return persons;
	}

	// @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Lista uma pessoa específica informada por {id}")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
//	@CrossOrigin(origins = {"http://localhost:8080", "www.erudio.com.br"}) //Configuração CORS local no método
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		PersonVO personVO = personServices.findById(id);
		// Adiciona um link de auto-relacionamento
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	/*
	 * @PostMapping é uma abreviação para @RequestMapping do verbo POST.
	 */

	// @RequestMapping(method = RequestMethod.POST, consumes =
	// MediaType.APPLICATION_JSON_VALUE, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// Método POST produz e consome JSON e XML
	@ApiOperation(value = "Cadastra uma nova pessoa")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO create(@RequestBody PersonVO person) {
		PersonVO personVO = personServices.create(person);
		// Adiciona um link de auto-relacionamento
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	/*
	 * @PutMapping é uma abreviação para @RequestMapping do verbo PUT.
	 */
	// @RequestMapping(method = RequestMethod.PUT, consumes =
	// MediaType.APPLICATION_JSON_VALUE, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// Método PUT produz e consome JSON e XML
	@ApiOperation(value = "Altera dados de uma pessoa já cadastrada e especificada por {id}")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO update(@RequestBody PersonVO person) {
		PersonVO personVO = personServices.update(person);
		// Adiciona um link de auto-relacionamento
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	/*
	 * @DeleteMapping é uma abreviação para @RequestMapping do verbo DELETE.
	 */
	// @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Remove uma pessoa já cadastrada e especificada por {id}")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		personServices.delete(id);
		return ResponseEntity.ok().build();
	}
}
