package domain;

public class QuestionText extends Question {

	public String question;

	public QuestionText( String answer, String extraInfo,String question) {
		super(answer, extraInfo);
		setQuestion(question);
	}
	public QuestionText( String answer,String question) {
		super(answer);
		setQuestion(question);
	}
	
	public String getQuestion() {
		return question;
	}
	
	@Override
	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public boolean solveQuestion(String answer) {
		/*
		 * 
		 * waarschijnlijk een of andere marge van correctheid kunnen inbouwen?
		 * 
		 */		
		return answer.equals(this.getAnswer());
	}

	
	
}
