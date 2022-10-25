package dev.conn.niles.db;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.util.Consumer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.conn.niles.model.ChecklistItems;

public class SharedPreferencesDatabase implements Repository {
    private static final String PREFS_FILE_NAME = "prefs";
    private static final String COMPLETED_ITEMS_KEY = "completed_items";

    private final SharedPreferences mPrefs;

    public SharedPreferencesDatabase(Context context) {
        mPrefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void getCompletedItems(Consumer<Set<String>> callback) {
        Set<String> items = mPrefs.getStringSet(COMPLETED_ITEMS_KEY, new HashSet<>());
        callback.accept(items);
    }

    @Override
    public void setCompletedItems(Set<String> items) {
        mPrefs.edit()
                .putStringSet(COMPLETED_ITEMS_KEY, items)
                .apply();
    }

    @Override
    public void getItems(Consumer<List<String>> callback) {
        callback.accept(ChecklistItems.ITEMS);
    }
}
