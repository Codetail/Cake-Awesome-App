package com.uzcustomcake.core.adapter;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

/**
 * created at 10/1/17
 *
 * @author 00003130
 * @version 1.0
 */

public abstract class MultiViewAdapter<V> extends
    RecyclerView.Adapter<MultiViewAdapter.ViewHolderWithBindAction> {

  private RecyclerView host;

  private int currentSize;

  private final ArrayMap<String, List<V>> currentData = new ArrayMap<>();
  private final SparseArray<Section> sectionPositionsMap = new SparseArray<>();

  private final LiveData<Map<String, List<V>>> liveData;
  private final Observer<Map<String, List<V>>> onLiveDataChanged =
      new Observer<Map<String, List<V>>>() {
        @Override public void onChanged(@Nullable Map<String, List<V>> newData) {
          if (newData == null) { // when data is null clear internal data
            currentData.clear();
            notifyItemRemoved(currentSize);
            return;
          }

          currentData.putAll(newData);

          int newSize = 0;
          for (int i = 0; i < currentData.size(); i++) {
            sectionPositionsMap.put(newSize, new Section(i, newSize,
                currentData.valueAt(i).size()));

            newSize += 1; // adding section title
            newSize += currentData.valueAt(i).size(); // adding section items size
          }

          notifyDataSetChanged();
        }
      };

  public MultiViewAdapter(LiveData<Map<String, List<V>>> liveData) {
    this.liveData = liveData;
  }

  @Override public ViewHolderWithBindAction onCreateViewHolder(ViewGroup parent, int viewType) {
    // We initiating Section title by default, while we can't create Holder

    if (viewType == 0) {
      return SectionTitleViewHolder.create(parent);
    }
    return null;
  }

  @Override
  public void onBindViewHolder(ViewHolderWithBindAction holder, int position) {
    //noinspection unchecked
    holder.bind(position, getItem(position));
  }

  public boolean isAttached() {
    return host != null;
  }

  @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    host = recyclerView;
    liveData.observeForever(onLiveDataChanged);
  }

  @Override public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
    host = null;
    liveData.removeObserver(onLiveDataChanged);
  }

  @Override
  public void onViewAttachedToWindow(ViewHolderWithBindAction holder) {
    super.onViewAttachedToWindow(holder);
    holder.host = this;
  }

  @Override
  public void onViewDetachedFromWindow(ViewHolderWithBindAction holder) {
    super.onViewDetachedFromWindow(holder);
    holder.host = null;
  }

  @Override public int getItemCount() {
    return currentSize;
  }

  @Override public int getItemViewType(int position) {
    // We going through each section to see which one suits
    // given position.
    // When Section is found, we analyzing view type and returning
    // right type based on position (where 0 -> title, others -> items)

    for (int i = 0; i < sectionPositionsMap.size(); i++) {
      final Section expected = sectionPositionsMap.valueAt(i);

      if (expected.isSectionTitlePosition(position)) {
        return 0; // ViewType Title
      } else if (expected.isSectionItemPosition(position)) {
        return 1; // ViewType Item
      }
    }

    throw new IllegalStateException(
        "Unexpected position(" + position + "), no section found to handle it");
  }

  /**
   * @param position Adapter position
   * @return Object that will
   */
  @SuppressWarnings("unchecked")
  public Object getItem(int position) {
    // We going through each section to see which one suits
    // given position.
    // When Section is found, we analyzing view type and returning
    // right type based on position (where 0 -> title, others -> items)

    for (int i = 0; i < sectionPositionsMap.size(); i++) {
      final Section expected = sectionPositionsMap.valueAt(i);

      if (expected.isSectionTitlePosition(position)) {
        return expected.getSectionTitle(currentData);
      } else if (expected.isSectionItemPosition(position)) {
        return expected.getSectionItem(position, currentData);
      }
    }

    throw new IllegalStateException(
        "Unexpected position(" + position + "), no section found to handle it");
  }

  /**
   * Section is helper class, knows how to retrieve right
   * data from section depending on position
   */
  static class Section<V> {
    private final int keyIndex;
    private final int offset;
    private final int size;

    Section(int keyIndex, int offset, int size) {
      this.keyIndex = keyIndex;
      this.offset = offset;
      this.size = size;
    }

    boolean isSectionTitlePosition(int position) {
      return position == offset + 1;
    }

    boolean isSectionItemPosition(int position) {
      return position > offset + 1 && position < offset + size + 1;
    }

    String getSectionTitle(ArrayMap<String, List<V>> data) {
      return data.keyAt(keyIndex);
    }

    V getSectionItem(int position, ArrayMap<String, List<V>> data) {
      return data.valueAt(keyIndex).get(position - offset);
    }
  }

  public static class SectionTitleViewHolder extends ViewHolderWithBindAction<CharSequence> {

    public static SectionTitleViewHolder create(ViewGroup parent) {
      final TextView item = new TextView(parent.getContext());
      TextViewCompat.setTextAppearance(item,
          android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Title);
      return new SectionTitleViewHolder(item);
    }

    private final TextView title;

    public SectionTitleViewHolder(TextView title) {
      super(title);
      this.title = title;
    }

    @Override
    public void bind(int position, CharSequence text) {
      this.title.setText(text);
    }
  }

  /**
   * View holder that represents certain value class
   */
  public abstract static class ViewHolderWithBindAction<Value> extends RecyclerView.ViewHolder {

    MultiViewAdapter host;

    public final boolean isAttached() {
      return host != null;
    }

    public ViewHolderWithBindAction(View itemView) {
      super(itemView);
    }

    public abstract void bind(int position, Value value);
  }
}
