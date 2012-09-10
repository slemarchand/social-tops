function main()
{
	// Call the repo for the definitions
	var xml = remote.call("/social-tops/definitions"); 

	if(xml.status == 200) {
		
		var myConfig = new XML(xml);
		
		model.filterCounts = getCountFilters(myConfig);
		model.filterTypes = getTypeFilters(myConfig);
		model.filterRanges = getRangeFilters(myConfig);
		
		model.defaultCountValue = model.filterCounts[0].value;
		model.defaultTypeValue = model.filterTypes[0].value;
		model.defaultRangeValue = model.filterRanges[0].value;
		
	}
}

function getCountFilters(config)
{
   var filters = [];

   for each (var count in config.counts.*)
   {
      filters.push(
      {
         value: count.@value.toString(),
         label: count.title.toString()
      });
   }

   return filters;
}

function getTypeFilters(config)
{
   var filters = [];

   for each (var xmlElement in config.types.*)
   {
      filters.push(
      {
         value: xmlElement.@id.toString(),
         label: xmlElement.title.toString()
      });
   }

   return filters;

   return filters;
}

function getRangeFilters(config)
{
   var filters = [];

   for each (var xmlElement in config.ranges.*)
   {
      filters.push(
      {
         value: xmlElement.@days.toString(),
         label: xmlElement.title.toString()
      });
   }

   return filters;
}

main();
