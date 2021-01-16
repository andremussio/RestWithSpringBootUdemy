package br.com.andremussio.data.converter;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.andremussio.data.converter.mocks.MockBook;
import br.com.andremussio.data.converter.mocks.MockPerson;
import br.com.andremussio.data.model.Book;
import br.com.andremussio.data.model.Person;
import br.com.andremussio.data.vo.v1.BookVO;
import br.com.andremussio.data.vo.v1.PersonVO;

public class DozerConverterTest {

	MockPerson inputObject;
	MockBook inputObjectBook;

	@Before
	public void setUp() {
		inputObject = new MockPerson();
		inputObjectBook = new MockBook();
	}

	@Test
	public void parseEntityToVOTest() {
		// Person
		PersonVO output = DozerConverter.parseObject(inputObject.mockEntity(), PersonVO.class);

		Assert.assertEquals(Long.valueOf(0L), output.getKey());
		Assert.assertEquals("First Name 0", output.getFirstName());
		Assert.assertEquals("Last Name 0", output.getLastName());
		Assert.assertEquals("Address test 0", output.getAddress());
		Assert.assertEquals("Male", output.getGender());

		// Book
		BookVO outputBook = DozerConverter.parseObject(inputObjectBook.mockEntity(), BookVO.class);
		Date data = new Date(2021, 0, 1);

		Assert.assertEquals(Long.valueOf(0L), outputBook.getKey());
		Assert.assertEquals("Author 0", outputBook.getAuthor());
		Assert.assertTrue(data.equals(outputBook.getLaunchDate()));
		Assert.assertTrue(outputBook.getPrice() == (0 + 100));
		Assert.assertEquals("Title 0", outputBook.getTitle());
	}

	@Test
	public void parseEntityListToVOListTest() {
		// Person
		List<PersonVO> outputList = DozerConverter.parseListObjects(inputObject.mockEntityList(), PersonVO.class);

		PersonVO outputZero = outputList.get(0);
		Assert.assertEquals(Long.valueOf(0L), outputZero.getKey());
		Assert.assertEquals("First Name 0", outputZero.getFirstName());
		Assert.assertEquals("Last Name 0", outputZero.getLastName());
		Assert.assertEquals("Address test 0", outputZero.getAddress());
		Assert.assertEquals("Male", outputZero.getGender());

		PersonVO outputSeven = outputList.get(7);
		Assert.assertEquals(Long.valueOf(7L), outputSeven.getKey());
		Assert.assertEquals("First Name 7", outputSeven.getFirstName());
		Assert.assertEquals("Last Name 7", outputSeven.getLastName());
		Assert.assertEquals("Address test 7", outputSeven.getAddress());
		Assert.assertEquals("Female", outputSeven.getGender());

		PersonVO outputTwelve = outputList.get(12);
		Assert.assertEquals(Long.valueOf(12L), outputTwelve.getKey());
		Assert.assertEquals("First Name 12", outputTwelve.getFirstName());
		Assert.assertEquals("Last Name 12", outputTwelve.getLastName());
		Assert.assertEquals("Address test 12", outputTwelve.getAddress());
		Assert.assertEquals("Male", outputTwelve.getGender());

		// Book
		List<BookVO> outputListBook = DozerConverter.parseListObjects(inputObjectBook.mockEntityList(), BookVO.class);
		Date data = new Date(2021, 0, 1);

		BookVO outputZeroBook = outputListBook.get(0);
		Assert.assertEquals(Long.valueOf(0L), outputZeroBook.getKey());
		Assert.assertEquals("Author 0", outputZeroBook.getAuthor());
		Assert.assertTrue(data.equals(outputZeroBook.getLaunchDate()));
		Assert.assertTrue(outputZeroBook.getPrice() == (0 + 100));
		Assert.assertEquals("Title 0", outputZeroBook.getTitle());

		BookVO outputSevenBook = outputListBook.get(7);
		Assert.assertEquals(Long.valueOf(7L), outputSevenBook.getKey());
		Assert.assertEquals("Author 7", outputSevenBook.getAuthor());
		Assert.assertTrue(data.equals(outputSevenBook.getLaunchDate()));
		Assert.assertTrue(outputSevenBook.getPrice() == (7 + 100));
		Assert.assertEquals("Title 7", outputSevenBook.getTitle());

		BookVO outputTwelveBook = outputListBook.get(12);
		Assert.assertEquals(Long.valueOf(12L), outputTwelveBook.getKey());
		Assert.assertEquals("Author 12", outputTwelveBook.getAuthor());
		Assert.assertTrue(data.equals(outputTwelveBook.getLaunchDate()));
		Assert.assertTrue(outputTwelveBook.getPrice() == (12 + 100));
		Assert.assertEquals("Title 12", outputTwelveBook.getTitle());
	}

