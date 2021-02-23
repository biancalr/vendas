package br.com.vendas.exception;

public class PedidoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 7384886230413822245L;

	public PedidoNaoEncontradoException() {
		super();
	}

	public PedidoNaoEncontradoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PedidoNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public PedidoNaoEncontradoException(String message) {
		super(message);
	}

	public PedidoNaoEncontradoException(Throwable cause) {
		super(cause);
	}

	
	
}
