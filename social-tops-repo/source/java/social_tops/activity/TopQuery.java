package social_tops.activity;

import java.util.Date;
import java.util.List;

import social_tops.definition.TopCriterion;

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
}
