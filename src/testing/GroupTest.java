package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import domain.Group;
import domain.Question;
import domain.User;

public class GroupTest {

	
	private Group group;
	
	private String name;
	
	private User admin;
		
	private ArrayList<User> users;
		
	private ArrayList<Question> questions;
	
	private boolean userAddQuestion, userInviteFriends;
	
	private int id;
	
	
	@Before
	public void setup(){
		group = new Group();
		name = "testGroup";
		admin = new User("adminTest","adminTest","adminTest");
		users = new ArrayList<>();
		users.add(new User("test","test","test"));
		
		questions = new ArrayList<>();
		userAddQuestion = true;
		userInviteFriends = true;
		id = 1;
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
}