	@Test
	public void parseVOToEntityTest() {
		// Person
		Person output = DozerConverter.parseObject(inputObject.mockVO(), Person.class);

		Assert.assertEquals(Long.valueOf(0L), output.getId());
		Assert.assertEquals("First Name 0", output.getFirstName());
		Assert.assertEquals("Last Name 0", output.getLastName());
		Assert.assertEquals("Address test 0", output.getAddress());
		Assert.assertEquals("Male", output.getGender());

		// Book
		Book outputBook = DozerConverter.parseObject(inputObjectBook.mockVO(), Book.class);
		Date data = new Date(2021, 0, 1);

		Assert.assertEquals(Long.valueOf(0L), outputBook.getId());
		Assert.assertEquals("Author 0", outputBook.getAuthor());
		Assert.assertTrue(data.equals(outputBook.getLaunchDate()));
		Assert.assertTrue(outputBook.getPrice() == (0 + 100));
		Assert.assertEquals("Title 0", outputBook.getTitle());
	}

	@Test
	public void parseVOListToEntityListTest() {
		// Person
		List<Person> outputList = DozerConverter.parseListObjects(inputObject.mockVOList(), Person.class);

		Person outputZero = outputList.get(0);
		Assert.assertEquals(Long.valueOf(0L), outputZero.getId());
		Assert.assertEquals("First Name 0", outputZero.getFirstName());
		Assert.assertEquals("Last Name 0", outputZero.getLastName());
		Assert.assertEquals("Address test 0", outputZero.getAddress());
		Assert.assertEquals("Male", outputZero.getGender());

		Person outputSeven = outputList.get(7);
		Assert.assertEquals(Long.valueOf(7L), outputSeven.getId());
		Assert.assertEquals("First Name 7", outputSeven.getFirstName());
		Assert.assertEquals("Last Name 7", outputSeven.getLastName());
		Assert.assertEquals("Address test 7", outputSeven.getAddress());
		Assert.assertEquals("Female", outputSeven.getGender());

		Person outputTwelve = outputList.get(12);
		Assert.assertEquals(Long.valueOf(12L), outputTwelve.getId());
		Assert.assertEquals("First Name 12", outputTwelve.getFirstName());
		Assert.assertEquals("Last Name 12", outputTwelve.getLastName());
		Assert.assertEquals("Address test 12", outputTwelve.getAddress());
		Assert.assertEquals("Male", outputTwelve.getGender());

		// Book
		List<Book> outputListBook = DozerConverter.parseListObjects(inputObjectBook.mockVOList(), Book.class);
		Date data = new Date(2021, 0, 1);

		Book outputZeroBook = outputListBook.get(0);
		Assert.assertEquals(Long.valueOf(0L), outputZeroBook.getId());
		Assert.assertEquals("Author 0", outputZeroBook.getAuthor());
		Assert.assertTrue(data.equals(outputZeroBook.getLaunchDate()));
		Assert.assertTrue(outputZeroBook.getPrice() == (0 + 100));
		Assert.assertEquals("Title 0", outputZeroBook.getTitle());

		Book outputSevenBook = outputListBook.get(7);
		Assert.assertEquals(Long.valueOf(7L), outputSevenBook.getId());
		Assert.assertEquals("Author 7", outputSevenBook.getAuthor());
		Assert.assertTrue(data.equals(outputSevenBook.getLaunchDate()));
		Assert.assertTrue(outputSevenBook.getPrice() == (7 + 100));
		Assert.assertEquals("Title 7", outputSevenBook.getTitle());

		Book outputTwelveBook = outputListBook.get(12);
		Assert.assertEquals(Long.valueOf(12L), outputTwelveBook.getId());
		Assert.assertEquals("Author 12", outputTwelveBook.getAuthor());
		Assert.assertTrue(data.equals(outputTwelveBook.getLaunchDate()));
		Assert.assertTrue(outputTwelveBook.getPrice() == (12 + 100));
		Assert.assertEquals("Title 12", outputTwelveBook.getTitle());

	}
}
