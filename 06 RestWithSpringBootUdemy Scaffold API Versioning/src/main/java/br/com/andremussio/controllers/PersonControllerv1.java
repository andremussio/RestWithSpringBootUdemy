package br.com.andremussio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andremussio.data.vo.PersonVOv1;
import br.com.andremussio.services.PersonServicesv1;

@RestController
@RequestMapping(value = "/person/v1")
public class PersonControllerv1 {
	@Autowired
	private PersonServicesv1 personServices;

	/* Method GET é default, não precisa ser informado.
	 * MediaType.APPLICATION.JSON_VALUE é default, não precisa ser informado.
	 * @GetMapping é uma abreviação para @RequestMapping do verbo GET.
	 */
	//@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping
	public List<PersonVOv1> findAll() {
		return personServices.findAll();
	}

	//@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/{id}")
	public PersonVOv1 findById(@PathVariable(value = "id") Long id) {
		return personServices.findById(id);
	}

	/* 
	 * @PostMapping é uma abreviação para @RequestMapping do verbo POST.
	 */

	//@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping
	public PersonVOv1 create(@RequestBody PersonVOv1 person) {
		return personServices.create(person);
	}

	/* 
	 * @PutMapping é uma abreviação para @RequestMapping do verbo PUT.
	 */
	//@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PutMapping
	public PersonVOv1 update(@RequestBody PersonVOv1 person) {
		return personServices.update(person);
	}

	/* 
	 * @DeleteMapping é uma abreviação para @RequestMapping do verbo DELETE.
	 */
	//@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		personServices.delete(id);
		return ResponseEntity.ok().build();
	}
}
