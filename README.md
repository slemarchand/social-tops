Social Tops for Alfresco 4.0
===================================

Author: Sébastien Le Marchand

This project defines a dashlet showing "Top People" based on their activities, 
with configurable top definitions (including scores calculation policies).

For further details, go to the wiki: 
https://github.com/slemarchand/social-tops/wiki.

Installation
------------

The component has been developed to install on top of an existing Alfresco 
Enterprise 4.0.2 instance, with a MySQL database : only this configuration 
have be tested (because this extension use custom SQL queries, the database 
type matter).

The social-tops-repo.jar needs to be copied into the Alfresco repository:

	tomcat/webapps/alfresco/WEB-INF/lib/
  
The other file social-tops-share.jar needs to be copied to the 
corresponding folder in the Share webapp:

	tomcat/webapps/share/WEB-INF/lib/
 
or in the following directory :

	tomcat/shared/lib/
 
In addition, you *MUST* add or update the following settings in your global 
configuration file, in order to deactivate activities post cleaner : 

	# activities post cleaner
	activities.post.cleaner.startDelayMins=2147483647 
	activities.post.cleaner.repeatIntervalMins=2147483647 

Your global configuration file are located at :
	
	tomcat/shared/classes/alfresco-global.properties	
	
In the previous configuration file, you can also specify a file with your own 
top definitions and score calculation policies (see original topDefinitions.xml
file for model):
	
	social-tops.definitions=classpath:alfresco/extension/myOwnTopDefinitions.xml
	
Building
--------

To build the individual JAR files, run the following command from the base 
project directory.

    ant -Dalfresco.sdk.dir=c:\dev\alfresco-entreprise-sdk-4.0.2 clean dist-jar

The command should build a JAR file named javascript-console-repo.jar or
javascript-console-share.jar in the 'dist' directory within your project.

There also is the social-tops-dist target which builds both jar files and 
amp files.

To deploy the extension files into a local Tomcat instance for testing, you 
can use the hotcopy-tomcat-jar task. You will need to set the tomcat.home
property in Ant.

    ant -Dtomcat.home=C:\alfresco-entreprise-4.0.2\tomcat clean hotcopy-tomcat-jar
    
Once you have run this you will need to restart Tomcat so that the classpath 
resources in the JAR file are picked up.