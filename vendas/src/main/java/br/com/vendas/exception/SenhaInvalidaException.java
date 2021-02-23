package br.com.vendas.exception;

public class SenhaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 9089118109344995597L;

	public SenhaInvalidaException() {
		super();
	}

	public SenhaInvalidaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SenhaInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	public SenhaInvalidaException(String message) {
		super(message);
	}

	public SenhaInvalidaException(Throwable cause) {
		super(cause);
	}

}
