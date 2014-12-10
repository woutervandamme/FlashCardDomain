package db;

import java.util.ArrayList;

import domain.Group;
import domain.Message;
import domain.Question;
import domain.User;

public interface Database {

	public User getUser(String email);
	
	public Question getRandomQuestion(int id);
	
	public Message getMessage(int id);

	public ArrayList<User> getUsersFromGroup(int id);

	public ArrayList<Message> getMessages(String email);
	
	public ArrayList<Group> getGroupsForUser(String email);
	
	public void updateQuestion(Question question);
	
	public void updateUser(User user);
	
	public void updateGroupName(int id, String name);
	
	public void removeUserFromGroup(int id, String email);
	
	public void addUserToGroup(int id, String email);
	
	public void addUser(User user);
	
	public void addQuestion(Question question);
	
	public void addGroup(Group group);
	
	public void sendMessage(Message message);

}
