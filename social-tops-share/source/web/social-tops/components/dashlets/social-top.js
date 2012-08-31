/**
* SocialTops root namespace.
* 
* @namespace SocialTops
*/
if (typeof SocialTops == "undefined" || !SocialTops)
{
   var SocialTops = {};
}

/**
* SocialTops dashlet namespace.
* 
* @namespace SocialTops.dashlet
*/
if (typeof SocialTops.dashlet == "undefined" || !SocialTops.dashlet)
{
	SocialTops.dashlet = {};
}

/**
 * Dashboard SocialTop common component.
 * 
 * @namespace SocialTops.dashlet
 * @class SocialTops.dashlet.SocialTop
 */
(function()
{
   /**
    * YUI Library aliases
    */
   var Dom = YAHOO.util.Dom,
      Event = YAHOO.util.Event,
      Selector = YAHOO.util.Selector;

   /**
    * Preferences
    */
   var PREFERENCES_SOCIAL_TOP = "social-tops.share.social-top",
       PREF_TYPE = ".type",
       PREF_RANGE = ".range",
       PREF_COUNT = ".count";
   
   /**
    * Dashboard SocialTop constructor.
    * 
    * @param {String} htmlId The HTML id of the parent element
    * @return {SocialTops.dashlet.SocialTop} The new component instance
    * @constructor
    */
   SocialTops.dashlet.SocialTop = function SocialTop_constructor(htmlId)
   {
      SocialTops.dashlet.SocialTop.superclass.constructor.call(this, "SocialTops.dashlet.SocialTop", htmlId, ["button", "container"]);
      
      // Preferences service
      this.services.preferences = new Alfresco.service.Preferences();
      
      return this;
   };

   YAHOO.extend(SocialTops.dashlet.SocialTop, Alfresco.component.Base,
   {
      /**
       * Object container for initialization options
       *
       * @property options
       * @type object
       */
      options:
      {
         /**
          * Dashlet mode
          * 
          * @property mode
          * @type string
          * @default "site"
          */
         mode: "site",

         /**
          * Current siteId.
          * 
          * @property siteId
          * @type string
          */
         siteId: "",
         
         /**
          * Component region ID.
          * 
          * @property regionId
          * @type string
          */
         regionId: ""
      },

      /**
       * Top list DOM container.
       * 
       * @property topList
       * @type object
       */
      topList: null,
      
      /**
       * Fired by YUI when parent element is available for scripting
       * @method onReady
       */
      onReady: function SocialTop_onReady()
      {
         var me = this;
         
         // Create dropdown filter widgets
         this.widgets.range = Alfresco.util.createYUIButton(this, "range", this.onDateFilterChanged,
         {
            type: "menu",
            menu: "range-menu",
            lazyloadmenu: false
         });
         
         this.widgets.type = Alfresco.util.createYUIButton(this, "type", this.onTypeFilterChanged,
         {
            type: "menu",
            menu: "type-menu",
            lazyloadmenu: false
         });
         
         this.widgets.count = Alfresco.util.createYUIButton(this, "count", this.onCountFilterChanged,
         {
            type: "menu",
            menu: "count-menu",
            lazyloadmenu: false
         });
         
         // The top list container
         this.topList = Dom.get(this.id + "-topList");
         
         // Load preferences to override default filter and range
         this.widgets.range.set("label", this.msg("filter.7days"));
         this.widgets.range.value = "7";
         this.widgets.type.set("label", this.msg("filter.mostActivePeople"));
         this.widgets.type.value = "mostActivePeople";
         this.widgets.count.set("label", this.msg("filter.top5"));
         this.widgets.count.value = "5";

         
         this.services.preferences.request(this.buildPreferences(),
         {
            successCallback:
            {
               fn: function(p_oResponse)
               {
                  //var countPreference = Alfresco.util.findValueByDotNotation(p_oResponse.json, this.buildPreferences(PREF_COUNT), "");
            	   var countPreference = "5";
            	   if (countPreference !== null)
                  {
                     this.widgets.count.value = countPreference;
                     // set the correct menu label
                     var menuItems = this.widgets.count.getMenu().getItems();
                     for (index in menuItems)
                     {
                        if (menuItems.hasOwnProperty(index))
                        {
                           if (menuItems[index].value === countPreference)
                           {
                              this.widgets.count.set("label", menuItems[index].cfg.getProperty("text"));
                              break;
                           }
                        }
                     }
                  }
                  
                  // var rangePreference = Alfresco.util.findValueByDotNotation(p_oResponse.json, this.buildPreferences(PREF_RANGE), "7");
            	  var rangePreference = "7";
            	  if (rangePreference !== null)
                  {
                     this.widgets.range.value = rangePreference;
                     // set the correct menu label
                     var menuItems = this.widgets.range.getMenu().getItems();
                     for (index in menuItems)
                     {
                        if (menuItems.hasOwnProperty(index))
                        {
                           if (menuItems[index].value === rangePreference)
                           {
                              this.widgets.range.set("label", menuItems[index].cfg.getProperty("text"));
                              break;
                           }
                        }
                     }
                  }
                  
                  //var typePreference = Alfresco.util.findValueByDotNotation(p_oResponse.json, this.buildPreferences(PREF_TYPE), "all");
                  var typePreference = "mostActivePeople";
                  if (typePreference !== null)
                  {
                     this.widgets.type.value = typePreference;
                     // set the correct menu label
                     var menuItems = this.widgets.type.getMenu().getItems();
                     for (index in menuItems)
                     {
                        if (menuItems.hasOwnProperty(index))
                        {
                           if (menuItems[index].value === typePreference)
                           {
                              this.widgets.type.set("label", menuItems[index].cfg.getProperty("text"));
                              break;
                           }
                        }
                     }
                  }
                  // Display the toolbar now that we have selected the filter
                  Dom.removeClass(Selector.query(".toolbar div", this.id, true), "hidden");
                  // Populate the top list
                  this.populateTopList(this.widgets.range.value, this.widgets.type.value, this.widgets.count.value);
               },
               scope: this
            },
            failureCallback:
            {
               fn: function()
               {
                  // Display the toolbar now that we have selected the filter
                  Dom.removeClass(Selector.query(".toolbar div", this.id, true), "hidden");
                  // Populate the top list
                  this.populateTopList(this.widgets.range.value, this.widgets.type.value, this.widgets.count.value);
               },
               scope: this
            }
         });
         
         
      },
      
      /**
       * Build the Social Top dashlet preferences name string with optional suffix.
       * The component region ID and the current siteId (if any) is used as part of the
       * preferences name - to uniquely identify the preference within the site or user
       * dashboard context.
       * 
       * @method buildPreferences
       * @param suffix {string} optional suffix to append to the preferences name
       */
      buildPreferences: function SocialTop_buildPreferences(suffix)
      {
         var opt = this.options;
         return PREFERENCES_SOCIAL_TOP + "." + opt.regionId + (opt.siteId ? ("." + opt.siteId) : "") + (suffix ? suffix : "");
      },
      
      /**
       * Populate the top list via Ajax request
       * @method populateTopList
       */
      populateTopList: function SocialTop_populateTopList(dateFilter, typeFilter, countFilter)
      {
         // Load the top list
         Alfresco.util.Ajax.request(
         {
            url: Alfresco.constants.URL_SERVICECONTEXT + "social-tops/components/dashlets/people/list",
            dataObj:
            {
               site: this.options.siteId,
               mode: this.options.mode,
               dateFilter: dateFilter,
               typeFilter: typeFilter,
               countFilter: countFilter
            },
            successCallback:
            {
               fn: this.onListLoaded,
               scope: this,
               obj: dateFilter
            },
            failureCallback:
            {
               fn: this.onListLoadFailed,
               scope: this
            },
            scope: this,
            noReloadOnAuthFailure: true
         });
      },
      
      /**
       * List loaded successfully
       * @method onListLoaded
       * @param p_response {object} Response object from request
       */
      onListLoaded: function SocialTop_onListLoaded(p_response, p_obj)
      {
         var html = p_response.serverResponse.responseText;
         if (YAHOO.lang.trim(html).length === 0)
         {
            this.topList.innerHTML = Dom.get(this.id + "-empty").innerHTML;
         }
         else
         {
            this.topList.innerHTML = html;
         }
      },

      /**
       * List load failed
       * @method onListLoadFailed
       */
      onListLoadFailed: function SocialTop_onListLoadFailed()
      {
         this.topList.innerHTML = '<div class="detail-list-item first-item last-item">' + this.msg("label.load-failed") + '</div>';
      },

      /**
       * YUI WIDGET EVENT HANDLERS
       * Handlers for standard events fired from YUI widgets, e.g. "click"
       */

      /**
       * Date drop-down changed event handler
       *
       * @method onDateFilterChanged
       * @param p_sType {string} The event
       * @param p_aArgs {array} Event arguments
       */
      onDateFilterChanged: function SocialTop_onDateFilterChanged(p_sType, p_aArgs)
      {
         var menuItem = p_aArgs[1];
         
         if (menuItem)
         {
            this.widgets.range.set("label", menuItem.cfg.getProperty("text"));
            this.widgets.range.value = menuItem.value;
            this.populateTopList(this.widgets.range.value, this.widgets.type.value, this.widgets.count.value);
            this.services.preferences.set(this.buildPreferences(PREF_RANGE), this.widgets.range.value);
         }
      },
      
      /**
       * Exclusion drop-down changed event handler
       *
       * @method onTypeFilterChanged
       * @param p_sType {string} The event
       * @param p_aArgs {array} Event arguments
       */
      onTypeFilterChanged: function SocialTop_onTypeFilterChanged(p_sType, p_aArgs)
      {
         var menuItem = p_aArgs[1];
         
         if (menuItem)
         {
            this.widgets.type.set("label", menuItem.cfg.getProperty("text"));
            this.widgets.type.value = menuItem.value;
            this.populateTopList(this.widgets.range.value, this.widgets.type.value, this.widgets.count.value);
            this.services.preferences.set(this.buildPreferences(PREF_TYPE), this.widgets.type.value);
         }
      },
      
      /**
       * Count drop-down changed event handler
       *
       * @method onCountFilterChanged
       * @param p_sType {string} The event
       * @param p_aArgs {array} Event arguments
       */
      onCountFilterChanged: function SocialTop_onCountFilterChanged(p_sType, p_aArgs)
      {
         var menuItem = p_aArgs[1];
         
         if (menuItem)
         {
            this.widgets.count.set("label", menuItem.cfg.getProperty("text"));
            this.widgets.count.value = menuItem.value;
            this.populateTopList(this.widgets.range.value, this.widgets.type.value, this.widgets.count.value);
            this.services.preferences.set(this.buildPreferences(PREF_COUNT), this.widgets.count.value);
         }
      }
   });
})();