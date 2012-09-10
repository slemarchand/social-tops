package social_tops.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.site.SiteInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateMidnight;

import social_tops.activity.ActivityScoreQuery;
import social_tops.activity.TopActivityDAO;
import social_tops.activity.TopQuery;
import social_tops.activity.UserActivityScore;
import social_tops.activity.UserScore;
import social_tops.definition.TopCriterion;
import social_tops.definition.TopDefinitionsDAO;
import social_tops.definition.TopType;
import social_tops.service.Score;
import social_tops.service.TopItem;
import social_tops.service.TopService;

import com.thoughtworks.xstream.XStream;

public class TopServiceImpl implements TopService {

	private static Log logger = LogFactory.getLog(TopServiceImpl.class);
	
	private PersonService personService;
	private TopActivityDAO topActivityDAO;
	private TopDefinitionsDAO topDefinitionsDAO;
	private Properties properties;
	
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	public void setTopActivityDAO(TopActivityDAO topActivityPostDAO) {
		this.topActivityDAO = topActivityPostDAO;
	}
	public void setTopDefinitionsDAO(TopDefinitionsDAO topDefinitionsDAO) {
		this.topDefinitionsDAO = topDefinitionsDAO;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private Map<String, Map<String,TopCriterion>> typeActivityCriteriaMap;
	
	public void init() throws IOException {
			
		typeActivityCriteriaMap = new HashMap<String, Map<String,TopCriterion>>();
		
		List<TopType> types = topDefinitionsDAO.getDefinitions().getTypes();
		for (TopType t : types) {
			List<TopCriterion> criterionList = t.getCriteria();
			
			Map<String, TopCriterion> activityCriterionMap = new HashMap<String, TopCriterion>();
			for (TopCriterion criterion : criterionList) {
				activityCriterionMap.put(criterion.getActivity(), criterion);
			}
			
			activityCriterionMap = Collections.unmodifiableMap(activityCriterionMap);
			
			typeActivityCriteriaMap.put(t.getId(),activityCriterionMap);
		}
		
		typeActivityCriteriaMap = Collections.unmodifiableMap(typeActivityCriteriaMap);
	}
	

	/**
	 * 
	 */
	public List<TopItem> listTopItems(int count, String type,
			int date, SiteInfo site) {
		
		ActivityScoreQuery activityScoreQuery = new ActivityScoreQuery();
		
		TopQuery topQuery = activityScoreQuery;
		
		// Site
		topQuery.setSite(site.getShortName());
		
		// Date
		DateMidnight dm = new DateMidnight();
		dm = dm.minusDays(date);
		topQuery.setDate(dm.toDate());
		
		// Criteria
		Map<String, TopCriterion> activityCriterionMap = typeActivityCriteriaMap.get(type);
		if(activityCriterionMap == null) {
			throw new IllegalArgumentException("Unknown top type '" + type + "'");
		}
		List<TopCriterion> criteria = new ArrayList<TopCriterion>(activityCriterionMap.values());
		
		topQuery.setCriteria(criteria);
		
		List<UserScore> topResults = topActivityDAO.executeTopQuery(topQuery );

		int topSize = Math.min(topResults.size(), count);
		List<TopItem> topItems = new ArrayList<TopItem>(topSize);
		
		if(topSize > 0) {
		
			Map<String, TopItem> topItemsMap = new HashMap<String, TopItem>(topSize);
			List<String> userIds = new ArrayList<String>(topSize);
			int position = 1;
			for (UserScore topUser : topResults) {
				
				String userName = topUser.getUserId();
				
				NodeRef node = personService.getPerson(userName);
				
				int score = topUser.getScore();
				
				TopItem topItem = new TopItem(position, score, node);
				topItems.add(topItem);
			
				topItemsMap.put(userName, topItem);
				
				userIds.add(userName);
				
				position++;
				
				if(position > count) {
					break;
				}
			}
			
			activityScoreQuery.setUserIds(userIds);
			
			List<UserActivityScore> activityScoreResults = topActivityDAO.executeActivityScoreQuery(activityScoreQuery);
			
			for (UserActivityScore userActivityScore : activityScoreResults) {
				
				String activity = userActivityScore.getActivity();
				TopCriterion criterion = activityCriterionMap.get(activity);
				userActivityScore.setCriterion(criterion);
				
				String userName = userActivityScore.getUserId();
				TopItem topItem = topItemsMap.get(userName);
				Score score = topItem.getScore();
				score.getCriterionScores().add(userActivityScore);
			}
		
		}
		
		if(logger.isDebugEnabled()) {
			XStream xstream = new XStream();
			logger.debug("listTopItems()=" + xstream.toXML(topItems));
		}
		
		return topItems;
	}
}
