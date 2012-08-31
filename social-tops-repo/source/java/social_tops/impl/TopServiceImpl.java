package social_tops.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.site.SiteInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateMidnight;
import org.springframework.beans.factory.config.PropertiesFactoryBean;

import com.thoughtworks.xstream.XStream;

import social_tops.TopActivityPostDAO;
import social_tops.TopItem;
import social_tops.TopQuery;
import social_tops.TopQuery.TopCriterion;
import social_tops.ibatis.TopActivityPostDAOImpl;
import social_tops.TopService;
import social_tops.TopUser;

public class TopServiceImpl implements TopService {

	private static Log logger = LogFactory.getLog(TopServiceImpl.class);
	
	private PersonService personService;
	private TopActivityPostDAO topActivityPostDAO;
	private Properties properties;
	
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	public void setTopActivityPostDAO(TopActivityPostDAO topActivityPostDAO) {
		this.topActivityPostDAO = topActivityPostDAO;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	
	private Map<String, List<TopCriterion>> criteriaMap;
	
	public void init() throws IOException {
		
		criteriaMap = new HashMap<String, List<TopCriterion>>();
		
		buildCriteriaMap();
	}
	
	public List<TopItem> listTopItems(int count, String type,
			int date, SiteInfo site) {
		
		TopQuery topQuery = new TopQuery();
		
		// Site
		topQuery.setSite(site.getShortName());
		
		// Date
		DateMidnight dm = new DateMidnight();
		dm = dm.minusDays(date);
		topQuery.setDate(dm.toDate());
		
		// Criteria
		List<TopCriterion> criteria = criteriaMap.get(type);
		if(criteria == null) {
			throw new IllegalArgumentException("No criteria found for type '" + type + "'");
		}
		topQuery.setCriteria(criteria);
		
		List<TopUser> results = topActivityPostDAO.executeTopQuery(topQuery );

		List<TopItem> topItems = new ArrayList<TopItem>(Math.min(results.size(), count));
		int position = 1;
		for (TopUser topUser : results) {
			
			String userName = topUser.getUserId();
			
			NodeRef node = personService.getPerson(userName);
			
			int score = topUser.getScore();
			
			TopItem topItem = new TopItem(position, score, node);
			topItems.add(topItem);
			
			position++;
			
			if(position > count) {
				break;
			}
		}
		
		return topItems;
	}

	// TODO : à supprimer
	/*
	private List<TopCriterion> buildCriteriaForMostActivePeople() {
		
		
		
		List<TopCriterion> criteria = new LinkedList<TopCriterion>();
		criteria.add(new TopCriterion("org.alfresco.comments.comment-created", 1));
		criteria.add(new TopCriterion("org.alfresco.documentlibrary.files-added", 1));
		criteria.add(new TopCriterion("org.alfresco.documentlibrary.file-liked", 1));
		criteria.add(new TopCriterion("org.alfresco.documentlibrary.file-added", 1));
		return criteria;
	}
	
	private List<TopCriterion> buildCriteriaForMostTalkativePeople() {
		List<TopCriterion> criteria = new LinkedList<TopCriterion>();
		criteria.add(new TopCriterion("org.alfresco.comments.comment-created", 1));
		return criteria;
	}*/
	
	private void buildCriteriaMap() {
		String propertyFilter = "social-tops.weights.";
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			String fullKey = (String)entry.getKey();
			if(fullKey.startsWith(propertyFilter)) {
				String key = fullKey.substring(propertyFilter.length());
				String value = (String)entry.getValue();
				
				int separatorIndex = key.indexOf('_');
				
				if (separatorIndex > -1 && separatorIndex < key.length() - 1) {
					
					String topType = key.substring(0, separatorIndex);
					String activityType = key.substring(separatorIndex + 1);
					
					int weight = 0;
					try {
						weight = Integer.parseInt(value);
						
						addCriterion(topType, activityType, weight);
						
					} catch (NumberFormatException e) {
						logger.error("Invalid value '" + value + "' for key '" + fullKey + "'");
					}	
							
				} else {
					logger.error("Invalid key '" + fullKey + "'");
				}
			}
		}
		
		if(logger.isDebugEnabled()) {
			XStream xstream = new XStream();
			logger.debug(xstream.toXML(criteriaMap));
		}
	}
	
	private void addCriterion(String topType, String activityType, int weight) {
				
		List<TopCriterion> criteria = criteriaMap.get(topType);
		
		if(criteria == null) {
			criteria = new LinkedList<TopCriterion>();
			criteriaMap.put(topType, criteria);
		}
		
		criteria.add(new TopCriterion(activityType, weight));
	}
}
