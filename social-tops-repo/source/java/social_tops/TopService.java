package social_tops;

import java.util.List;

import org.alfresco.service.cmr.site.SiteInfo;
import org.joda.time.DateTime;

public interface TopService {
	
	public List<TopItem> listTopItems(int count, String type,
			int date, SiteInfo site);
}
