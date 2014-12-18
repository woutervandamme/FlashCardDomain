package db;

import java.util.ArrayList;
import java.util.Random;
import domain.DomainException;
//Domain imports
import domain.Group;
import domain.Message;
import domain.MessageType;
import domain.Question;
import domain.QuestionFactory;
import domain.User;

//SQLimports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDatabase implements Database {
	Connection dbConnection = null;

	public MySQLDatabase() {
		try {
			dbConnection = DriverManager.getConnection(ConnectionData.url,
					ConnectionData.user, ConnectionData.password);
			dbConnection.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public User getUser(String email) throws DBException{
		User user = null;
		try {
			PreparedStatement getUser = dbConnection
					.prepareStatement("select * from User where email = ?");
			getUser.setString(1, email);
			ResultSet result = getUser.executeQuery();
			if(result.next()){
				user = convertToUser(result);
			}else{
				//no user like this
			}
		} catch (SQLException e) {
			throw new DBException("user not found");
		}
		return user;
	}

	@Override
	public Question getRandomQuestion(int groupId) throws DBException{
		Question question = null;
		try {
			PreparedStatement getQuestion = dbConnection
					.prepareStatement("select * from QuestionsInGroup where gropuID = ?");
			getQuestion.setInt(1, groupId);
			ResultSet result = getQuestion.executeQuery();

			PreparedStatement sizeStatement = dbConnection
					.prepareStatement("select Count(*) from QuestionsInGroup where gropuID = ?");
			sizeStatement.setInt(1, groupId);
			ResultSet sizeResult = sizeStatement.executeQuery();

			int size = sizeResult.getInt(1);
			question = convertToQuestion(result, size);
		} catch (SQLException e) {
			throw new DBException("no random question found for this group");

		}

		return question;
	}

	@Override
	public Message getMessage(int id) throws DBException{
		Message message = null;
		try {
			PreparedStatement getMessage = dbConnection
					.prepareStatement("select * from Message where ID = ?");
			getMessage.setInt(1, id);
			ResultSet result = getMessage.executeQuery();
			message = convertToMessage(result);
			if(message == null){
				throw new DBException("Message not found, are you sure you have the right ID?");
			}
		} catch (SQLException e) {
			throw new DBException("Error while getting messages",e);
		}
		return message;
	}

	@Override
	public ArrayList<User> getUsersFromGroup(int id) throws DBException{
		ArrayList<User> users = null;
		try {
			PreparedStatement getUsersFromGroup = dbConnection
					.prepareStatement("select * from UsersInGroup where groupID = ?");
			getUsersFromGroup.setInt(1, id);
			ResultSet result = getUsersFromGroup.executeQuery();
			users = convertToUserList(result);
			if(users.size() == 0){
				throw new DBException("No users in this group, are you sure this group exists?");
			}
		} catch (SQLException e) {
			throw new DBException("Error while getting users from group",e);
		}
		return users;
	}

	@Override
	public ArrayList<Message> getMessages(String email) throws DBException{
		ArrayList<Message> messages = null;
		try {
			PreparedStatement getMessagesForUser = dbConnection
					.prepareStatement("select * from Message where UserID = ?");
			getMessagesForUser.setString(1, email);
			ResultSet result = getMessagesForUser.executeQuery();
			messages = convertToMessageList(result);
			//No check needed for users can have an empty messagebox
		} catch (SQLException e) {
			throw new DBException("Error while getting messages for user",e);
		}
		return messages;
	}

	@Override
	public ArrayList<Group> getGroupsForUser(String email) throws DBException{
		ArrayList<Group> groups = null;
		try {
			PreparedStatement getGroupsForUser = dbConnection
					.prepareStatement("select * from UsersInGroup where userID = ?");
			getGroupsForUser.setString(1, email);
			ResultSet result = getGroupsForUser.executeQuery();
			groups = convertToGroupList(result);
			//No check needed for users can have no groups. (Ex. New user)
		} catch (SQLException e) {
			throw new DBException("Error while getting groups for user",e);
			
		}
		return groups;
	}

	@Override
	public User getGroupAdmin(int id) throws DBException{
		User user = null;
		try {
			PreparedStatement getGroupAdmin = dbConnection
					.prepareStatement("select admin from Group where ID = ?");
			getGroupAdmin.setInt(1, id);
			ResultSet result = getGroupAdmin.executeQuery();
			if(!result.next()){
				throw new DBException("Can't find admin for group, does your group exist?");
			}
			user = getUser(result.getString("admin"));
		} catch (SQLException e) {
			throw new DBException("Error while getting group admin",e);
		}
		return user;
	}

	@Override
	public void updateQuestion(Question question) throws DBException{
		try {
			PreparedStatement addQuestion = dbConnection
					.prepareStatement("insert into Question(answer,extraInfo,question,type) values(?,?,?,?)");
			addQuestion.setString(1, question.getAnswer());
			addQuestion.setString(2, question.getExtraInfo());
			addQuestion.setString(3, question.getQuestion());
			addQuestion.setString(4, QuestionFactory.toDatabaseString(question));
			if(addQuestion.executeUpdate() < 1){
				throw new DBException("failed to add Question");
			}
			//dbConnection.commit();
		} catch (SQLException | DomainException e) {
			throw new DBException("Error while adding user",e);
		}

	}

	@Override
	public void updateUser(User user) throws DBException{
		try {
			PreparedStatement updateUser = dbConnection
					.prepareStatement("update user set e-mail = ?,name = ?,pw = ? WHERE e-mail = ?");
			updateUser.setString(1, user.getEmail());
			updateUser.setString(2, user.getName());
			updateUser.setString(3, user.getPw());
			updateUser.setString(4, user.getEmail());
			if(updateUser.executeUpdate() < 1){
				throw new DBException("failed to update user");
			}
			//dbConnection.commit();
		} catch (SQLException e) {
			throw new DBException("Error while updating user",e);
		}

	}

	@Override
	public void updateGroupName(int id, String name) throws DBException{
		try {
		PreparedStatement updateGroupName = dbConnection
				.prepareStatement("update group set name = ? WHERE ID = ?");
		updateGroupName.setString(1, name);
		updateGroupName.setInt(2, id);
		if(updateGroupName.executeUpdate() < 1){
			throw new DBException("failed to update the group name");
		}
		} catch (SQLException e) {
			throw new DBException("Error while changing group",e);
		}

	}

	@Override
	public void removeUserFromGroup(int id, String email) throws DBException{
		try {
			PreparedStatement removeUserFromGroup = dbConnection
					.prepareStatement("DELETE from UsersInGroup where GroupID = ? AND UserID = ?");
			removeUserFromGroup.setInt(1, id);
			removeUserFromGroup.setString(2,email);
			if(removeUserFromGroup.executeUpdate() < 1){
				throw new DBException("failed to remove the user from the group");
			}		
		} catch (SQLException e) {
			throw new DBException("Error while removing the user from the group",e);
		}

	}

	@Override
	public void addUserToGroup(int id, String email) throws DBException{
		try {
		PreparedStatement addUserToGroup = dbConnection
				.prepareStatement("insert into usersInGroup(GroupID,UserID) values (?,?)");
		addUserToGroup.setInt(1, id);
		addUserToGroup.setString(2, email);
		} catch (SQLException e) {
			throw new DBException("Error while adding user to group",e);
		}

	}

	@Override
	public void addUser(User user) throws DBException{
		try {
			PreparedStatement addUser = dbConnection
					.prepareStatement("insert into User(email,name,password) values(?,?,?)");
			addUser.setString(1, user.getEmail());
			addUser.setString(2, user.getName());
			addUser.setString(3, user.getPw());
			if(addUser.executeUpdate() < 1){
				throw new DBException("failed to add user");
			}
			//dbConnection.commit();
		} catch (SQLException e) {
			throw new DBException("Error while adding user",e);
		}
		
	}

	@Override
	public void addQuestion(Question question) throws DBException{
		try {
		PreparedStatement addQuestion = dbConnection
				.prepareStatement("insert into Question(answer,extrainfo,question,type) values(?,?,?,?)");
		addQuestion.setString(1, question.getAnswer());
		addQuestion.setString(2, question.getExtraInfo());
		addQuestion.setString(3, question.getQuestion());
		addQuestion.setString(4, QuestionFactory.toDatabaseString(question));
		if(addQuestion.executeUpdate() < 1){
			throw new DBException("failed to add question");
		}
		} catch (SQLException | DomainException e) {
			throw new DBException("Error while adding question",e);
		}
	}

	@Override
	public void addGroup(Group group) throws DBException{
		try {
		PreparedStatement addGroup = dbConnection
				.prepareStatement("insert into Group(name,admin) values(?,?)");
		addGroup.setString(1, group.getName());
		addGroup.setString(2, group.getAdmin().getEmail());
		if(addGroup.executeUpdate() < 1){
			throw new DBException("failed to add group");
		}
		} catch (SQLException e) {
			throw new DBException("Error while adding group",e);
		}
	}

	@Override
	public void sendMessage(Message message) throws DBException {
		try {
		PreparedStatement addMessage = dbConnection
				.prepareStatement("insert into Group(title,body,type,UserID) values(?,?,?,?)");
		addMessage.setString(1, message.getTitle());
		addMessage.setString(2, message.getBody());
		addMessage.setString(3, message.getType().toString());
		addMessage.setString(3, message.getReceiver().getEmail());
		if(addMessage.executeUpdate() < 1){
			throw new DBException("failed to add group");
		}
		} catch (SQLException e) {
			throw new DBException("Error while adding group",e);
		}

	}

	/**
	 * Utility functions
	 */

	// Could perfectly done in parent method but did split it up for consistency
	private User convertToUser(ResultSet result) throws DBException{
		User user = null;
		try {
			user = new User(result.getString("email"), result.getString("name"),
					result.getString("password"));
		} catch (SQLException e) {
			throw new DBException("user convertion went wrong",e);
		}
		return user;
	}

	private Message convertToMessage(ResultSet result) throws DBException{
		Message message = null;
		try {
			int ID = result.getInt("ID");
			String title = result.getString("title");
			String body = result.getString("body");
			MessageType type;
			if (result.getString("type") == MessageType.FEEDBACK.toString()) {
				type = MessageType.FEEDBACK;
			} else {
				type = MessageType.EXTRA_INFO;
			}
			User receiver = getUser(result.getString("UserID"));
			message = new Message(ID, title, body, type, receiver);
		} catch (SQLException e) {
			throw new DBException("message convertion went wrong",e);
		}
		return message;
	}

	private Question convertToQuestion(ResultSet result, int size) throws DBException{
		Question question = null;

		Random r = new Random();
		int row = (int) (size * r.nextFloat());
		try {
			result.absolute(row);
			

		} catch (SQLException e) {
			throw new DBException("question convertion went wrong",e);
		}
		return question;
	}

	private ArrayList<User> convertToUserList(ResultSet result) throws DBException{
		ArrayList<User> users = new ArrayList<User>();
		try {
			while (result.next()) {
				users.add(getUser(result.getString("UserID")));
			}
		} catch (SQLException e) {
			throw new DBException("user to list convertion went wrong",e);
		}
		return users;
	}

	private ArrayList<Message> convertToMessageList(ResultSet result)throws DBException {
		ArrayList<Message> messages = new ArrayList<Message>();
		try {
			while (result.next()) {
				messages.add(convertToMessage(result));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("message to list convertion went wrong",e);
		}
		return messages;
	}

	private ArrayList<Group> convertToGroupList(ResultSet result) throws DBException{
		ArrayList<Group> groups = new ArrayList<Group>();
		try {
			while (result.next()) {
				groups.add(getGroup(result.getInt("GroupID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DBException("group to list convertion went wrong",e);
		}
		return groups;
	}

	public Group getGroup(int groupID) throws DBException {
		Group group = null;
		try {
			PreparedStatement getGroup = dbConnection
					.prepareStatement("select * from group where id = ?");
			getGroup.setInt(1, groupID);
			ResultSet result = getGroup.executeQuery();
			if(result.next()){
				group = new Group();
				group.setName(result.getString("name"));
				group.setId(result.getInt("ID"));
			}else{
				throw new DBException("Non existing group");
			}
			
		} catch (SQLException e) {
			throw new DBException("Fetching group went wrong",e);
		}
		return group;
	}

	@Override
	public Question getQuestion(int id) throws DBException{
		// TODO Auto-generated method stub
		return null;
	}

}
