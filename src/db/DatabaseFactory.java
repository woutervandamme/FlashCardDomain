package db;

public class DatabaseFactory {

	
	private Database db;
	
	
	public Database createDatabase(String type){
		
		if(type.equals("mysql")){
			db = new MySQLDatabase();
		} 
		
		return db;
	}
}
