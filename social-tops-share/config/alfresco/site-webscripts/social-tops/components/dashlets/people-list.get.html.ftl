<div class="body scrollableList" <#if args.height??>style="height: ${args.height}px;"</#if>>
<#list items as i>
      <div class="detail-list-item">
         <div class="avatar">
            <img src="${url.context}/proxy/alfresco/slingshot/profile/avatar/${i.person.userName?url}" alt="Avatar" />
         </div>
         <div class="person">
            <h3><a href="${url.context}/page/user/${i.person.userName?url}/profile" class="theme-color-1">${i.person.firstName?html} <#if i.person.lastName??>${i.person.lastName?html}</#if></a><span class="score">(${i.score} ${msg('label.points')?html})</span></h3>
   <#if i.person.userStatus??>
            <div class="user-status">${(i.person.userStatus!"")?html} <span class="time">(${(i.person.userStatusRelativeTime!"")?html})</span></div>
   </#if>
         </div>
         <div class="clear"></div>
      </div>
</#list>
</div>