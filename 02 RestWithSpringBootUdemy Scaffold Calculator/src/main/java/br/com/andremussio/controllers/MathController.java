package br.com.andremussio.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.andremussio.exception.UnsupportedMathOperationException;
import br.com.andremussio.math.SimpleMath;
import br.com.andremussio.utils.Utils;
import br.com.andremussio.validators.InputValidators;

@RestController
public class MathController {

	@RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double sum(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		InputValidators.validaEntradaNumerica(numberOne, numberTwo);
		return SimpleMath.sum(Utils.convertToDouble(numberOne), Utils.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/subtraction/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double subtraction(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		InputValidators.validaEntradaNumerica(numberOne, numberTwo);
		return SimpleMath.subtraction(Utils.convertToDouble(numberOne), Utils.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/multiplication/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double multiplication(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		InputValidators.validaEntradaNumerica(numberOne, numberTwo);
		return SimpleMath.multiplication(Utils.convertToDouble(numberOne), Utils.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/division/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double division(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		InputValidators.validaEntradaNumerica(numberOne, numberTwo);

		Double dividendo = Utils.convertToDouble(numberOne);
		Double divisor = Utils.convertToDouble(numberTwo);
		if (divisor == 0D) {
			throw new UnsupportedMathOperationException("O divisor não pode ser zero!");
		}
		return SimpleMath.sum(dividendo, divisor);
	}

	@RequestMapping(value = "/average/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double average(@PathVariable(value = "numberOne") String numberOne,
			@PathVariable(value = "numberTwo") String numberTwo) throws Exception {
		InputValidators.validaEntradaNumerica(numberOne, numberTwo);
		return SimpleMath.average(Utils.convertToDouble(numberOne), Utils.convertToDouble(numberTwo));
	}

	@RequestMapping(value = "/sqrt/{numberOne}", method = RequestMethod.GET)
	public Double sqrt(@PathVariable(value = "numberOne") String numberOne) throws Exception {
		InputValidators.validaEntradaNumerica(numberOne);
		InputValidators.validaEntradaMaiorQueZero(numberOne);
		return SimpleMath.sqrt(Utils.convertToDouble(numberOne));
	}

}
