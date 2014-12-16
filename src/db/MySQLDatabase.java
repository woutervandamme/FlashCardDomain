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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
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
			user = getUser(result.getString("admin"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void updateQuestion(Question question) throws dbException{
		// TODO Auto-generated method stub

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
			addUser.executeUpdate();
			
			//dbConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return question;
	}

	private ArrayList<User> convertToUserList(ResultSet result) throws dbException{
		ArrayList<User> users = new ArrayList<User>();
		try {
			while (result.next()) {
				users.add(getUser(result.getString("UserID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public Question getQuestion(int id) throws dbException{
		// TODO Auto-generated method stub
		return null;
	}

}
