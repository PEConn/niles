package dev.conn.niles.model;

/**
 * An item, must be uniquely identified by its name.
 */
public class ChecklistItem {
    public final boolean checked;
    public final String name;

    public ChecklistItem(boolean checked, String name) {
        this.checked = checked;
        this.name = name;
    }
}
