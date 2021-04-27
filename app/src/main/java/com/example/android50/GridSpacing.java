package com.example.android50;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridSpacing extends RecyclerView.ItemDecoration {
    private int mItemOffset;

    public GridSpacing(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public GridSpacing(Context context, int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }


    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}
