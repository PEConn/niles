package dev.conn.niles.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

import dev.conn.niles.R;
import dev.conn.niles.viewmodel.ChecklistViewModel;

public class ChecklistFragment extends Fragment {

    private ChecklistViewModel mViewModel;
    private RecyclerView mRecyclerView;

    public static ChecklistFragment newInstance() {
        return new ChecklistFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.checklist_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel = new ViewModelProvider(this).get(ChecklistViewModel.class);
        ChecklistAdapter adapter = new ChecklistAdapter(mViewModel::toggleItem);

        mViewModel.getChecklistItems().observe(getViewLifecycleOwner(), adapter::setItems);

        mRecyclerView.setAdapter(adapter);

        FloatingActionButton clear = view.findViewById(R.id.clear_checks_fab);
        clear.setOnClickListener(v -> mViewModel.setCompletedItems(Collections.emptySet()));

        return view;
    }

}