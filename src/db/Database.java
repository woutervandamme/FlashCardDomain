package db;

import java.util.ArrayList;

import domain.Group;
import domain.Message;
import domain.Question;
import domain.User;


public interface Database {

	public User getUser(String email) throws DBException;
	
	// returned random question from a group id 
	public Question getRandomQuestion(int groupId) throws DBException;
	
	public Message getMessage(int id) throws DBException;

	public ArrayList<User> getUsersFromGroup(int id) throws DBException;

	public ArrayList<Message> getMessages(String email) throws DBException;
	
	//Only returns a group object with filled in name and id.
	//Why: In our application this is only used for listing the groupNames
	public ArrayList<Group> getGroupsForUser(String email) throws DBException;
	
	public User getGroupAdmin(int id) throws DBException;
	
	public Group getGroup(int id) throws DBException;
	
	public Question getQuestion(int id) throws DBException;
	
	public void updateQuestion(Question question) throws DBException;
	
	public void updateUser(User user) throws DBException;
	
	public void updateGroup(int id, String name, boolean canInvite, boolean canAdd) throws DBException;
	
	public void removeUserFromGroup(int id, String email) throws DBException;
	
	public void addUserToGroup(int id, String email) throws DBException;
	
	public void addUser(User user) throws DBException;
	
	public void addQuestion(Question question) throws DBException;
	
	public void addGroup(Group group) throws DBException;
	
	public void sendMessage(Message message) throws DBException;

}
