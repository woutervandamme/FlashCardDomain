package domain;

public class DomainException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1364655200879490664L;
	
	public DomainException(String message){
		super(message);
	}
	public DomainException(Exception e){
		super(e);
	}
	public DomainException(String message, Exception e){
		super(message,e);
	}

}
