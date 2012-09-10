package social_tops.definition;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class TopType extends Entitled {

	@XmlAttribute(required = true)
	private String id;

	@XmlElement(name = "criterion", required = true, nillable = false)
	List<TopCriterion> criteria;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TopCriterion> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<TopCriterion> criteria) {
		this.criteria = criteria;
	}
}
