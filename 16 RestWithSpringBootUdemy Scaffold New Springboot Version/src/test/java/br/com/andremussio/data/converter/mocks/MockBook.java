package br.com.andremussio.data.converter.mocks;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.andremussio.data.model.Book;
import br.com.andremussio.data.vo.v1.BookVO;

public class MockBook {

	public Book mockEntity() {
		return mockEntity(0);
	}
	
	public BookVO mockVO() {
		return mockVO(0);
	}
	
	public List<Book> mockEntityList() {
		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i< 14; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}
	
	public List<BookVO> mockVOList() {
		List<BookVO> books = new ArrayList<BookVO>();
		for (int i = 0; i< 14; i++) {
			books.add(mockVO(i));
		}
		return books;
	}
	
	private Book mockEntity(Integer number) {
		Date data = new Date(2021,0,1);
		Integer preco = (number + 100);
		
		Book book = new Book();
		book.setAuthor("Author " + number);
		book.setLaunchDate(data);
		book.setPrice(preco.doubleValue());
		book.setTitle("Title " + number);
		book.setId(number.longValue());
		
		return book;
	}

	private BookVO mockVO(Integer number) {
		Date data = new Date(2021,0,1);
		Integer preco = (number + 100);

		BookVO book = new BookVO();
		book.setAuthor("Author " + number);
		book.setLaunchDate(data);
		book.setPrice(preco.doubleValue());
		book.setTitle("Title " + number);
		book.setKey(number.longValue());

		return book;
	}
}
