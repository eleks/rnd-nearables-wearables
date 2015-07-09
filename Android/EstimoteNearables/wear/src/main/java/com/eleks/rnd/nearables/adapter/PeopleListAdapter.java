package com.eleks.rnd.nearables.adapter;

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.model.Person;
import com.eleks.rnd.nearables.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by bogdan.melnychuk on 09.07.2015.
 */
public class PeopleListAdapter extends WearableListView.Adapter {
    private final Context context;
    private final List<Person> items;

    public PeopleListAdapter(Context context, List<Person> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new WearableListView.ViewHolder(new PersonItemView(context));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, int i) {
        Person p = items.get(i);
        PersonItemView itemView = (PersonItemView) viewHolder.itemView;

        TextView txtViewName = (TextView) itemView.findViewById(R.id.name);
        txtViewName.setText(p.getName());

        TextView txtViewLocation = (TextView) itemView.findViewById(R.id.location);
        txtViewLocation.setText(p.getLocation());

        TextView txtViewTime = (TextView) itemView.findViewById(R.id.time);

        Map<TimeUnit, Long> timeDiff = DateUtils.computeDiff(new Date(p.getLastActivity()), new Date());
        StringBuilder s = new StringBuilder();
        long hrs = timeDiff.get(TimeUnit.HOURS);
        if(hrs > 0) {
            s.append(timeDiff.get(TimeUnit.HOURS));
            s.append(" hrs, ");
        }
        long min = timeDiff.get(TimeUnit.MINUTES);
        if(min > 0) {
            s.append(timeDiff.get(TimeUnit.MINUTES));
            s.append(" min ago");
        }
        if(s.length() == 0) {
            s.append("Just Now");
        }
        txtViewTime.setText(s.toString());

        CircledImageView imgView = (CircledImageView) itemView.findViewById(R.id.image);
        imgView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
