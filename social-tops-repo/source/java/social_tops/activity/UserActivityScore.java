package social_tops.activity;

import social_tops.service.CriterionScore;

public class UserActivityScore extends CriterionScore {
	
	private String activity;
	
	private String userId;

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
