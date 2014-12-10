package db;

import java.util.ArrayList;

import domain.Group;
import domain.Message;
import domain.Question;
import domain.User;

public class MySQLDatabase implements Database {

	@Override
	public User getUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question getRandomQuestion(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message getMessage(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> getUsersFromGroup(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Message> getMessages(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Group> getGroupsForUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateQuestion(Question question) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGroupName(int id, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromGroup(int id, String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUserToGroup(int id, String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addQuestion(Question question) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

}
