package io.t3w.desafio;

import java.math.BigDecimal;

public abstract class T3WUtils {
	public static BigDecimal calcularValorTotal(final int quantidade, final BigDecimal valorUnitario) {
		if (quantidade < 0 || valorUnitario == null || valorUnitario.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Quantidade ou valor unitário inválido");
		}
		return valorUnitario.multiply(BigDecimal.valueOf(quantidade));
	}
}
