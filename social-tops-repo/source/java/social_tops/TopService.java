package social_tops;

import java.util.List;

import org.alfresco.service.cmr.site.SiteInfo;

public interface TopService {
	
	public List<TopItem> listTopItems(int count, String type,
			int date, SiteInfo site);
}
