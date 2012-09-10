package social_tops.service;

import java.util.LinkedList;
import java.util.List;

public class Score {
	
	private int totalScore;
	
	private List<CriterionScore> criterionScores = new LinkedList<CriterionScore>();

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public List<CriterionScore> getCriterionScores() {
		return criterionScores;
	}

	public void setCriterionScores(List<CriterionScore> criterionScores) {
		this.criterionScores = criterionScores;
	}
	
	

}
