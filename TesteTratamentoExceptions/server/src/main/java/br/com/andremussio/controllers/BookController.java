package br.com.andremussio.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.andremussio.data.vo.v1.BookVO;
import br.com.andremussio.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(description = "Books management", name = "Books")
@RestController
@RequestMapping(value = "/api/book/v1")
public class BookController {
	@Autowired
	private BookServices bookServices;

	@Operation(summary = "Lista todos os livros cadastrados")
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<CollectionModel<BookVO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		// Define sortDirection desconsiderando case sensitive.
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
				
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));
		
		Page<BookVO> books = bookServices.findAll(pageable);		
		
		//Adiciona links de auto-relacionamento para cada elemento da lista
		books
			.stream()
			.forEach(p -> p.add(
					linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()
				)
			);
		
		return ResponseEntity.ok(CollectionModel.of(books));

	}

	@Operation(summary = "Lista um livro específico informado por {id}")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public BookVO findById(@PathVariable(value = "id") Long id) {
		BookVO bookVO = bookServices.findById(id);
		// Adiciona um link de auto-relacionamento
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}

	@Operation(summary = "Cadastra um novo livro")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BookVO create(@RequestBody BookVO book) {
		BookVO bookVO = bookServices.create(book);
		// Adiciona um link de auto-relacionamento
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@Operation(summary = "Altera dados de um livro já cadastrado e especificado por {id}")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public BookVO update(@RequestBody BookVO book) {
		BookVO bookVO = bookServices.update(book);
		// Adiciona um link de auto-relacionamento
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@Operation(summary = "Remove um livro já cadastrado e especificado por {id}")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		bookServices.delete(id);
		return ResponseEntity.ok().build();
	}

}
