<#import "item.lib.ftl" as itemLib />
<#list items as i>
   <#assign id="${dashletId}_list_item_${i.position}">	
      <div class="detail-list-item" id="${id}">
         <div class="avatar">
            <img src="${url.context}/proxy/alfresco/slingshot/profile/avatar/${i.person.userName?url}" alt="Avatar" />
         </div>
         <div class="person">
            <h3>
               <a href="${url.context}/page/user/${i.person.userName?url}/profile" class="theme-color-1">${i.person.firstName?html} <#if i.person.lastName??>${i.person.lastName?html}</#if></a> 
               <span class="score">(<@itemLib.points value=i.score.totalScore />)</span>
               <span class="topItemActions"></span> 
            </h3>
   <#if i.person.userStatus??>
            <div class="user-status">${(i.person.userStatus!"")?html} <span class="time">(<span class="relativeTime">${(i.person.userStatusTime.iso8601!"")?html}</span>)</span></div>
   </#if>
         </div>
         <div class="clear"></div>
         <@itemLib.itemInfos item=i />   
      </div>
</#list>