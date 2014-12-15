package facade;

import java.util.ArrayList;

import db.Database;
import db.DatabaseFactory;
import domain.Group;
import domain.Question;
import domain.QuestionText;
import domain.User;

public class Facade {

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
	 * 
	 * 
	 */
	
	
	public Facade(){
		dbFac = new DatabaseFactory();
	}
	
	/*
	 * currentGroup is active group
	 * get random question from group
	 * 
	 */
	
	public Question getQuestion(){
		currentQuestion =  db.getRandomQuestion(currentGroup.getId());
		return currentQuestion;
	}

	/*
	 * 
	 * registreer
	 * makt nieuwe user aan en voegt toe aan db
	 * 
	 */
	
	public void addUser(String email, String name, String pw){
		
		User u = new User(email,name,pw);
		db.addUser(u);
		
	}
	
	public void addUserToGroup(int groupId, String email){
		
		User u = db.getUser(email);
		Group group = db.getGroup(groupId);
		
	}
	
	public void addGroup(String email, String name, boolean canUserInviteFriends, boolean canUserAddQuestions){
		
		User admin = db.getUser(email);
		Group group = new Group(admin,name,canUserInviteFriends,canUserAddQuestions);
		db.addGroup(group);
		
	}
	
	public void addTextQuestion(String answer, String extraInfo, String text){
		
		QuestionText question= new QuestionText(answer, extraInfo, text);
		db.addQuestion(question);
		
	}
	
	public void addImageQuestion(/*Image img,*/ String response){
		//TODO fix diis shit
	}
	
	public boolean login(String email, String pw){
		boolean loggedIn = false;
		
		User u = db.getUser(email);
		
		//als user bestaat
		if(u!= null){
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
	 * 
	 * 
	 */
	
//	public void addExtraInfo(int questionId, String info){
//		
//		question.setExtraInfo(question.getExtraInfo() + info);
//	}
	
	public boolean answerQuestion(String answer){ 
		return currentQuestion.answerQuestion(answer);
	}
	
	public void sendMessage(String title, String body, String type, String recipient){
		
		
		
	}
	
	public boolean isAdmin(int groupId){
		
		User u = db.getGroupAdmin(groupId);
		
		if(u.equals(user)){
			return true;
		} else {
			return false;
		}
		
	}
	
	// get the groups for the currently logged in user
	// stores the group is the groups variable
	
	public ArrayList<Group> getGroups(){ 
		groups = db.getGroupsForUser(user.getEmail());
		return groups;
	}
	
}
