package com.eleks.rnd.nearables.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;
import com.github.johnkil.print.PrintView;

/**
 * Simple view holder for a single text view.
 */
class LabelViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;
    private PrintView icon;

    LabelViewHolder(View view) {
        super(view);

        mTextView = (TextView) view.findViewById(R.id.text);
        icon = (PrintView) view.findViewById(R.id.icon);

    }

    public void bindItem(String text) {
        if("*".equals(text)) {
            icon.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);
        } else {
            icon.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(text);
        }
    }
}