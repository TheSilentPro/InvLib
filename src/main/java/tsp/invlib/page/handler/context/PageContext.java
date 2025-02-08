package tsp.invlib.page.handler.context;

import tsp.invlib.page.Page;

/**
 * Represents a context for a {@link tsp.invlib.page.handler.PageHandler}.
 *
 * @author TheSilentPro (Silent)
 * @param <T> The event
 */
public class PageContext<T> {

    private final T event;
    private final Page page;

    public PageContext(T event, Page page) {
        this.event = event;
        this.page = page;
    }

    public T event() {
        return event;
    }

    public Page page() {
        return page;
    }

}