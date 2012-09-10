<import resource="classpath:/alfresco/templates/org/alfresco/import/alfresco-util.js">

/**
 * People List component GET method
 */
function main()
{
   // Note: mode not used in this version 
   var dashletId = args.dashletId, mode = args.mode, site = args.site, 
      countFilter = args.countFilter, 
      typeFilter = args.typeFilter, 
      dateFilter = args.dateFilter;
	
   // Call the repo for the top people
   var json = remote.call("/social-tops/top-people?count=" + countFilter 
		   + "&type=" + typeFilter 
		   + "&date=" + dateFilter 
		   + "&site=" + site);
   
   var items = [];
   
   if (json.status == 200)
   {
      // Create javascript objects from the repo response
      var obj = eval('(' + json + ')');
      if (obj)
      {
         items = obj;
      }
   }
   
   // Prepare the model
   model.dashletId = dashletId;
   model.items = items;
}

main();