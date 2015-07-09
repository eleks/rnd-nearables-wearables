package com.eleks.rnd.nearables.adapter;

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;

/**
 * Created by bogdan.melnychuk on 09.07.2015.
 */
public final class PersonItemView extends RelativeLayout implements WearableListView.OnCenterProximityListener {

    final CircledImageView imgView;
    final TextView txtViewName;
    final TextView txtViewLocation;
    final TextView txtViewTime;
    final View parent;

    public PersonItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.layout_item_person, this);
        imgView = (CircledImageView) findViewById(R.id.image);
        parent = (View) imgView.getParent();
        txtViewName = (TextView) findViewById(R.id.name);
        txtViewLocation = (TextView) findViewById(R.id.location);
        txtViewTime = (TextView) findViewById(R.id.time);
    }



    @Override
    public void onCenterPosition(boolean b) {
        //Animation example to be ran when the view becomes the centered one
//        imgView.animate().scaleX(1f).scaleY(1f).alpha(1);
//        txtViewName.animate().scaleX(1f).scaleY(1f).alpha(1);
//        txtViewLocation.animate().scaleX(1f).scaleY(1f).alpha(1);
//        txtViewTime.animate().scaleX(1f).scaleY(1f).alpha(1);
        parent.animate().scaleX(1f).scaleY(1f).alpha(1);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        //Animation example to be ran when the view is not the centered one anymore
//        imgView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
//        txtViewName.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
//        txtViewLocation.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
//        txtViewTime.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        parent.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
    }
}
