package social_tops.definition.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import social_tops.definition.TopDefinitions;
import social_tops.definition.TopDefinitionsDAO;

import com.thoughtworks.xstream.XStream;

public class TopDefinitionsDAOImpl implements TopDefinitionsDAO {

	private static Log logger = LogFactory.getLog(TopDefinitionsDAOImpl.class);
	
	private String definitionsUrl;
	
	private TopDefinitions definitions;
	
	private String definitionsAsXml;

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
		
	public void setDefinitionsUrl(String definitionsUrl) {
		this.definitionsUrl = definitionsUrl;
	}

	@Override
	public TopDefinitions getDefinitions() {
		return definitions;
	}
	
	@Override
	public String getDefinitionsAsXml() {
		return definitionsAsXml;
	}
	
	public void init() throws IOException, JAXBException {
		
		logger.info("Loading definitions url: " + definitionsUrl);

		final JAXBContext context = JAXBContext.newInstance(TopDefinitions.class);
		
		final Resource resource = resourcePatternResolver.getResource(definitionsUrl);
		
		final InputStream inputStream = resource.getInputStream();
		final Unmarshaller unmarshaller = context.createUnmarshaller();
        definitions = (TopDefinitions) unmarshaller.unmarshal(inputStream);
        
        // We marshall object to get XML again because we want xml without 
        // comments nor formatting
        StringWriter writer = new StringWriter();
        final Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(definitions, writer);
        definitionsAsXml = writer.toString();
        
		if(logger.isDebugEnabled()) {
			XStream xstream = new XStream();
			logger.debug(xstream.toXML(definitions));
		}
	}
	

}
