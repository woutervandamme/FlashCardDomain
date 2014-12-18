package domain;

public class QuestionFactory {

	public static Question getQuestion(String answer, String question,
			String type) throws DomainException {
		if (type.toUpperCase().equals("IMAGE")) {
			return new QuestionImage(answer, question);
		}
		if (type.toUpperCase().equals("TEXT")) {
			return new QuestionText(answer, question);
		}
		throw new DomainException("Unexisting question type or question type not implemented yet");
	}

	public static String toDatabaseString(Question q) throws DomainException{
		if(q instanceof QuestionText){
			return "TEXT";
		}
		if(q instanceof QuestionImage){
			return "IMAGE";
		}
		throw new DomainException("Unexisting question type or question type not implemented yet");
	}

}
