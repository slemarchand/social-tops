Social Tops for Alfresco 4.0
===================================

Author: Sébastien Le Marchand

This project defines a dashlet showing "Top People" based on their activities, with configurable policy for scores calculation.

For further details, go to the wiki: https://github.com/slemarchand/social-tops/wiki.

Installation
------------

The component has been developed to install on top of an existing Alfresco
Community 4.0e installation, with a MySQL database : only this configuration 
have be tested (in next release PostgreSQL support will be added).

The social-tops-repo.jar needs to be copied into the Alfresco repository:

	tomcat/webapps/alfresco/WEB-INF/lib/
  
The other file social-tops-share.jar needs to be copied to the 
corresponding folder in the Share webapp:

	tomcat/webapps/share/WEB-INF/lib/
 
or in the following directory :

	tomcat/shared/lib/
 
In addition, you need to add or update some settings in the following file : 

	tomcat/shared/classes/alfresco-global.properties
	
like this to deactive activities post cleaner :

	# activities post cleaner
	activities.post.cleaner.startDelayMins=2147483647 
	activities.post.cleaner.repeatIntervalMins=2147483647 

and you can optionnaly override score calculation policy in this same file :

	# weights for 'most active people' top
	social-tops.weights.mostActivePeople_org.alfresco.documentlibrary.file-liked=1
	social-tops.weights.mostActivePeople_org.alfresco.comments.comment-created=2
	social-tops.weights.mostActivePeople_org.alfresco.documentlibrary.files-added=12
	social-tops.weights.mostActivePeople_org.alfresco.documentlibrary.file-added=6
	social-tops.weights.mostActivePeople_org.alfresco.documentlibrary.file-updated=3

	# weights for 'most talkative people' top
	social-tops.weights.mostTalkativePeople_org.alfresco.comments.comment-created=2
	
Building
--------

To build the individual JAR files, run the following command from the base 
project directory.

    ant -Dalfresco.sdk.dir=c:\dev\alfresco-community-sdk-4.0.e clean dist-jar

The command should build a JAR file named javascript-console-repo.jar or
javascript-console-share.jar in the 'dist' directory within your project.

There also is the social-tops-dist target which builds both jar files and 
amp files.

To deploy the extension files into a local Tomcat instance for testing, you can 
use the hotcopy-tomcat-jar task. You will need to set the tomcat.home
property in Ant.

    ant -Dtomcat.home=C:\alfresco-community-4.0.e\tomcat clean hotcopy-tomcat-jar
    
Once you have run this you will need to restart Tomcat so that the classpath 
resources in the JAR file are picked up.