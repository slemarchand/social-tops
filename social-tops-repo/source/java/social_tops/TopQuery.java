package social_tops;

import java.util.Date;
import java.util.List;

public class TopQuery {
	
	private String site;
	private Date date;
	private List<TopCriterion> criteria;
	
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<TopCriterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<TopCriterion> criteria) {
		this.criteria = criteria;
	}

	public static class TopCriterion {
		
		private String activity;
		private int weight;
		
		public TopCriterion(String activity, int weight) {
			super();
			this.activity = activity;
			this.weight = weight;
		}
		
		public String getActivity() {
			return activity;
		}
		public void setActivity(String activity) {
			this.activity = activity;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
	}
}
