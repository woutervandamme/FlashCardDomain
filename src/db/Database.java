package db;

import java.util.ArrayList;

import domain.Group;
import domain.Message;
import domain.Question;
import domain.User;


public interface Database {

	public User getUser(String email) throws dbException;
	
	// returned random question from a group id 
	public Question getRandomQuestion(int groupId) throws dbException;
	
	public Message getMessage(int id) throws dbException;

	public ArrayList<User> getUsersFromGroup(int id) throws dbException;

	public ArrayList<Message> getMessages(String email) throws dbException;
	
	//Only returns a group object with filled in name and id.
	//Why: In our application this is only used for listing the groupNames
	public ArrayList<Group> getGroupsForUser(String email) throws dbException;
	
	public User getGroupAdmin(int id) throws dbException;
	
	public Group getGroup(int id) throws dbException;
	
	public Question getQuestion(int id) throws dbException;
	
	public void updateQuestion(Question question) throws dbException;
	
	public void updateUser(User user) throws dbException;
	
	public void updateGroupName(int id, String name) throws dbException;
	
	public void removeUserFromGroup(int id, String email) throws dbException;
	
	public void addUserToGroup(int id, String email) throws dbException;
	
	public void addUser(User user) throws dbException;
	
	public void addQuestion(Question question) throws dbException;
	
	public void addGroup(Group group) throws dbException;
	
	public void sendMessage(Message message) throws dbException;

}
