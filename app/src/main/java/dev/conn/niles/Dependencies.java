package dev.conn.niles;

import android.content.Context;

import androidx.annotation.Nullable;

import dev.conn.niles.db.Repository;
import dev.conn.niles.db.SharedPreferencesDatabase;

/**
 * Class used until I start using a dependency injection framework.
 */
public class Dependencies {
    @Nullable
    private static Repository sRepository;

    public static void setRepository(Repository repository) {
        sRepository = repository;
    }

    public static Repository provideRepository(Context context) {
        if (sRepository != null) return sRepository;

        return new SharedPreferencesDatabase(context);
    }
}
