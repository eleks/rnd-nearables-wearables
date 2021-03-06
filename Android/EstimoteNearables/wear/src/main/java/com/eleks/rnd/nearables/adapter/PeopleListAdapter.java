package com.eleks.rnd.nearables.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.model.Movement;
import com.eleks.rnd.nearables.service.HttpService;
import com.eleks.rnd.nearables.util.DateUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bogdan.melnychuk on 09.07.2015.
 */
public class PeopleListAdapter extends WearableListView.Adapter {
    private Context context;
    private List<Movement> items;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public PeopleListAdapter(Context context, List<Movement> items) {
        this.context = context;
        this.items = items;
    }

    public PeopleListAdapter(Context context) {
        this(context, new ArrayList<Movement>());
    }

    public void setData(List<Movement> people) {
        this.items = people;
        notifyDataSetChanged();
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new WearableListView.ViewHolder(new PersonItemView(context));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
        Movement p = items.get(i);
        PersonItemView itemView = (PersonItemView) viewHolder.itemView;

        TextView txtViewName = (TextView) itemView.findViewById(R.id.name);
        txtViewName.setText(p.getEmployeeName());

        TextView txtViewLocation = (TextView) itemView.findViewById(R.id.location);
        txtViewLocation.setText("@" + p.getLocation());

        TextView txtViewTime = (TextView) itemView.findViewById(R.id.time);

        Map<TimeUnit, Long> timeDiff = DateUtils.computeDiff(new Date(p.getTimestamp()), new Date());
        StringBuilder s = new StringBuilder();
        long hrs = timeDiff.get(TimeUnit.HOURS);
        if (hrs > 0) {
            s.append(timeDiff.get(TimeUnit.HOURS));
            s.append(" hrs, ");
        }
        long min = timeDiff.get(TimeUnit.MINUTES);
        if (min > 0) {
            s.append(timeDiff.get(TimeUnit.MINUTES));
            s.append(" min ago");
        }
        if (s.length() == 0) {
            s.append("Just Now");
        }
        txtViewTime.setText(s.toString());

        final CircleImageView imgView = (CircleImageView) itemView.findViewById(R.id.image);
        //imgView.setImageResource(R.mipmap.ic_launcher);
        imageLoader.loadImage(HttpService.getImageUrl(p.getEmployeeId()), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Drawable d = new BitmapDrawable(context.getResources(), loadedImage);
                imgView.setImageDrawable(d);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
