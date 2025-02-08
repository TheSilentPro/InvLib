package tsp.invlib.page.handler;

import tsp.invlib.page.handler.context.ButtonClickContext;

/**
 * @author TheSilentPro (Silent)
 */
public interface ButtonClickHandler extends PageHandler {

    void onClick(ButtonClickContext ctx);

}