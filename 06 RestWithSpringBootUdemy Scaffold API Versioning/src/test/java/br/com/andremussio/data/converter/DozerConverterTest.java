package br.com.andremussio.data.converter;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.andremussio.data.converter.mocks.MockPerson;
import br.com.andremussio.data.model.Person;
import br.com.andremussio.data.vo.PersonVOv1;

public class DozerConverterTest {

	MockPerson inputObject;

	@Before
	public void setUp() {
		inputObject = new MockPerson();
	}

	@Test
	public void parseEntityToVOTest() {
		PersonVOv1 output = DozerConverter.parseObject(inputObject.mockEntity(), PersonVOv1.class);

		Assert.assertEquals(Long.valueOf(0L), output.getId());
		Assert.assertEquals("First Name 0", output.getFirstName());
		Assert.assertEquals("Last Name 0", output.getLastName());
		Assert.assertEquals("Address test 0", output.getAddress());
		Assert.assertEquals("Male", output.getGender());
	}

	@Test
	public void parseEntityListToVOListTest() {
		List<PersonVOv1> outputList = DozerConverter.parseListObjects(inputObject.mockEntityList(), PersonVOv1.class);

		PersonVOv1 outputZero = outputList.get(0);
		Assert.assertEquals(Long.valueOf(0L), outputZero.getId());
		Assert.assertEquals("First Name 0", outputZero.getFirstName());
		Assert.assertEquals("Last Name 0", outputZero.getLastName());
		Assert.assertEquals("Address test 0", outputZero.getAddress());
		Assert.assertEquals("Male", outputZero.getGender());

		PersonVOv1 outputSeven = outputList.get(7);
		Assert.assertEquals(Long.valueOf(7L), outputSeven.getId());
		Assert.assertEquals("First Name 7", outputSeven.getFirstName());
		Assert.assertEquals("Last Name 7", outputSeven.getLastName());
		Assert.assertEquals("Address test 7", outputSeven.getAddress());
		Assert.assertEquals("Female", outputSeven.getGender());

		PersonVOv1 outputTwelve = outputList.get(12);
		Assert.assertEquals(Long.valueOf(12L), outputTwelve.getId());
		Assert.assertEquals("First Name 12", outputTwelve.getFirstName());
		Assert.assertEquals("Last Name 12", outputTwelve.getLastName());
		Assert.assertEquals("Address test 12", outputTwelve.getAddress());
		Assert.assertEquals("Male", outputTwelve.getGender());
	}

	@Test
	public void parseVOToEntityTest() {
		Person output = DozerConverter.parseObject(inputObject.mockVO(), Person.class);

		Assert.assertEquals(Long.valueOf(0L), output.getId());
		Assert.assertEquals("First Name 0", output.getFirstName());
		Assert.assertEquals("Last Name 0", output.getLastName());
		Assert.assertEquals("Address test 0", output.getAddress());
		Assert.assertEquals("Male", output.getGender());
	}

	@Test
	public void parseVOListToEntityListTest() {
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
	}
}
