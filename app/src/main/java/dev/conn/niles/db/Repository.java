package dev.conn.niles.db;

import androidx.core.util.Consumer;

import java.util.List;
import java.util.Set;

public interface Repository {
    void getCompletedItems(Consumer<Set<String>> callback);

    void setCompletedItems(Set<String> items);

    void getItems(Consumer<List<String>> callback);
}
