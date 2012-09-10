import javax.xml.bind.JAXBException;

import social_tops.definition.impl.TopDefinitionsDAOImpl;


public class TestTopDefinitionsDAOImpl {
	
	public static void main(String[] args) throws Exception, JAXBException {
		TopDefinitionsDAOImpl topDefinitionsDAOImpl = new TopDefinitionsDAOImpl();
		topDefinitionsDAOImpl.setDefinitionsUrl("file:D:/opensource/alfresco/social-tops/social-tops-repo/config/alfresco/module/social_tops/definitions/topDefinitions.xml");
		topDefinitionsDAOImpl.init();
		System.out.println(topDefinitionsDAOImpl.getDefinitions());
		System.out.println(topDefinitionsDAOImpl.getDefinitionsAsXml());
	}

}
