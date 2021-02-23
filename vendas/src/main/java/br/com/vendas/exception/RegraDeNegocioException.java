package br.com.vendas.exception;

public class RegraDeNegocioException extends RuntimeException{

	private static final long serialVersionUID = -4211352702120411533L;

	public RegraDeNegocioException() {
		super();
	}

	public RegraDeNegocioException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RegraDeNegocioException(String message, Throwable cause) {
		super(message, cause);
	}

	public RegraDeNegocioException(String message) {
		super(message);
	}

	public RegraDeNegocioException(Throwable cause) {
		super(cause);
	}

	
	
}
