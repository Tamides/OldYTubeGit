package pl.tamides.ytube.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.tamides.ytube.App;
import pl.tamides.ytube.R;
import pl.tamides.ytube.helpers.ResourcesHelper;

public class ListView<T, U extends View> extends RecyclerView {

    private List<T> items = new ArrayList<>();
    private OnBindViewHolder<T, U> onBindViewHolder;
    private Class<U> itemViewClass;
    private boolean showHorizontalSpaceDivider = false;
    private ListViewAdapter adapter;

    public ListView(@NonNull Context context) {
        super(context);
    }

    public ListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListView<T, U> setItems(List<T> items) {
        this.items = items;
        return this;
    }

    public ListView<T, U> setOnBindViewHolder(OnBindViewHolder<T, U> onBindViewHolder) {
        this.onBindViewHolder = onBindViewHolder;
        return this;
    }

    public ListView<T, U> setItemViewClass(Class<U> itemViewClass) {
        this.itemViewClass = itemViewClass;
        return this;
    }

    public ListView<T, U> enableHorizontalSpaceDivider() {
        showHorizontalSpaceDivider = true;
        return this;
    }

    public ListView<T, U> setLayoutMng(LayoutManager layoutManager) {
        setLayoutManager(layoutManager);
        return this;
    }

    public ListView<T, U> confirmChanges() {
        if (showHorizontalSpaceDivider) {
            addItemDecoration(new HorizontalSpaceDivider());
        }

        adapter = new ListViewAdapter();
        setAdapter(adapter);
        return this;
    }

    public ListView<T, U> notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
        return this;
    }

    public interface OnBindViewHolder<T, U extends View> {
        void onBindViewHolder(T item, U view);
    }

    private class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            U itemView = null;

            try {
                itemView = itemViewClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                App.showMessage(e.getMessage());
            }

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            onBindViewHolder.onBindViewHolder(items.get(position), holder.getItemView());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private U itemView;

            public ViewHolder(@NonNull U itemView) {
                super(itemView);
                this.itemView = itemView;
            }

            public U getItemView() {
                return itemView;
            }
        }
    }

    private class HorizontalSpaceDivider extends ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.right = ResourcesHelper.getInstance().getDimension(R.dimen.normal_margin);
            }
        }
    }
}
