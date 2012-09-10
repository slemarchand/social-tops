<#-- Work with a top item JSON object -->
<#macro points value ><#compress>
<#if ((value > 1) || (value < -1)) >
${value} ${msg('label.points')?html}
<#else>
${value} ${msg('label.point')?html}
</#if>
</#compress></#macro>

<#macro itemInfos item >
   <#assign i=item>  
   <div class="item-infos-wrapper">    		
      <div class="social-top">
      <span class="item-infos"> 	   
	     <div class="avatar">
            <img src="${url.context}/proxy/alfresco/slingshot/profile/avatar/${i.person.userName?url}" alt="Avatar" />
         </div>
         <div class="person">
            <h3>
               <a href="${url.context}/page/user/${i.person.userName?url}/profile" class="theme-color-1">${i.person.firstName?html} <#if i.person.lastName??>${i.person.lastName?html}</#if></a> 
            </h3>          
         </div>
         <div class="clear"></div>
         <div class="score">
         	 <h3 class="total">${msg('total.score')?html} <@points value=i.score.totalScore /></h3>
         	 <div class="score-details">
   <#list i.score.criterionScores as cs>      	 
         	 	<div class="criterion">${msg(cs.activity)?html}  &times; ${cs.hits} &times; <@points value=cs.weight /> = <@points value=cs.score /></div> 
   </#list>
         	 </div>
         </div>
     </span>    
     </div>
  </div>       
</#macro>