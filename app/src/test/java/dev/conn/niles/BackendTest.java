package dev.conn.niles;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.test.core.app.ApplicationProvider;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import dev.conn.niles.model.ChecklistItem;
import dev.conn.niles.model.ChecklistItems;
import dev.conn.niles.viewmodel.ChecklistViewModel;

/**
 * Tests the {@link ChecklistViewModel} and {@link dev.conn.niles.db.SharedPreferencesDatabase}
 * interaction.
 */
@RunWith(RobolectricTestRunner.class)
public class BackendTest {
    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private ChecklistViewModel mViewModel;

    @Before
    public void setUp() {
        mViewModel = new ChecklistViewModel(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void correctItems() {
        List<ChecklistItem> checklistItems = mViewModel.getChecklistItems().getValue();

        List<String> fetchedItemNames = checklistItems
                .stream()
                .map(item -> item.name)
                .collect(Collectors.toList());

        assertEquals(ChecklistItems.ITEMS, fetchedItemNames);
    }

    @Test
    public void defaultToNoItemsChecked() {
        List<ChecklistItem> checklistItems = mViewModel.getChecklistItems().getValue();

        // Yeah, I know it's not amazing to use flow control within a test, but I tried:
        // assertThat(checklistItems, contains(
        //         Matchers.<Boolean>hasProperty("checked", is(false))
        // ));
        // But it didn't work, presumably it tripped over the boolean value.

        for (ChecklistItem item: checklistItems) {
            assertFalse(item.name, item.checked);
        }
    }

    @Test
    public void toggleItems() {
        List<ChecklistItem> items1 = mViewModel.getChecklistItems().getValue();
        assertFalse(items1.get(0).checked);

        mViewModel.toggleItem(items1.get(0));

        List<ChecklistItem> items2 = mViewModel.getChecklistItems().getValue();
        assertTrue(items2.get(0).checked);

        mViewModel.toggleItem(items2.get(0));

        List<ChecklistItem> items3 = mViewModel.getChecklistItems().getValue();
        assertFalse(items3.get(0).checked);
    }

    @Test
    public void makesDefensiveCopy() {
        mViewModel.getChecklistItems(); // Trigger initial load.

        mViewModel.setCompletedItems(Collections.emptySet());

        List<ChecklistItem> items1 = mViewModel.getChecklistItems().getValue();
        mViewModel.toggleItem(items1.get(0));

        List<ChecklistItem> items2 = mViewModel.getChecklistItems().getValue();
        assertTrue(items2.get(0).checked);
    }
}