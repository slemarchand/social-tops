function getFilters(filterName)
{
   var myConfig = new XML(config.script),
       filters = [];

   for each (var xmlFilter in myConfig[filterName].filter)
   {
      filters.push(
      {
         value: xmlFilter.@value.toString(),
         label: xmlFilter.@label.toString()
      });
   }

   return filters;
}

model.filterCounts = getFilters("filter-count");
model.filterTypes = getFilters("filter-type");
model.filterRanges = getFilters("filter-range");
