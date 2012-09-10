package social_tops.service;

import social_tops.definition.TopCriterion;

public class CriterionScore {
	
	private TopCriterion criterion; 
	
	private int hits;
	
	private int score;

	public TopCriterion getCriterion() {
		return criterion;
	}

	public void setCriterion(TopCriterion criterion) {
		this.criterion = criterion;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}
