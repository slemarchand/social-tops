<import resource="classpath:/alfresco/templates/org/alfresco/import/alfresco-util.js">

/**
 * People List component GET method
 */
function main()
{
   // Call the correct repo script depending on the mode
   var mode = args.mode, site = args.site, countFilter = args.countFilter, 
      typeFilter = args.typeFilter, dateFilter = args.dateFilter;
	
   // Call the repo for the top people
   var json = remote.call("/social-tops/top-people?count=" + countFilter 
		   + "&type=" + typeFilter 
		   + "&date=" + dateFilter 
		   + "&site=" + site);
   
   var items = [],
      totalResults = 0;
   
   if (json.status == 200)
   {
      // Create javascript objects from the repo response
      var obj = eval('(' + json + ')');
      if (obj)
      {
         totalResults = obj.length;
         items = obj;

         var item, userStatus;
         for (var i = 0, j = items.length; i < j; i++)
         {
            item = items[i];
            userStatus = item.person.userStatus;
            if (typeof userStatus != "undefined" && userStatus.length > 0)
            {
               item.person.userStatusRelativeTime = AlfrescoUtil.relativeTime(item.person.userStatusTime.iso8601);
            }
         }
      }
   }
   
   // Prepare the model
   model.items = items;
}

main();