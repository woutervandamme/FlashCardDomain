package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import db.DBException;
import domain.Group;
import domain.Question;
import domain.QuestionText;
import domain.User;
import facade.Facade;

public class GroupTest {

	private User user;
	private Facade facade;
	
	@Before
	public void setUp(){
		facade = Facade.getInstance();
	}
	
	@Test
	public void addUser() {
		try {
			facade.addUser("blub@bla.blub", "name", "pw");
			User u = facade.getUser("blub@bla.blub");
			
			assertEquals(facade.getUser("blub@bla.blub").getName(), "name");
		} catch (DBException e) {
			e.printStackTrace();
		}
	
	}
	
	
	@Test (expected = DBException.class)
	public void getNonExistingUser() throws DBException{
		facade.getUser("gaga@bla.blub");
	}
	
	@Test
	public void getExistingUser(){
		try {
			assertNotNull(facade.getUser("test@test.lol"));
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test (expected = DBException.class)
	public void addUserToNonExistingGroup() throws DBException{
		facade.addUserToGroup(555, "blub@bla.blub");
	}
	
	@Test  (expected = DBException.class)
	public void getNonExistingQuestion() throws DBException{
		facade.getQuestion(99999);
	}
	
	@Test (expected = DBException.class)
	public void getNonExistingGroup() throws DBException{
		facade.getGroup(9999999);
	}
	
	@Test (expected = DBException.class)
	public void getNonExistingGroupAdmin() throws DBException {
		facade.getGroupAdmin(99999);
	}
	
	@Test (expected = DBException.class)
	public void getGroupsForNonExistingUser() throws DBException{
		facade.getGroupsForUser("fefewfwef@frefre.be");
	}
	
	@Test (expected = DBException.class)
	public void getMessagesForNonExistingUser() throws DBException {
		facade.getMessagesForUser("fefewfwef@frefre.be");
	}
	
	@Test
	public void isAdminForNonExistingGroup() throws DBException{
		facade.isAdmin(999999);
	}
	
}
