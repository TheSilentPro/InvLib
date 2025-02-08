package tsp.invlib.page.handler;

import tsp.invlib.page.handler.context.PageOpenContext;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageOpenHandler extends PageHandler {

    void onOpen(PageOpenContext ctx);

}