package social_tops.webscript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

import social_tops.TopItem;
import social_tops.TopService;

public class TopGet extends DeclarativeWebScript {

	private DateTimeFormatter DTF = ISODateTimeFormat.dateTime();

	private SiteService siteService;
	private TopService topService;
	
	public void setSiteService(SiteService siteService) {
		this.siteService =siteService;
	}
	
	public void setTopService(TopService topService) {
		this.topService = topService;
	}
	
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req,
			Status status, Cache cache) {
		
		// Grab top count
		int count;
		String strCount = req.getParameter("count");
		try {
			count = Integer.parseInt(strCount);
		} catch (NumberFormatException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST,
					"Value for argument 'count' is not a number");
		}
		
		// Grab top type
		String type = req.getParameter("type");
		if(type == null || type.trim().length() == 0) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST,
					"Value for argument 'type' is missing");
		}
		
		// Grab top begin date
		int date;
		String strDate = req.getParameter("date");
		try {
			date = Integer.parseInt(strDate);
		} catch (NumberFormatException e) {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST,
					"Value for argument 'date' is not a number");
		}
		
		// Grab the related site
		SiteInfo site = null;
		String siteName = req.getParameter("site");
		if(siteName != null && siteName.trim().length() > 0) {
			site = siteService.getSite(siteName);
			if (site == null) {
				throw new WebScriptException(Status.STATUS_NOT_FOUND,
						"No Site found with that short name");
			}
		} else {
			throw new WebScriptException(Status.STATUS_BAD_REQUEST,
					"Value for argument 'site' is missing");
		}

		// Process
		List<TopItem> topItems = topService.listTopItems(count, type, date, site);
		
		int size = topItems.size();
		
		// Process it ready for FreeMarker

		Map<Integer, Object> scores = new HashMap<Integer, Object>(size);
		Map<Integer, Object> nodes = new HashMap<Integer, Object>(size);
		
		for (TopItem topItem : topItems) {
			
			int pos = topItem.getPosition();
			int score =  topItem.getScore();
			NodeRef node =  topItem.getNode();
			
			scores.put(pos, score);
			nodes.put(pos, node);
		}

		// Pass the details to freemarker
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("site", site);
		model.put("scores", scores);
		model.put("nodes", nodes);

		return model;
	}
}
