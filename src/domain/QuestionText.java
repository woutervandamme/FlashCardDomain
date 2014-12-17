package domain;

public class QuestionText extends Question {

	public String question;

	public QuestionText( String answer, String extraInfo,String question, QuestionType type) {
		super(answer, extraInfo, type);
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
