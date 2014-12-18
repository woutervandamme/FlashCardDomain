package domain;

public enum MessageType {

	FEEDBACK, EXTRA_INFO, INVITE;

	public static MessageType toMessageType(String type) throws DomainException{
		if(type.equals("FEEDBACK")){
			return FEEDBACK;
		}
		if(type.equals("EXTRA_INFO")){
			return EXTRA_INFO;
		}
		if(type.equals("INVITE")){
			return INVITE;
		}
		throw new DomainException("Unexisting type");	
	}
	
}
