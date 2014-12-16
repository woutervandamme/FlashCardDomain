package db;

public class dbException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5040533206967781826L;
	
	public dbException(String message){
		super(message);
	}
	public dbException(Exception e){
		super(e);
	}

}
