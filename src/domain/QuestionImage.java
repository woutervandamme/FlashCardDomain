package domain;

public class QuestionImage extends Question {
	public String questionPath;

	public QuestionImage(String answer, String extraInfo) {
		super( answer, extraInfo);
		// TODO Auto-generated constructor stub
	}
	public QuestionImage(String answer) {
		super(answer);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean solveQuestion(String answer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getQuestion() {
		return questionPath;
	}

	@Override
	public void setQuestion(String question) {
		questionPath = question;
	}

	//TODO add image
	
}
