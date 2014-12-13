package domain;

import java.util.ArrayList;

public class Group {

	private String name;
	
	private User admin;
	
	private ArrayList<User> users;
	
	private ArrayList<Question> questions;

	private boolean userAddQuestion, userInviteFriends;
	
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean canUserAddQuestion() {
		return userAddQuestion;
	}

	public void setUserAddQuestion(boolean userAddQuestion) {
		this.userAddQuestion = userAddQuestion;
	}

	public boolean canUserInviteFriends() {
		return userInviteFriends;
	}

	public void setUserInviteFriends(boolean userInviteFriends) {
		this.userInviteFriends = userInviteFriends;
	}

	
	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
    /*
     * 
     * User Section crud
     * 
     * 
     */
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	public void addUser(User user){
		if(!users.contains(user)){
			users.add(user);
		}
	}
	
	public void removeUser(User user){
		users.remove(user);
	}
	
	
	/*
	 * 
	 * 
	 * Question section crud
	 * 
	 * 
	 */
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question question){
		if(!questions.contains(question)){
			questions.add(question);
		}
	}

	public void removeQuestion(Question question){
		questions.remove(question);
	}
	
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
