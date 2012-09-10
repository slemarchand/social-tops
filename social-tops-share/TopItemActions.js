/**
* SocialTops widget namespace.
* 
* @namespace SocialTops.widget
*/
if (typeof SocialTops.widget == "undefined" || !SocialTops.widget)
{
	SocialTops.widget = {};
}

/**
 * Top item action controller
 *
 * @namespace SocialTops.widget
 * @class SocialTops.widget.TopItemActions
 */

var TOP_ITEM_ACTIONS_OPACITY = 0,
	TOP_ITEM_ACTIONS_OPACITY_FADE_IN_SPEED = 0.4,
	TOP_ITEM_ACTIONS_OPACITY_FADE_OUT_SPEED = 0.1;

(function()
{
   /**
    * YUI Library aliases
    */
   var Dom = YAHOO.util.Dom,
      Event = YAHOO.util.Event,
      Selector = YAHOO.util.Selector;

   /**
    * Top Item Action controller constructor.
    *
    * @return {SocialTops.widget.TopItemActions} The new SocialTops.widget.TopItemActions instance
    * @constructor
    */
   SocialTops.widget.TopItemActions = function TopItemActions_constructor(htmlId)
   {
      return SocialTops.widget.TopItemActions.superclass.constructor.call(this, "SocialTops.widget.TopItemActions", htmlId, ["selector"]);
   };

   YAHOO.extend(SocialTops.widget.TopItemActions, Alfresco.component.Base,
   {
      /**
       * DOM node of top item
       * Looks for first child DIV of dashlet with class="dashlet" and attach to this
       *
       * @property dashlet
       * @type object
       * @default null
       */
      topItem: null,
      
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
      onReady: function TopItemActions_onReady()
      {
         this.topItem = Dom.get(this.id);
         if (this.topItem)
         {
            this.actionsNode = Selector.query("div.topItemActions", this.topItem, true);
            if (YAHOO.env.ua.ie > 0)
            {
               // IE doesn't handle the fading in/out very well so we won't do it. 
            }
            else
            {
               Dom.setStyle(this.actionsNode, "opacity", TOP_ITEM_ACTIONS_OPACITY);
            }

            // Reverse the order of the arrays so that the first entry is furthest to the left...
            this.options.actions.reverse();
            // Iterate through the array of actions creating a node for each one...
            for (var i = 0; i < this.options.actions.length; i++)
            {
               var currAction = this.options.actions[i];
               if (currAction.cssClass && currAction.bubbleOnClick)
               {
                  var currActionNode = document.createElement("div");  // Create the node
                  if (currAction.tooltip)
                  {
                     Dom.setAttribute(currActionNode, "title", currAction.tooltip);
                  }
                  Dom.addClass(currActionNode, "topItemActionIcon");
                  Dom.addClass(currActionNode, currAction.cssClass);   // Set the class (this should add the icon image
                  this.actionsNode.appendChild(currActionNode);        // Add the node to the parent

                  if (currAction.id)
                  {
                     currActionNode.id = this.id + currAction.id;
                  }

                  var _this = this;

                  var balloon = Alfresco.util.createBalloon(this.id,
                  {
                     html: currAction.bubbleOnClick.message,
                     width: "30em"
                  });
                  
                  Event.addListener(currActionNode, "click", balloon.show, balloon, true);
               }
               else
               {
                  Alfresco.logger.warn("TopItemActions_onReady: Action is not valid.");
               }
            }

            // Add a listener to animate the actions...
            Event.addListener(this.topItem, "mouseover", this._fadeIn, this);
            Event.addListener(this.topItem, "mouseout", this._fadeOut, this);
         }
         else
         {
            // It's not possible to set up the actions without the topItem
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
      _fadeOut: function TopItemActions__fadeOut(e, me)
      {
         if (YAHOO.env.ua.ie > 0 && YAHOO.env.ua.ie < 9)
         {
            me.actionsNode.style.display = "none";
         }
         else
         {
            // Only fade out if the mouse has left the item entirely
            if (!Dom.isAncestor(me.dashlet, Event.getRelatedTarget(e)))
            {
               var fade = new YAHOO.util.Anim(me.actionsNode,
               {
                  opacity:
                  {
                     to: TOP_ITEM_ACTIONS_OPACITY
                  }
               }, TOP_ITEM_ACTIONS_OPACITY_FADE_OUT_SPEED);
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
      _fadeIn: function TopItemActions__fadeIn(e, me)
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
            }, TOP_ITEM_ACTIONS_OPACITY_FADE_IN_SPEED);
            fade.animate();
         }
      }
   });
})();