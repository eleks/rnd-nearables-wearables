package com.eleks.rnd.nearables.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;

/**
 * Simple view holder for a single text view.
 */
class LabelViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    LabelViewHolder(View view) {
        super(view);

        mTextView = (TextView) view.findViewById(R.id.text);
    }

    public void bindItem(String text) {
        mTextView.setText(text);
    }

}