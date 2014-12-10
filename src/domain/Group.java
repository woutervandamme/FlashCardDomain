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

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
