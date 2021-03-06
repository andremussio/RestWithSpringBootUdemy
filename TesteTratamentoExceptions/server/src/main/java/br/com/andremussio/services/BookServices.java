package br.com.andremussio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.andremussio.data.converter.DozerConverter;
import br.com.andremussio.data.model.Book;
import br.com.andremussio.data.vo.v1.BookVO;
import br.com.andremussio.exception.ResourceNotFoundException;
import br.com.andremussio.repository.BookRepository;

@Service
public class BookServices {

	@Autowired
	BookRepository repository;
	
	public BookVO create(BookVO book) {
		Book entity = DozerConverter.parseObject(book, Book.class);
		BookVO vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public BookVO update(BookVO book) {
		Book entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());

		BookVO vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		repository.delete(entity);
	}

	public BookVO findById(Long id) {
		Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		BookVO vo = DozerConverter.parseObject(entity, BookVO.class);
		return vo;
	}

	public Page<BookVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		
		return page.map(this::convertToBookVO);
	}
	
	private BookVO convertToBookVO(Book entity) {
		return DozerConverter.parseObject(entity, BookVO.class);
	}
}
