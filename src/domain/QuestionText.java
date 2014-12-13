package domain;

public class QuestionText extends Question {

	
	public String question;

	public String getQuestion() {
		return question;
	}

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
		
		if(answer.equals(this.getAnswer())){
			return true;
		}
		return false;
	}
	
	
}
