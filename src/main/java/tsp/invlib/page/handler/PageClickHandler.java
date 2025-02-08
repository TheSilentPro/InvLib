package tsp.invlib.page.handler;

import tsp.invlib.page.handler.context.PageClickContext;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageClickHandler extends PageHandler {

    void onClick(PageClickContext ctx);

}