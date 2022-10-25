package dev.conn.niles.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dev.conn.niles.Dependencies;
import dev.conn.niles.db.Repository;
import dev.conn.niles.db.SharedPreferencesDatabase;
import dev.conn.niles.model.ChecklistItem;

public class ChecklistViewModel extends AndroidViewModel {
    private List<String> mItems = new ArrayList<>();
    private Set<String> mCompletedItems = new HashSet<>();
    private final Repository mRepository;
    private boolean mInitialLoadTriggered;

    private final MutableLiveData<List<ChecklistItem>> mChecklistItems = new MutableLiveData<>();

    public ChecklistViewModel(Application application) {
        super(application);

        mRepository = Dependencies.provideRepository(application);

        updateChecklistItems();
    }

    public void toggleItem(ChecklistItem item) {
        String name = item.name;

        if (mCompletedItems.contains(name)) {
            mCompletedItems.remove(name);
        } else {
            mCompletedItems.add(name);
        }

        mRepository.setCompletedItems(mCompletedItems);
        updateChecklistItems();
    }

    public void setCompletedItems(Set<String> items) {
        mCompletedItems = new HashSet<>(items);
        updateChecklistItems();
    }

    public void setItems(List<String> items) {
        mItems = new ArrayList<>(items);
        updateChecklistItems();
    }

    private void updateChecklistItems() {
        mChecklistItems.setValue(mItems.stream()
                .map((item) -> new ChecklistItem(mCompletedItems.contains(item), item))
                .collect(Collectors.toList()));
    }

    public LiveData<List<ChecklistItem>> getChecklistItems() {
        if (!mInitialLoadTriggered) {
            mRepository.getCompletedItems(this::setCompletedItems);
            mRepository.getItems(this::setItems);
            mInitialLoadTriggered = true;
        }

        return mChecklistItems;
    }
}