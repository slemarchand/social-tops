/**
 * Dashlet title bar action controller
 *
 * When creating a new title bar action controller it is necessary to call setOptions with the following
 * attributes in a hash:
 * - actions: an array of the actions to display (see below)
 *
 * Actions:
 * Each action can have the following attributes:
 * - cssClass (required)      : this should be a CSS class that defines a 16x16 image to render as the action icon
 * - tooltip (options)        : this should be a message to use for the hover help tooltip
 * - eventOnClick (optional)  : this is the custom event event that will be fired when the action is clicked
 * - linkOnClick (optional)   : this is URL that the browser will redirect to when the action is clicked
 * - targetOnClick (optional) : this is the URL that the browser display in a new window/tab
 * - bubbleOnClick (optional) : this should be an object containing "message" (String) and "messageArgs" (String array) attributes
 *
 * @namespace Alfresco.widget
 * @class Alfresco.widget.DashletTitleBarActions
 */

var DASHLET_TITLE_BAR_ACTIONS_OPACITY = 0,
   OPACITY_FADE_SPEED = 0.2;

(function()
{
   /**
    * YUI Library aliases
    */
   var Dom = YAHOO.util.Dom,
      Event = YAHOO.util.Event,
      Selector = YAHOO.util.Selector;

   /**
    * Dashlet Title Bar Action controller constructor.
    *
    * @return {Alfresco.widget.DashletTitleBarActions} The new Alfresco.widget.DashletTitleBarActions instance
    * @constructor
    */
   Alfresco.widget.DashletTitleBarActions = function DashletTitleBarActions_constructor(htmlId)
   {
      return Alfresco.widget.DashletTitleBarActions.superclass.constructor.call(this, "Alfresco.widget.DashletTitleBarActions", htmlId, ["selector"]);
   };

   YAHOO.extend(Alfresco.widget.DashletTitleBarActions, Alfresco.component.Base,
   {
      /**
       * DOM node of dashlet
       * Looks for first child DIV of dashlet with class="dashlet" and attach to this
       *
       * @property dashlet
       * @type object
       * @default null
       */
      dashlet: null,

      /**
       * DOM node of dashlet title
       * The first child DIV of dashlet with class="title"
       *
       * @property dashletTitle
       * @type object
       * @default null
       */
      dashletTitle: null,

      /**
       * DOM node of dashlet body
       * Resizer will look for first child DIV of dashlet with class="body" and resize this element
       *
       * @property dashletBody
       * @type object
       * @default null
       */
      dashletBody: null,

      /**
       * The that node containing all the actions nodes. The actions are
       * grouped under a single parent so that only one animation effect needs
       * to be applied.
       *
       * @property actionsNode
       * @type object
       * @default null
       */
      actionsNode: null,

      /**
       * Fired by YUI when parent element is available for scripting.
       * Template initialisation, including instantiation of YUI widgets and event listener binding.
       *
       * @method onReady
       */
      onReady: function DashletTitleBarActions_onReady()
      {
         this.dashlet = Selector.query("div.dashlet", Dom.get(this.id), true);
         this.dashletTitle = Selector.query("div.title", this.dashlet, true);
         this.dashletBody = Selector.query("div.body", this.dashlet, true);
         if (this.dashlet && this.dashletTitle && this.dashletBody)
         {
            this.actionsNode = document.createElement("div");
            Dom.addClass(this.actionsNode, "titleBarActions");  // This class sets the position of the actions.
            if (YAHOO.env.ua.ie > 0)
            {
               // IE doesn't handle the fading in/out very well so we won't do it. 
            }
            else
            {
               Dom.setStyle(this.actionsNode, "opacity", DASHLET_TITLE_BAR_ACTIONS_OPACITY);
            }
          

            // Add the actions node before the dashlet body...
            this.dashlet.insertBefore(this.actionsNode, this.dashletBody);

            // Reverse the order of the arrays so that the first entry is furthest to the left...
            this.options.actions.reverse();
            // Iterate through the array of actions creating a node for each one...
            for (var i = 0; i < this.options.actions.length; i++)
            {
               var currAction = this.options.actions[i];
               if (currAction.cssClass && (currAction.eventOnClick ||
                                           currAction.linkOnClick ||
                                           currAction.targetOnClick ||
                                           currAction.bubbleOnClick))
               {
                  var currActionNode = document.createElement("div");  // Create the node
                  if (currAction.tooltip)
                  {
                     Dom.setAttribute(currActionNode, "title", currAction.tooltip);
                  }
                  Dom.addClass(currActionNode, "titleBarActionIcon");
                  Dom.addClass(currActionNode, currAction.cssClass);   // Set the class (this should add the icon image
                  this.actionsNode.appendChild(currActionNode);        // Add the node to the parent

                  if (currAction.id)
                  {
                     currActionNode.id = this.id + currAction.id;
                  }

                  var _this = this;
                  if (currAction.eventOnClick)
                  {
                     Event.addListener(currActionNode, "click", (function(e)
                     {
                        // If the action is an event then the value passed should be a custom event that
                        // we will simply fire when the action node is clicked...
                        var customEvent = currAction.eventOnClick; // Copy this value as the currAction handle will be reassigned...
                        
                        return function(e)
                        {
                           _this._fadeOut(e, _this);
                           customEvent.fire({});
                        }
                     })());
                  }
                  else if (currAction.linkOnClick)
                  {
                     Event.addListener(currActionNode, "click", (function()
                     {
                        // If the action is a navigation link, then add a listener function that updates
                        // the browsers current location to be the supplied value...
                        var link = currAction.linkOnClick; // Copy this value as the currAction handle will be reassigned...
                        
                        return function()
                        { 
                           window.location = link;
                        };
                     })());
                  }
                  else if (currAction.targetOnClick)
                  {
                     Event.addListener(currActionNode, "click", (function()
                     {
                        // If the action is a target link, then open a new window/tab and set its location
                        // to the supplied value...
                        var target = currAction.targetOnClick; // Copy this value as the currAction handle will be reassigned...
                         
                        return function()
                        {
                           window.open(target);
                        };
                     })());
                  }
                  else if (currAction.bubbleOnClick)
                  {
                     var balloon = Alfresco.util.createBalloon(this.id,
                     {
                        html: currAction.bubbleOnClick.message,
                        width: "30em"
                     });

                     Event.addListener(currActionNode, "click", balloon.show, balloon, true);
                  }
               }
               else
               {
                  Alfresco.logger.warn("DashletTitleBarActions_onReady: Action is not valid.");
               }
            }

            // Add a listener to animate the actions...
            Event.addListener(this.dashlet, "mouseover", this._fadeIn, this);
            Event.addListener(this.dashlet, "mouseout", this._fadeOut, this);
         }
         else
         {
            // It's not possible to set up the actions without the dashlet, its title and the body
         }
      },

      /**
       * Fade the node actions out
       *
       * @method _fadeOut
       * @param e {event} The current event
       * @param me {scope} the context to run in
       * @protected
       */
      _fadeOut: function DashletTitleBarActions__fadeOut(e, me)
      {
         if (YAHOO.env.ua.ie > 0 && YAHOO.env.ua.ie < 9)
         {
            me.actionsNode.style.display = "none";
         }
         else
         {
            // Only fade out if the mouse has left the dashlet entirely
            if (!Dom.isAncestor(me.dashlet, Event.getRelatedTarget(e)))
            {
               var fade = new YAHOO.util.Anim(me.actionsNode,
               {
                  opacity:
                  {
                     to: DASHLET_TITLE_BAR_ACTIONS_OPACITY
                  }
               }, OPACITY_FADE_SPEED);
               fade.animate();
            }
         }
      },

      /**
       * Fade the actions node in
       *
       * @method _fadeIn
       * @param e {event} The current event
       * @param me {scope} the context to run in
       * @protected
       */
      _fadeIn: function DashletTitleBarActions__fadeIn(e, me)
      {
         if (YAHOO.env.ua.ie > 0 && YAHOO.env.ua.ie < 9)
         {
            me.actionsNode.style.display = "block";
         }
         else
         {
            var fade = new YAHOO.util.Anim(me.actionsNode,
            {
               opacity:
               {
                  to: 1
               }
            }, OPACITY_FADE_SPEED);
            fade.animate();
         }
      }
   });
})();