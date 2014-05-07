package com.etsy.android.grid;

import android.widget.ListAdapter;

public interface StaggeredListAdapter extends ListAdapter {
    public static final int ITEM_VIEW_TYPE_SPAN_ALL = -3;

    /**
     * @return <0 for span all
     */
    public abstract int getItemViewSpanCount(int position);
}
