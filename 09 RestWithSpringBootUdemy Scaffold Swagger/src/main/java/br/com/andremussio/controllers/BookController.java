package br.com.andremussio.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

import br.com.andremussio.data.vo.v1.BookVO;
import br.com.andremussio.services.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Book endpoint", description = "Books management", tags = {"endpoint /book"})
@RestController
@RequestMapping(value = "/api/book/v1")
public class BookController {
	@Autowired
	private BookServices bookServices;

	@ApiOperation(value = "Lista todos os livros cadastrados")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public List<BookVO> findAll() {
		List<BookVO> books = bookServices.findAll();		
		//Adiciona links de auto-relacionamento para cada elemento da lista
		books
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()
				)
			);
		return books;
	}

	@ApiOperation(value = "Lista um livro específico informado por {id}")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public BookVO findById(@PathVariable(value = "id") Long id) {
		BookVO bookVO = bookServices.findById(id);
		// Adiciona um link de auto-relacionamento
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Cadastra um novo livro")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BookVO create(@RequestBody BookVO book) {
		BookVO bookVO = bookServices.create(book);
		// Adiciona um link de auto-relacionamento
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Altera dados de um livro já cadastrado e especificado por {id}")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BookVO update(@RequestBody BookVO book) {
		BookVO bookVO = bookServices.update(book);
		// Adiciona um link de auto-relacionamento
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@ApiOperation(value = "Remove um livro já cadastrado e especificado por {id}")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		bookServices.delete(id);
		return ResponseEntity.ok().build();
	}

}
