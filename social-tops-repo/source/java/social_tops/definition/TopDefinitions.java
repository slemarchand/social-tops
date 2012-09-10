package social_tops.definition;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="social-tops", namespace= "http://slemarchand.github.org/social-tops/definitions/1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class TopDefinitions {

	public static String NAMESPACE = "http://slemarchand.github.org/social-tops/definitions/1.0";
	
	@XmlElement(name="count", required=true, nillable=false)
	@XmlElementWrapper(name="counts", required=true, nillable=false)
	private List<TopCount> counts;

	@XmlElement(name="type", required=true, nillable=false)
	@XmlElementWrapper(name="types", required=true, nillable=false)
	private List<TopType> types;

	@XmlElement(name="range", required=true, nillable=false)
	@XmlElementWrapper(name="ranges", required=true, nillable=false)
	private List<TopRange> ranges;	

	public List<TopCount> getCounts() {
		return counts;
	}

	public void setCounts(List<TopCount> counts) {
		this.counts = counts;
	}

	public List<TopType> getTypes() {
		return types;
	}

	public void setTypes(List<TopType> types) {
		this.types = types;
	}

	public List<TopRange> getRanges() {
		return ranges;
	}

	public void setRanges(List<TopRange> ranges) {
		this.ranges = ranges;
	}
	
	
}
