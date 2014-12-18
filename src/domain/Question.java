package domain;

public abstract class Question {

	private int id;
	
	private String answer,extraInfo;
	
	public abstract boolean solveQuestion(String answer);
	
	public Question(String answer, String extraInfo){
		setAnswer(answer);
		setExtraInfo(extraInfo);
	}
	public Question(String answer){
		setAnswer(answer);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String response) {
		this.answer = response;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	
	public boolean answerQuestion(String answer){
		
		if(this.getAnswer().equals(answer)){
			return true;
		}
		
		return false;
	}
	
	public abstract String getQuestion();
	
	public abstract void setQuestion(String question);
	
	
	
}
