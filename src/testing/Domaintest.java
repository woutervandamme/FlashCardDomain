package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import db.DBException;
import db.MySQLDatabase;

import facade.Facade;

public class Domaintest {

	@Test
	public void login() throws DBException {
		Facade facade = Facade.getInstance();
		facade.setDatabase(new MySQLDatabase());
		assertFalse(facade.login("blub@bla.bl", "1526401"));
		
	}

}
