package tsp.invlib.page.handler;

import tsp.invlib.page.handler.context.PageCloseContext;

/**
 * @author TheSilentPro (Silent)
 */
public interface PageCloseHandler extends PageHandler {

    void onClose(PageCloseContext ctx);

}