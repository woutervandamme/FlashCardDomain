package facade;

import java.util.ArrayList;

import domain.Group;
import domain.Question;
import domain.User;

public class Facade {

	public Question getQuestion(Group group, User u){
		return null;
	}
	
	public boolean addUser(String email, String name, String pw){
		return false;
	}
	
	public boolean addUserToGroup(Group group, User user){ return false;}
	
	public void addGroup(User admin, String name,boolean canUserInviteFriends, boolean canUserAddQuestions){}
	
	public void addTextQuestion(String text, String response){}
	
	public void addImageQuestion(/*Image img,*/ String response){
		//TODO fix diis shit
	}
	
	public void addExtraInfo(Question question, String info){}
	
	public boolean answerQuestion(Question question, String response){ return true;}
	
	public void sendMessage(String title, String body, String type){}
	
	public boolean isAdmin(User user, Group group){ return false; }
	
	public ArrayList<Group> getGroups(User user){ return null; }
	
}
