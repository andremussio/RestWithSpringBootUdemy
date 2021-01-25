package br.com.andremussio.data.vo.v1;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport; //ResourceSupport não existe mais a partir da versão 1.0 do HATEOAS. Foi substituído por RepresentationModel. 

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

//Altera ordem dos atributos no JSON
@JsonPropertyOrder({"id","address","first_name","last_name","payDay","gender"})
public class PersonVO extends ResourceSupport implements Serializable {

	private static final long serialVersionUID = 1L;

	//Necessário trocar o nome do atributo "id" para "key" porque ResourceSupport já possui um atributo chamado "id".
	//@Mapping do Dozer permite dar o apelido "id" para o atributo "key", de forma a não ser necessário mexer em mais nada.
	//@JsonProperty configura o nome do atributo no JSON
	@Mapping("id")
	@JsonProperty("id")
	private Long key;
	//Altera o nome do atributo no JSON
	@JsonProperty("first_name")
	private String firstName;
	//Altera o nome do atributo no JSON
	@JsonProperty("last_name")
	private String lastName;
	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	private String address;
	//Omite o atributo no JSON
	//@JsonIgnore
	private String gender;
	private Integer payDay;

	public PersonVO() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getPayDay() {
		return payDay;
	}

	public void setPayDay(Integer payDay) {
		this.payDay = payDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((payDay == null) ? 0 : payDay.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonVO other = (PersonVO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (payDay == null) {
			if (other.payDay != null)
				return false;
		} else if (!payDay.equals(other.payDay))
			return false;
		return true;
	}

}
