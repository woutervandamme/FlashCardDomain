package db;

import java.util.ArrayList;
import java.util.Random;
//Domain imports
import domain.Group;
import domain.Message;
import domain.MessageType;
import domain.Question;
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
	public User getUser(String email) throws dbException{
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
			throw new dbException("user not found");
		}
		return user;
	}

	@Override
	public Question getRandomQuestion(int groupId) throws dbException{
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
			throw new dbException("no random question found for this group");

		}

		return question;
	}

	@Override
	public Message getMessage(int id) throws dbException{
		Message message = null;
		try {
			PreparedStatement getMessage = dbConnection
					.prepareStatement("select * from Message where ID = ?");
			getMessage.setInt(1, id);
			ResultSet result = getMessage.executeQuery();
			message = convertToMessage(result);
			if(message == null){
				throw new dbException("Message not found, are you sure you have the right ID?");
			}
		} catch (SQLException e) {
			throw new dbException("Error while getting messages",e);
		}
		return message;
	}

	@Override
	public ArrayList<User> getUsersFromGroup(int id) throws dbException{
		ArrayList<User> users = null;
		try {
			PreparedStatement getUsersFromGroup = dbConnection
					.prepareStatement("select * from UsersInGroup where groupID = ?");
			getUsersFromGroup.setInt(1, id);
			ResultSet result = getUsersFromGroup.executeQuery();
			users = convertToUserList(result);
			if(users.size() == 0){
				throw new dbException("No users in this group, are you sure this group exists?");
			}
		} catch (SQLException e) {
			throw new dbException("Error while getting users from group",e);
		}
		return users;
	}

	@Override
	public ArrayList<Message> getMessages(String email) throws dbException{
		ArrayList<Message> messages = null;
		try {
			PreparedStatement getMessagesForUser = dbConnection
					.prepareStatement("select * from Message where UserID = ?");
			getMessagesForUser.setString(1, email);
			ResultSet result = getMessagesForUser.executeQuery();
			messages = convertToMessageList(result);
			//No check needed for users can have an empty messagebox
		} catch (SQLException e) {
			throw new dbException("Error while getting messages for user",e);
		}
		return messages;
	}

	@Override
	public ArrayList<Group> getGroupsForUser(String email) throws dbException{
		ArrayList<Group> groups = null;
		try {
			PreparedStatement getGroupsForUser = dbConnection
					.prepareStatement("select * from UsersInGroup where userID = ?");
			getGroupsForUser.setString(1, email);
			ResultSet result = getGroupsForUser.executeQuery();
			groups = convertToGroupList(result);
			//No check needed for users can have no groups. (Ex. New user)
		} catch (SQLException e) {
			throw new dbException("Error while getting groups for user",e);
			
		}
		return groups;
	}

	@Override
	public User getGroupAdmin(int id) throws dbException{
		User user = null;
		try {
			PreparedStatement getGroupAdmin = dbConnection
					.prepareStatement("select admin from Group where ID = ?");
			getGroupAdmin.setInt(1, id);
			ResultSet result = getGroupAdmin.executeQuery();
			if(!result.next()){
				throw new dbException("Can't find admin for group, does your group exist?");
			}
			user = getUser(result.getString("admin"));
		} catch (SQLException e) {
			throw new dbException("Error while getting group admin",e);
		}
		return user;
	}

	@Override
	public void updateQuestion(Question question) throws dbException{
		try {
			PreparedStatement addQuestion = dbConnection
					.prepareStatement("insert into Question(answer,extraInfo,question,type) values(?,?,?)");
			addQuestion.setString(1, question.getAnswer());
			addQuestion.setString(2, question.getExtraInfo());
			addQuestion.setString(3, question.getQuestion());
			if(addQuestion.executeUpdate() < 1){
				throw new dbException("failed to add Question");
			}
			//dbConnection.commit();
		} catch (SQLException e) {
			throw new dbException("Error while adding user",e);
		}

	}

	@Override
	public void updateUser(User user) throws dbException{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGroupName(int id, String name) throws dbException{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUserFromGroup(int id, String email) throws dbException{
		// TODO Auto-generated method stub

	}

	@Override
	public void addUserToGroup(int id, String email) throws dbException{
		getGroup(id);

	}

	@Override
	public void addUser(User user) throws dbException{
		try {
			PreparedStatement addUser = dbConnection
					.prepareStatement("insert into User(email,name,password) values(?,?,?)");
			addUser.setString(1, user.getEmail());
			addUser.setString(2, user.getName());
			addUser.setString(3, user.getPw());
			if(addUser.executeUpdate() < 1){
				throw new dbException("failed to add user");
			}
			//dbConnection.commit();
		} catch (SQLException e) {
			throw new dbException("Error while adding user",e);
		}
		
	}

	@Override
	public void addQuestion(Question question) throws dbException{
		// TODO Auto-generated method stub

	}

	@Override
	public void addGroup(Group group) throws dbException{
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(Message message) throws dbException {
		// TODO Auto-generated method stub

	}

	/**
	 * Utility functions
	 */

	// Could perfectly done in parent method but did split it up for consistency
	private User convertToUser(ResultSet result) throws dbException{
		User user = null;
		try {
			user = new User(result.getString("email"), result.getString("name"),
					result.getString("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new dbException("user convertion went wrong",e);
		}
		return user;
	}

	private Message convertToMessage(ResultSet result) throws dbException{
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
			// TODO Auto-generated catch block
			throw new dbException("message convertion went wrong",e);
		}
		return message;
	}

	private Question convertToQuestion(ResultSet result, int size) throws dbException{
		Question question = null;

		Random r = new Random();
		int row = (int) (size * r.nextFloat());
		try {
			result.absolute(row); // Move cursor to the random generated row

			// TODO program QuestionFactory to create questions

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new dbException("question convertion went wrong",e);
		}
		return question;
	}

	private ArrayList<User> convertToUserList(ResultSet result) throws dbException{
		ArrayList<User> users = new ArrayList<User>();
		int i =0;
		try {
			while (result.next()) {
				users.add(getUser(result.getString("UserID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new dbException("user to list convertion went wrong",e);
		}
		return users;
	}

	private ArrayList<Message> convertToMessageList(ResultSet result)throws dbException {
		ArrayList<Message> messages = new ArrayList<Message>();
		try {
			while (result.next()) {
				messages.add(convertToMessage(result));
				// TODO: Check this -> This could generate problems for I'm not
				// sure if
				// the cursor position will be reset when passing or not.
				// Assuming it will not.
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new dbException("message to list convertion went wrong",e);
		}
		return messages;
	}

	private ArrayList<Group> convertToGroupList(ResultSet result) throws dbException{
		ArrayList<Group> groups = new ArrayList<Group>();
		try {
			while (result.next()) {
				groups.add(getGroup(result.getInt("GroupID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new dbException("group to list convertion went wrong",e);
		}
		return groups;
	}

	public Group getGroup(int groupID) throws dbException {
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
				throw new dbException("Non existing group");
			}
			
		} catch (SQLException e) {
			throw new dbException("Fetching group went wrong",e);
		}
		return group;
	}

	@Override
	public Question getQuestion(int id) throws dbException{
		// TODO Auto-generated method stub
		return null;
	}

}
