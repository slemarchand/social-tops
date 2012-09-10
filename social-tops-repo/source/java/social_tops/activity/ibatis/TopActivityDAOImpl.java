package social_tops.activity.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.domain.activities.ibatis.ActivitiesDAOImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;

import social_tops.activity.ActivityScoreQuery;
import social_tops.activity.TopActivityDAO;
import social_tops.activity.TopQuery;
import social_tops.activity.UserActivityScore;
import social_tops.activity.UserScore;

public class TopActivityDAOImpl extends ActivitiesDAOImpl implements TopActivityDAO {

	private static Log logger = LogFactory.getLog(TopActivityDAOImpl.class);
	
	@Override
	public List<UserScore> executeTopQuery(TopQuery topQuery) {
		List<UserScore> results = (List<UserScore>)template.selectList("social_tops.top.top_query", topQuery);
		
		if(logger.isDebugEnabled()) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("topQuery", topQuery);
			m.put("results", results);
			XStream xstream = new XStream();
			logger.debug(xstream.toXML(m));
		}
		
		return results;
	}
	
	@Override
	public List<UserActivityScore> executeActivityScoreQuery(ActivityScoreQuery activityScoreQuery) {
		
		List<UserActivityScore> results = (List<UserActivityScore>)template.selectList("social_tops.top.activity_score_query", activityScoreQuery);
		
		if(logger.isDebugEnabled()) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("activityScoreQuery", activityScoreQuery);
			m.put("results", results);
			XStream xstream = new XStream();
			logger.debug(xstream.toXML(m));
		}
		
		return results;
	}
	
}
