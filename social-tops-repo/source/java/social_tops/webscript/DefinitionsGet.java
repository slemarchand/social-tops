package social_tops.webscript;

import java.util.HashMap;
import java.util.Map;

import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import social_tops.definition.TopDefinitionsDAO;

public class DefinitionsGet extends DeclarativeWebScript {

	private static final String XML_PRELUDE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

	private static final CharSequence XMLNS = "xmlns=\"http://slemarchand.github.org/social-tops/definitions/1.0\"";

	private TopDefinitionsDAO topDefinitionsDAO;

	public void setTopDefinitionsDAO(TopDefinitionsDAO topDefinitionsDAO) {
		this.topDefinitionsDAO = topDefinitionsDAO;
	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req,
			Status status, Cache cache) {
		Map<String, Object> model = new HashMap<String, Object>();

		String xml = topDefinitionsDAO.getDefinitionsAsXml();
		
		// Xml simplification to be compatible with E4X
		xml = xml.replace(XML_PRELUDE, "");
		xml = xml.replace(XMLNS, "");

		model.put("xml", xml);

		return model;
	}

}
