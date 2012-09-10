<#assign id = args.htmlid>
<#assign jsid = args.htmlid?js_string>
<script type="text/javascript">//<![CDATA[
(function()
{
   var socialTop = new SocialTops.dashlet.SocialTop("${jsid}").setOptions(
   {
      siteId: "${page.url.templateArgs.site!""}",
      mode: "site",
      regionId: "${args['region-id']?js_string}",
      defaultCountValue: "${defaultCountValue}",
      defaultRangeValue: "${defaultRangeValue}",
      defaultTypeValue: "${defaultTypeValue}"
      
   }).setMessages(${messages});
   
   new Alfresco.widget.DashletResizer("${jsid}", "${instance.object.id}");

   new Alfresco.widget.DashletTitleBarActions("${jsid}").setOptions(
   {
      actions:
      [
         {
            cssClass: "help",
            bubbleOnClick:
            {
               message: "${msg("dashlet.help")?js_string}"
            },
            tooltip: "${msg("dashlet.help.tooltip")?js_string}"
         }
      ]
   });
})();
//]]></script>

<div class="dashlet social-top">
   <div class="title">${msg("header")}</div>
   <div class="toolbar flat-button">
      <div class="hidden">
      
         <span class="align-left yui-button yui-menu-button" id="${id}-count">
            <span class="first-child">
               <button type="button" tabindex="0"></button>
            </span>
         </span>
         <select id="${id}-count-menu">
         <#list filterCounts as filter>
            <option value="${filter.value?html}">
            ${filter.label?html}</option>
         </#list>
         </select>
      
         <span class="align-left yui-button yui-menu-button" id="${id}-type">
            <span class="first-child">
               <button type="button" tabindex="0"></button>
            </span>
         </span>
         <select id="${id}-type-menu">
         <#list filterTypes as filter>
            <option value="${filter.value?html}">
            ${filter.label?html}</option>
         </#list>
         </select>
         
         <span class="align-left yui-button yui-menu-button" id="${id}-range">
            <span class="first-child">
               <button type="button" tabindex="0"></button>
            </span>
         </span>
         <select id="${id}-range-menu">
         <#list filterRanges as filter>
            <option value="${filter.value?html}">
            ${filter.label?html}</option>
         </#list>
         </select>
         
         <div class="clear"></div>
      </div>
   </div>
   <div id="${id}-topList" class="body scrollableList" <#if args.height??>style="height: ${args.height}px;"</#if>></div>
</div>

<#-- Empty results list template -->
<div id="${id}-empty" style="display: none">
   <div class="empty"><h3>${msg("empty.title")}</h3><span>${msg("empty.description")}</span></div>
</div>