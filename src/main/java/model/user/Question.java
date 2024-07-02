package model.user;

public class Question {

	public static final String[] questions = {
			"What is the name of your favourite book?",
			"What is the name of your favourite movie?",
			"What is the name of your favourite song?",
			"What is the name of your favourite food?",
			"What is the name of your favourite animal?",
			"What is the name of your favourite colour?",
			"What is the name of the city you were born in?",
			"What is the name of your first school?",
			"What is the name of the first online game you played?",
			"What is the name of the first country you visited?"
	};

	private final String question;
	private final String answer;

	public Question(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return this.question;
	}

	public boolean isAnswerCorrect(String answer) {
		return this.answer.equals(answer);
	}

}
