package tsp.invlib.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tsp.invlib.button.Button;
import tsp.invlib.gui.GUI;
import tsp.invlib.page.Page;

/**
 * Utility methods for working with Bukkit {@link Inventory} objects and custom GUI elements.
 * <p>
 * This class contains methods for filling, outlining, and drawing shapes (lines, squares, rectangles)
 * on inventories and custom GUI pages using {@link Button} objects or {@link ItemStack}s.
 * </p>
 *
 * @author TheSilentPro
 */
public final class InvUtils {

    private InvUtils() {
        throw new IllegalStateException("Utility class must not be initialized.");
    }

    // -------------------------------------------------------------------------
    // ItemStack methods
    // -------------------------------------------------------------------------

    /**
     * Fills all empty slots in the given {@link Inventory} with the specified {@link ItemStack}.
     *
     * @param inventory the inventory to fill
     * @param item      the item to fill empty slots with
     */
    public static void fill(@NotNull Inventory inventory, @NotNull ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                setItem(inventory, i, item);
            }
        }
    }

    /**
     * Fills a specific row in the given {@link Inventory} with the specified {@link ItemStack}.
     *
     * @param inventory the inventory to fill
     * @param item      the item to fill the row with
     * @param row       the row to fill (0-indexed)
     */
    public static void fillRow(@NotNull Inventory inventory, @NotNull ItemStack item, int row) {
        int index = row * 9;
        for (int i = 0; i < 9; i++) {
            int slot = index + i;
            if (slot < inventory.getSize()) {
                setItem(inventory, slot, item);
            }
        }
    }

    /**
     * Fills a specific column in the given {@link Inventory} with the specified {@link ItemStack}.
     *
     * @param inventory the inventory to fill
     * @param item      the item to fill the column with
     * @param column    the column to fill (0-indexed)
     */
    public static void fillColumn(@NotNull Inventory inventory, @NotNull ItemStack item, int column) {
        for (int row = 0; row < 6; row++) {
            int slot = row * 9 + column;
            if (slot < inventory.getSize()) {
                setItem(inventory, slot, item);
            }
        }
    }

    /**
     * Outlines the border of the given {@link Inventory} with the specified {@link ItemStack}.
     *
     * @param inventory the inventory whose border will be outlined
     * @param item      the item to use for the outline
     */
    public static void outline(@NotNull Inventory inventory, @NotNull ItemStack item) {
        int rows = inventory.getSize() / 9;
        int cols = 9;

        // Fill top and bottom rows
        for (int i = 0; i < cols; i++) {
            setItem(inventory, i, item);
            setItem(inventory, rows * 9 - i - 1, item);
        }

        // Fill left and right borders (excluding corners)
        for (int i = 1; i < rows - 1; i++) {
            setItem(inventory, i * 9, item);
            setItem(inventory, i * 9 + cols - 1, item);
        }
    }

    /**
     * Draws a border (outline) around the given {@link Inventory} using the specified {@link ItemStack}.
     *
     * @param inventory the inventory whose border will be drawn
     * @param item      the item to use for the border
     */
    public static void border(@NotNull Inventory inventory, @NotNull ItemStack item) {
        outline(inventory, item);
    }

    // -------------------------------------------------------------------------
    // Button methods for Page
    // -------------------------------------------------------------------------

    /**
     * Fills all empty slots in the given {@link Page} with the specified {@link Button}.
     *
     * @param page   the page to fill
     * @param button the button to fill empty slots with
     */
    public static void fill(@NotNull Page page, @NotNull Button button) {
        for (int i = 0; i < page.getSize(); i++) {
            if (page.getButton(i).isEmpty()) {
                setButton(page, i, button);
            }
        }
    }

    /**
     * Fills all empty slots in the given {@link Page} with the specified {@link ItemStack}.
     *
     * @param page the page to fill
     * @param item the item to fill empty slots with
     */
    public static void fill(@NotNull Page page, @NotNull ItemStack item) {
        for (int i = 0; i < page.getSize(); i++) {
            if (page.getButton(i).isEmpty()) {
                setItem(page, i, item);
            }
        }
    }

    /**
     * Fills a specific row in the given {@link Page} with the specified {@link Button}.
     *
     * @param page   the page to fill
     * @param button the button to fill the row with
     * @param row    the row to fill (0-indexed)
     */
    public static void fillRow(@NotNull Page page, @NotNull Button button, int row) {
        int index = row * 9;
        for (int i = 0; i < 9; i++) {
            int slot = index + i;
            if (slot < page.getSize()) {
                setButton(page, slot, button);
            }
        }
    }

    /**
     * Fills a specific column in the given {@link Page} with the specified {@link Button}.
     *
     * @param page   the page to fill
     * @param button the button to fill the column with
     * @param column the column to fill (0-indexed)
     */
    public static void fillColumn(@NotNull Page page, @NotNull Button button, int column) {
        for (int row = 0; row < 6; row++) {
            int slot = row * 9 + column;
            if (slot < page.getSize()) {
                setButton(page, slot, button);
            }
        }
    }

    /**
     * Outlines the border of the given {@link Page} with the specified {@link Button}.
     *
     * @param page   the page whose border will be outlined
     * @param button the button to use for the outline
     */
    public static void outline(@NotNull Page page, @NotNull Button button) {
        int rows = page.getSize() / 9;
        int cols = 9;

        // Fill top and bottom rows
        for (int i = 0; i < cols; i++) {
            setButton(page, i, button);
            setButton(page, rows * 9 - i - 1, button);
        }

        // Fill left and right borders (excluding corners)
        for (int i = 1; i < rows - 1; i++) {
            setButton(page, i * 9, button);
            setButton(page, i * 9 + cols - 1, button);
        }
    }

    /**
     * Draws a border (outline) around the given {@link Page} using the specified {@link Button}.
     *
     * @param page   the page whose border will be drawn
     * @param button the button to use for the border
     */
    public static void border(@NotNull Page page, @NotNull Button button) {
        outline(page, button);
    }

    // -------------------------------------------------------------------------
    // Drawing methods for lines, squares, and rectangles
    // -------------------------------------------------------------------------

    /**
     * Draws a line between two points on the given {@link Page} using the specified {@link Button}.
     * <p>
     * Coordinates are 0-indexed.
     * </p>
     *
     * @param page   the page on which to draw the line
     * @param button the button to use for drawing the line
     * @param x1     the starting X coordinate (0-8)
     * @param y1     the starting Y coordinate (0-5)
     * @param x2     the ending X coordinate (0-8)
     * @param y2     the ending Y coordinate (0-5)
     */
    public static void drawLineAt(@NotNull Page page, @NotNull Button button, int x1, int y1, int x2, int y2) {
        // Ensure that (x1, y1) is always the starting point and (x2, y2) is the ending point
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            int tempY = y1;
            y1 = y2;
            y2 = tempY;
        }
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;
            int tempX = x1;
            x1 = x2;
            x2 = tempX;
        }

        // Handle horizontal, vertical, and diagonal lines
        // Use slope to determine how we iterate through the grid
        double slope = (x2 - x1) != 0 ? (double) (y2 - y1) / (x2 - x1) : 0;

        for (int x = x1; x <= x2; x++) {
            // Calculate the corresponding y value for this x
            int y = (int) (slope * (x - x1) + y1);

            // Ensure we are within the bounds of the inventory (9x6 grid)
            if (x >= 0 && x < 9 && y >= 0 && y < 6) {
                int slot = y * 9 + x; // Convert 2D (x, y) to 1D slot index
                setButton(page, slot, button);
            }
        }
    }

    /**
     * Draws a line between two points (1-based coordinates) on the given {@link Page} using the specified {@link Button}.
     *
     * @param page      the page on which to draw the line
     * @param button    the button to use for drawing the line
     * @param column    the starting column (1-9)
     * @param row       the starting row (1-6)
     * @param columnEnd the ending column (1-9)
     * @param rowEnd    the ending row (1-6)
     * @see #drawLineAt(Page, Button, int, int, int, int)
     */
    public static void drawLine(@NotNull Page page, @NotNull Button button, int column, int row, int columnEnd, int rowEnd) {
        drawLineAt(page, button, column - 1, row - 1, columnEnd - 1, rowEnd - 1);
    }

    /**
     * Draws a line between two points across multiple {@link Page} objects using the specified {@link Button}.
     * <p>
     * Coordinates are 0-indexed relative to an overall grid spanning the pages.
     * </p>
     *
     * @param x1     the starting X coordinate (0-indexed)
     * @param y1     the starting Y coordinate (0-5)
     * @param x2     the ending X coordinate (0-indexed)
     * @param y2     the ending Y coordinate (0-5)
     * @param button the button to use for drawing the line
     * @param pages  the pages across which the line is drawn
     */
    public static void drawLineAt(int x1, int y1, int x2, int y2, Button button, Page... pages) {
        // Calculate overall grid dimensions
        int totalWidth = 9 * pages.length;
        int totalHeight = 6;

        // Bresenham's line algorithm setup
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        int x = x1;
        int y = y1;

        while (true) {
            // Only set the button if (x, y) falls within our overall grid bounds.
            if (x >= 0 && x < totalWidth && y >= 0 && y < totalHeight) {
                // Determine which page we're on.
                int pageIndex = x / 9;
                int localX = x % 9; // Column position within the page

                // Safety check for pageIndex (should always be true)
                if (pageIndex < pages.length) {
                    int slot = y * 9 + localX;
                    setButton(pages[pageIndex], slot, button);
                }
            }

            // Stop once we've reached the end point.
            if (x == x2 && y == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
    }

    /**
     * Draws a line between two points (1-based coordinates) across multiple {@link Page} objects using the specified {@link Button}.
     *
     * @param column    the starting column (1-based)
     * @param row       the starting row (1-based)
     * @param columnEnd the ending column (1-based)
     * @param rowEnd    the ending row (1-based)
     * @param button    the button to use for drawing the line
     * @param pages     the pages across which the line is drawn
     */
    public static void drawLine(int column, int row, int columnEnd, int rowEnd, Button button, Page... pages) {
        drawLineAt(column - 1, row - 1, columnEnd - 1, rowEnd - 1, button, pages);
    }

    /**
     * Draws a line between two points on the pages of the specified {@link GUI} using the specified {@link Button}.
     * <p>
     * Coordinates are 0-indexed relative to the individual pages.
     * </p>
     *
     * @param x1   the starting X coordinate (0-indexed)
     * @param y1   the starting Y coordinate (0-5)
     * @param x2   the ending X coordinate (0-indexed)
     * @param y2   the ending Y coordinate (0-5)
     * @param button the button to use for drawing the line
     * @param gui  the GUI containing the pages
     */
    public static void drawLineAt(int x1, int y1, int x2, int y2, Button button, GUI<Integer> gui) {
        drawLineAt(x1, y1, x2, y2, button, gui.getPages().values().toArray(new Page[0]));
    }

    /**
     * Draws a line between two points (1-based coordinates) on the pages of the specified {@link GUI} using the specified {@link Button}.
     *
     * @param column    the starting column (1-based)
     * @param row       the starting row (1-based)
     * @param columnEnd the ending column (1-based)
     * @param rowEnd    the ending row (1-based)
     * @param button    the button to use for drawing the line
     * @param gui       the GUI containing the pages
     */
    public static void drawLine(int column, int row, int columnEnd, int rowEnd, Button button, GUI<Integer> gui) {
        drawLineAt(column - 1, row - 1, columnEnd - 1, rowEnd - 1, button, gui);
    }

    /**
     * Draws a rectangle on the given {@link Page} using the specified {@link Button}.
     * <p>
     * Coordinates are 0-indexed. If {@code filled} is {@code true}, the entire rectangle area is filled;
     * otherwise, only the border is drawn.
     * </p>
     *
     * @param x1     the starting X coordinate (0-8)
     * @param y1     the starting Y coordinate (0-5)
     * @param x2     the ending X coordinate (0-8)
     * @param y2     the ending Y coordinate (0-5)
     * @param filled whether the rectangle should be filled
     * @param button the button to use for drawing the rectangle
     * @param page   the page on which to draw the rectangle
     */
    public static void drawRectangleAt(int x1, int y1, int x2, int y2, boolean filled, Button button, Page page) {
        // Ensure x1, y1 are always the top-left corner and x2, y2 are the bottom-right corner
        int xStart = Math.min(x1, x2);
        int xEnd = Math.max(x1, x2);
        int yStart = Math.min(y1, y2);
        int yEnd = Math.max(y1, y2);

        for (int x = xStart; x <= xEnd; x++) {
            for (int y = yStart; y <= yEnd; y++) {
                if (x >= 0 && x < 9 && y >= 0 && y < 6) { // Bounds check at the start
                    int slot = y * 9 + x;

                    if (filled) {
                        setButton(page, slot, button);
                    } else {
                        // Draw only the edges of the rectangle
                        if (x == xStart || x == xEnd || y == yStart || y == yEnd) {
                            setButton(page, slot, button);
                        }
                    }
                }
            }
        }
    }

    /**
     * Draws a rectangle on the given {@link Page} using the specified {@link Button}.
     * <p>
     * Coordinates are 1-based. If {@code filled} is {@code true}, the entire rectangle area is filled;
     * otherwise, only the border is drawn.
     * </p>
     *
     * @param column    the starting column (1-9)
     * @param row       the starting row (1-6)
     * @param columnEnd the ending column (1-9)
     * @param rowEnd    the ending row (1-6)
     * @param filled    whether the rectangle should be filled
     * @param button    the button to use for drawing the rectangle
     * @param page      the page on which to draw the rectangle
     */
    public static void drawRectangle(int column, int row, int columnEnd, int rowEnd, boolean filled, Button button, Page page) {
        drawRectangleAt(column - 1, row - 1, columnEnd - 1, rowEnd - 1, filled, button, page);
    }

    /**
     * Draws a rectangle across multiple {@link Page} objects using the specified {@link Button}.
     * <p>
     * Coordinates are 0-indexed relative to an overall grid spanning the pages.
     * </p>
     *
     * @param x1     the starting X coordinate (0-indexed)
     * @param y1     the starting Y coordinate (0-5)
     * @param x2     the ending X coordinate (0-indexed)
     * @param y2     the ending Y coordinate (0-5)
     * @param filled whether the rectangle should be filled
     * @param button the button to use for drawing the rectangle
     * @param pages  the pages across which the rectangle is drawn
     */
    public static void drawRectangleAt(int x1, int y1, int x2, int y2, boolean filled, Button button, Page... pages) {
        int totalWidth = 9 * pages.length;
        int totalHeight = 6;

        // Normalize coordinates to fit within a grid of multiple pages
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                if (x >= 0 && x < totalWidth && y >= 0 && y < totalHeight) {
                    // Determine which inventory page to use
                    int pageIndex = x / 9; // Find the page number (every 9 columns move to the next page)
                    int localX = x % 9; // Get the local x position within the page (0 to 8)

                    // Ensure we are within bounds for the current page
                    if (pageIndex < pages.length) {
                        int slot = y * 9 + localX; // Convert to slot index within the page
                        setButton(pages[pageIndex], slot, button);
                    }
                }
            }
        }
    }

    /**
     * Draws a rectangle across multiple {@link Page} objects using the specified {@link Button}.
     * <p>
     * Coordinates are 1-based relative to an overall grid spanning the pages.
     * </p>
     *
     * @param column    the starting column (1-based)
     * @param row       the starting row (1-based)
     * @param columnEnd the ending column (1-based)
     * @param rowEnd    the ending row (1-based)
     * @param filled    whether the rectangle should be filled
     * @param button    the button to use for drawing the rectangle
     * @param pages     the pages across which the rectangle is drawn
     */
    public static void drawRectangle(int column, int row, int columnEnd, int rowEnd, boolean filled, Button button, Page... pages) {
        drawRectangleAt(column, row, columnEnd, rowEnd, filled, button, pages);
    }

    /**
     * Draws a rectangle on the pages of the specified {@link GUI} using the specified {@link Button}.
     * <p>
     * Coordinates are 0-indexed relative to an overall grid spanning the pages.
     * </p>
     *
     * @param x1           the starting X coordinate (0-indexed)
     * @param y1           the starting Y coordinate (0-5)
     * @param x2           the ending X coordinate (0-indexed)
     * @param y2           the ending Y coordinate (0-5)
     * @param filled       whether the rectangle should be filled
     * @param button       the button to use for drawing the rectangle
     * @param paginatedGui the GUI containing the pages
     */
    public static void drawRectangleAt(int x1, int y1, int x2, int y2, boolean filled, Button button, GUI<Integer> paginatedGui) {
        drawRectangleAt(x1, y1, x2, y2, filled, button, paginatedGui.getPages().values().toArray(new Page[0]));
    }

    /**
     * Draws a rectangle on the pages of the specified {@link GUI} using the specified {@link Button}.
     * <p>
     * Coordinates are 1-based relative to an overall grid spanning the pages.
     * </p>
     *
     * @param column       the starting column (1-based)
     * @param row          the starting row (1-based)
     * @param columnEnd    the ending column (1-based)
     * @param rowEnd       the ending row (1-based)
     * @param filled       whether the rectangle should be filled
     * @param button       the button to use for drawing the rectangle
     * @param paginatedGui the GUI containing the pages
     */
    public static void drawRectangle(int column, int row, int columnEnd, int rowEnd, boolean filled, Button button, GUI<Integer> paginatedGui) {
        drawRectangleAt(column, row, columnEnd, rowEnd, filled, button, paginatedGui);
    }

    /**
     * Draws a square on the given {@link Page} using the specified {@link Button}.
     * <p>
     * The square starts at the given 0-indexed coordinate. If {@code filled} is {@code true},
     * the entire square area is filled; otherwise, only the border is drawn.
     * </p>
     *
     * @param page   the page on which to draw the square
     * @param button the button to use for drawing the square
     * @param x1     the starting X coordinate (0-8)
     * @param y1     the starting Y coordinate (0-5)
     * @param length the length of the square
     * @param filled whether the square should be filled
     */
    public static void drawSquareAt(@NotNull Page page, @NotNull Button button, int x1, int y1, int length, boolean filled) {
        int xEnd = x1 + length;
        int yEnd = y1 + length;

        for (int x = x1; x < xEnd; x++) {
            for (int y = y1; y < yEnd; y++) {
                if (x >= 0 && x < 9 && y >= 0 && y < 6) {
                    int slot = y * 9 + x;

                    if (filled) {
                        setButton(page, slot, button);
                    } else {
                        if (x == x1 || x == xEnd - 1 || y == y1 || y == yEnd - 1) {
                            setButton(page, slot, button);
                        }
                    }
                }
            }
        }
    }

    /**
     * Draws a square on the given {@link Page} using the specified {@link Button}.
     * <p>
     * The square starts at the given 1-based coordinate. If {@code filled} is {@code true},
     * the entire square area is filled; otherwise, only the border is drawn.
     * </p>
     *
     * @param page   the page on which to draw the square
     * @param button the button to use for drawing the square
     * @param column the starting column (1-9)
     * @param row    the starting row (1-6)
     * @param length the length of the square
     * @param filled whether the square should be filled
     */
    public static void drawSquare(@NotNull Page page, @NotNull Button button, int column, int row, int length, boolean filled) {
        drawSquareAt(page, button, column - 1, row - 1, length, filled);
    }

    // -------------------------------------------------------------------------
    // Helper methods
    // -------------------------------------------------------------------------

    private static void setItem(Inventory inventory, int slot, ItemStack item) {
        if (item != null && !item.getType().isItem()) {
            return;
        }

        inventory.setItem(slot, item);
    }

    private static void setButton(@NotNull Page page, int slot, @NotNull Button button) {
        page.setButton(slot, button);
    }

    private static void setItem(@NotNull Page page, int slot, ItemStack item) {
        if (item != null && !item.getType().isItem()) {
            return;
        }

        page.inventory().setItem(slot, item);
    }

}