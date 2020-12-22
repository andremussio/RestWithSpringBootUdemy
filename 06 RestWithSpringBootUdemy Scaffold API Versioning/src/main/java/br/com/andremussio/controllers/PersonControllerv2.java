package br.com.andremussio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.andremussio.data.vo.PersonVOv2;
import br.com.andremussio.services.PersonServicesv2;

@RestController
@RequestMapping(value = "/person/v2")
public class PersonControllerv2 {
	@Autowired
	private PersonServicesv2 personServices;

	/*
	 * @PostMapping é uma abreviação para @RequestMapping do verbo POST.
	 */
	// @RequestMapping(method = RequestMethod.POST, consumes =
	// MediaType.APPLICATION_JSON_VALUE, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	@PostMapping
	public PersonVOv2 createv2(@RequestBody PersonVOv2 person) {
		return personServices.createv2(person);
	}

}
