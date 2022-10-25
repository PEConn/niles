package dev.conn.niles.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.conn.niles.R;
import dev.conn.niles.model.ChecklistItem;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {
    private final List<ChecklistItem> mItems = new ArrayList<>();
    private final Delegate mDelegate;

    public interface Delegate {
        // TODO: What type should this be?
        void onItemClicked(ChecklistItem item);
    }

    public ChecklistAdapter(Delegate delegate) {
        mDelegate = delegate;
    }

    public void setItems(List<ChecklistItem> list) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            // TODO: Note on how this works.
            @Override
            public int getOldListSize() {
                return mItems.size();
            }

            @Override
            public int getNewListSize() {
                return list.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // TODO: Add ids?
                ChecklistItem oldItem = mItems.get(oldItemPosition);
                ChecklistItem newItem = list.get(newItemPosition);
                return oldItem.name.equals(newItem.name);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // TODO: Add equality/data type-ness.
                ChecklistItem oldItem = mItems.get(oldItemPosition);
                ChecklistItem newItem = list.get(newItemPosition);
                return oldItem.checked == newItem.checked && oldItem.name.equals(newItem.name);
            }
        });

        mItems.clear();
        mItems.addAll(list);

        result.dispatchUpdatesTo(this);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);

            checkBox = view.findViewById(R.id.check_box);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklist_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChecklistItem item = mItems.get(position);
        holder.checkBox.setChecked(item.checked);
        holder.checkBox.setText(item.name);

        holder.itemView.setOnClickListener(v -> {
            mDelegate.onItemClicked(item);
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
