package facade;

import java.util.ArrayList;

import db.Database;
import db.DatabaseFactory;
import db.MySQLDatabase;
import db.dbException;
import domain.Group;
import domain.Question;
import domain.QuestionText;
import domain.User;

public class Facade {

	// this is the current user
	
	private static Facade instance = null;
	private User user;
	
	private Database db;
	private DatabaseFactory dbFac;
	
	private ArrayList<Group> groups;
	private Group currentGroup;
	
	private Question currentQuestion;
	
	
	/*
	 * 
	 * flow vn de app:
	 * 
	 * inloggen (logIn functie geef email en pw mee) 
	 * login functie zet de user variabele juist;
	 * 
	 * -> group overzicht
	 * getGroups functie, geen parameters , haalt alle groupen van user op
	 * 
	 * - > kies groep fetch random vraag van die groep
	 * 
	 * -> los op -> juist/fout
	 * 
	 * -> fetch random question 
	 * 
	 * 
	 * 
	 */
	
	
	private Facade(){
		dbFac = new DatabaseFactory();
		db = new MySQLDatabase();
	}
	
	public void setDatabase(Database db){
		if(db != null) this.db = db;
	}
	
	public static Facade getInstance(){
		
		if(instance == null){
			instance = new Facade();
		}
		
		return instance;
	}
	
	/*
	 * currentGroup is active group
	 * get random question from group
	 * 
	 */	
	public Question getRandomQuestion() throws dbException{
		currentQuestion =  db.getRandomQuestion(currentGroup.getId());
		return currentQuestion;
	}
	
	public Question getQuestion(int id) throws dbException{
		return db.getQuestion(id);
	}
	

	/*
	 * 
	 * registreer
	 * makt nieuwe user aan en voegt toe aan db
	 * 
	 */
	
	public void addUser(String email, String name, String pw) throws dbException{
		User u = new User(email,name,pw);
		db.addUser(u);
		
	}
	
	public void addUserToGroup(int groupId, String email)throws dbException{
		
		User u = db.getUser(email);
		Group group = db.getGroup(groupId);
		
		group.addUser(u);
		
	}
	
	public void addGroup(String email, String name, boolean canUserInviteFriends, boolean canUserAddQuestions) throws dbException{
		
		User admin = db.getUser(email);
		Group group = new Group(admin,name,canUserInviteFriends,canUserAddQuestions);
		db.addGroup(group);
		
	}
	
	public void addTextQuestion(String answer, String extraInfo, String text) throws dbException{
		
		QuestionText question= new QuestionText(answer, extraInfo, text);
		db.addQuestion(question);
		
	}
	
	public void addImageQuestion(/*Image img,*/ String response)throws dbException{
		//TODO fix diis shit
	}
	
	public boolean login(String email, String pw)throws dbException{
		boolean loggedIn = false;
		
		User u = db.getUser(email);
		
		//als user bestaat
		if(u != null){
			//check voor pw (vrij basic) TODO add encryption algorithm
			if(user.getPw().equals(pw)){
				user = u;
				loggedIn = true;
			}
		}
		
		return loggedIn;
	}
	
	/*
	 * 
	 * add extra iinfo to curent Question
	 * 
	 */
	
	public void addExtraInfo(String info){
		
		currentQuestion.setExtraInfo(currentQuestion.getExtraInfo() + info);
		
	}
	
	public boolean answerQuestion(String answer){ 
		return currentQuestion.answerQuestion(answer);
	}
	
	public void sendMessage(String title, String body, String type, String recipient){
		//TODO implement messages
	}
	
	public boolean isAdmin(int groupId) throws dbException{
		
		User u = db.getGroupAdmin(groupId);
		
		if(u.equals(user)){
			return true;
		} else {
			return false;
		}
		
	}
	
	// get the groups for the currently logged in user
	// stores the group is the groups variable
	
	public ArrayList<Group> getGroups()throws dbException{ 
		groups = db.getGroupsForUser(user.getEmail());
		return groups;
	}
	
	
	public User getUser(String email)throws dbException{
		return db.getUser(email);
	}
}
