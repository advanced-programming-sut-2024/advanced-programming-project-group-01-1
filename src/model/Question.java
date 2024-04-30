package model;

public class Question {

	private final String question;
	private final String answer;

	public Question(String question, String answer) {
		System.out.println(answer.length());
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
