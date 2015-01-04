package facade;

import java.util.ArrayList;

import db.Database;
import db.MySQLDatabase;
import db.DBException;
import domain.DomainException;
import domain.Group;
import domain.Message;
import domain.Question;
import domain.QuestionFactory;
import domain.QuestionText;
import domain.User;

public class Facade {

	// this is the current user
	
	private static Facade instance = null;
	private User user;
	
	private Database db;
	
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
		db = null;
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
	

	public Question getRandomQuestion(int id) throws DBException{
		currentQuestion =  db.getRandomQuestion(id);
		return currentQuestion;
	}
	
	public Question getQuestion(int id) throws DBException{
		return db.getQuestion(id);
	}
	
	public Group getGroup(int id) throws DBException{
		return db.getGroup(id);
	}
	
	public User getGroupAdmin(int groupId) throws DBException{
		return db.getGroupAdmin(groupId);
	}

	
	public ArrayList<Group> getGroupsForUser(String email) throws DBException{
		return db.getGroupsForUser(email);
	}

	/*
	 * 
	 * registreer
	 * makt nieuwe user aan en voegt toe aan db
	 * 
	 */
	
	public void addUser(String email, String name, String pw) throws DBException{
		User u = new User(email,name,pw);
		db.addUser(u);
		
	}
	
	public void addUserToGroup(int groupId, String email)throws DBException{
		db.addUserToGroup(groupId, email);
	}
	
	
	public void addGroup(String email, String name, boolean canUserInviteFriends, boolean canUserAddQuestions) throws DBException{
		
		Group group = new Group(user,name,canUserInviteFriends,canUserAddQuestions);
		db.addGroup(group);
		
	}
	
	public void addQuestion(String answer, String extraInfo, String question,String type,int groupID) throws DBException{
			Question q = null;
			try {
				q = QuestionFactory.getQuestion(answer, question, type);
			} catch (DomainException e) {
				throw new DBException("Something went wrong adding your question",e);
			}
			q.setExtraInfo(extraInfo);
		db.addQuestion(q,groupID);
		
	}
	
	
	public boolean login(String email, String pw)throws DBException{
		boolean loggedIn = false;
		User u =null;
		
		if(db != null){
			u = db.getUser(email);
		}else{
			throw new DBException("No database available");
		}
		
		//als user bestaat
		if(u != null){
			//check voor pw (vrij basic) TODO add encryption algorithm
			if(u.getPw().equals(pw)){
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
	
	public ArrayList<Message> getMessagesForUser(String email) throws DBException{
		return db.getMessages(email);
	}
	
	public boolean isAdmin(int groupId) throws DBException{
		
		User u = db.getGroupAdmin(groupId);
		
		if(u.equals(user)){
			return true;
		} else {
			return false;
		}
		
	}
	
	public User getCurrentUser(){
		return user;
	}
	
	// get the groups for the currently logged in user
	// stores the group is the groups variable
	
	public ArrayList<Group> getGroups()throws DBException{ 
		groups = db.getGroupsForUser(user.getEmail());
		return groups;
	}
	
	
	public User getUser(String email)throws DBException{
		return db.getUser(email);
	}
	
	public void updateQuestion(Question question) throws DBException {
		db.updateQuestion(question);
	}
	
	public void updateGroup(int id, String name,boolean canInvite, boolean canAdd) throws DBException {
		db.updateGroup(id, name,canInvite,canAdd);
	}
	
	public void updateUser(String screenName, String password) throws DBException {
		user.setName(screenName);
		user.setPw(password);
		db.updateUser(user);
	}
	
	
}
