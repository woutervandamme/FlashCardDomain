package domain;

public class Message {

	
	private String title, body;
	private int id;
	private MessageType type;
	private User receiver;
	
	public Message(int id, String title, String body, MessageType type, User receiver){
		
		setId(id);
		setTitle(title);
		setBody(body);
		setType(type);
		setReceiver(receiver);
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public void setType(MessageType type) {
		this.type = type;
	}
	
	public User getReceiver() {
		return receiver;
	}
	
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
}
