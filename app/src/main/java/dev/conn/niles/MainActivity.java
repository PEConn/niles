package dev.conn.niles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.conn.niles.ui.ChecklistFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ChecklistFragment.newInstance())
                    .commitNow();
        }
    }
}