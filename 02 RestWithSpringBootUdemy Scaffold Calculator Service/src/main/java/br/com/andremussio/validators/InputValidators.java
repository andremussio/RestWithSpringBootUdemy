package br.com.andremussio.validators;

import br.com.andremussio.exception.UnsupportedMathOperationException;
import br.com.andremussio.utils.Utils;

public class InputValidators {

	public static void validaEntradaNumerica(String numberOne) {
		if (!Utils.isNumeric(numberOne)) {
			throw new UnsupportedMathOperationException("Valor inválido para a operação!");
		}

	}

	public static void validaEntradaNumerica(String numberOne, String numberTwo) {
		if (!Utils.isNumeric(numberOne) || !Utils.isNumeric(numberTwo)) {
			throw new UnsupportedMathOperationException("Valor inválido para a operação!");
		}

	}

	public static void validaEntradaMaiorQueZero(String numberOne) {
		if (Utils.convertToDouble(numberOne) <= 0)
			throw new UnsupportedMathOperationException("Valor deve ser maior que zero!");
	}
}
