package com.eleks.rnd.nearables.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.model.Person;
import com.eleks.rnd.nearables.util.DateUtils;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Simple view holder for a single text view.
 */
class PersonViewHolder extends RecyclerView.ViewHolder {


    final CircleImageView imgView;
    final TextView txtViewName;
    final TextView txtViewLocation;
    final TextView txtViewTime;
    final View parent;


    PersonViewHolder(View view) {
        super(view);

        imgView = (CircleImageView) view.findViewById(R.id.image);
        parent = (View) imgView.getParent();
        txtViewName = (TextView) view.findViewById(R.id.name);
        txtViewLocation = (TextView) view.findViewById(R.id.location);
        txtViewTime = (TextView) view.findViewById(R.id.time);
    }

    public void bindItem(Person p) {
        imgView.setImageResource(R.mipmap.ic_launcher);
        txtViewName.setText(p.getName());
        txtViewLocation.setText(p.getLocation());
        txtViewTime.setText(DateUtils.getDateDiffMessage(new Date(p.getTimestamp())));
    }
}