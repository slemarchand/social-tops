package social_tops.definition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class TopCriterion {
	
	@XmlAttribute(required=true)
	private String activity;
	@XmlAttribute(required=true)
	private int weight;
	
	public TopCriterion() {
		super();
	}
	
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