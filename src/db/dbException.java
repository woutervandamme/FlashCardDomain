package db;

public class DBException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5040533206967781826L;
	
	public DBException(String message){
		super(message);
	}
	public DBException(Exception e){
		super(e);
	}
	public DBException(String message, Exception e){
		super(message,e);
	}
}
