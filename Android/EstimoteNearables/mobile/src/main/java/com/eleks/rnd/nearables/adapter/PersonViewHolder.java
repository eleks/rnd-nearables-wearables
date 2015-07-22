package com.eleks.rnd.nearables.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.model.Person;
import com.eleks.rnd.nearables.service.HttpService;
import com.eleks.rnd.nearables.util.DateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

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
    private Context context;


    PersonViewHolder(View view) {
        super(view);
        this.context = view.getContext();
        imgView = (CircleImageView) view.findViewById(R.id.image);
        parent = (View) imgView.getParent();
        txtViewName = (TextView) view.findViewById(R.id.name);
        txtViewLocation = (TextView) view.findViewById(R.id.location);
        txtViewTime = (TextView) view.findViewById(R.id.time);
    }

    public void bindItem(Person p) {
        ImageLoader.getInstance().loadImage(HttpService.getImageUrl((int) p.getEmpId()), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Drawable d = new BitmapDrawable(context.getResources(), loadedImage);
                imgView.setImageDrawable(d);
            }
        });
        //Picasso.with(context).load(HttpService.getImageUrl((int) p.getEmpId())).into(imgView);
        txtViewName.setText(p.getName());
        txtViewLocation.setText(p.getLocation());
        txtViewTime.setText(DateUtils.getDateDiffMessage(new Date(p.getTimestamp())));
    }
}