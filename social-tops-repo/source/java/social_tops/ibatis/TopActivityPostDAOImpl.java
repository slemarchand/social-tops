package social_tops.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.domain.activities.ibatis.ActivitiesDAOImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;

import social_tops.TopActivityPostDAO;
import social_tops.TopQuery;
import social_tops.TopUser;

public class TopActivityPostDAOImpl extends ActivitiesDAOImpl implements TopActivityPostDAO {

	private static Log logger = LogFactory.getLog(TopActivityPostDAOImpl.class);
	
	@Override
	public List<TopUser> executeTopQuery(TopQuery topQuery) {
		List<TopUser> results = (List<TopUser>)template.selectList("social_tops.top.top_query", topQuery);
		
		if(logger.isDebugEnabled()) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("topQuery", topQuery);
			m.put("results", results);
			XStream xstream = new XStream();
			logger.debug(xstream.toXML(m));
		}
		
		return results;
	}
	
}
