package com.eleks.rnd.nearables.fragment;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eleks.rnd.nearables.R;
import com.eleks.rnd.nearables.model.Person;
import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LinearSLM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0x01;
    private static final int VIEW_TYPE_CONTENT = 0x00;

    private Set<Long> selected = new HashSet<>();
    private ArrayList<LineItem> mItems;
    private PersonCheckeListener checkeListener;

    private int mHeaderDisplay;

    private boolean mMarginsFixed;

    private final Context mContext;

    public void setData(List<Person> people) {
        mItems = new ArrayList<>();
        //Insert headers into list of items.
        String lastHeader = "";
        int sectionManager = -1;
        int headerCount = 0;
        int sectionFirstPosition = 0;
        for (int i = 0; i < people.size(); i++) {
            Person p = people.get(i);
            String header;
            if (p.isFavorite()) {
                header = "*";
            } else {
                header = people.get(i).getName().substring(0, 1);
            }

            if (!TextUtils.equals(lastHeader, header)) {
                // Insert new header view and update section data.
                sectionManager = (sectionManager + 1) % 2;
                sectionFirstPosition = i + headerCount;
                lastHeader = header;
                headerCount += 1;
                mItems.add(new LineItem(header, true, sectionFirstPosition));
            }
            mItems.add(new LineItem(people.get(i), false, sectionFirstPosition));
        }
        setMarginsFixed(true);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selected.clear();
        notifyDataSetChanged();
    }

    public PersonAdapter(Context context, int headerMode) {
        mContext = context;
        mHeaderDisplay = headerMode;
        mItems = new ArrayList<>();
    }

    public void setPersonCheckeListener(PersonCheckeListener listener) {
        this.checkeListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_item, parent, false);
            return new LabelViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_person, parent, false);
            return new PersonViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder  holder, int position) {
        final LineItem item = mItems.get(position);
        final View itemView = holder.itemView;

        if(holder instanceof PersonViewHolder) {
            final PersonViewHolder pHolder = (PersonViewHolder) holder;
            final Person person = (Person) item.data;
            pHolder.bindItem((Person) item.data);
            pHolder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean checked = !selected.contains(person.getId());
                    if (checked) {
                        pHolder.imgView.setAlpha(0.5f);
                        selected.add(person.getId());
                    } else {
                        pHolder.imgView.setAlpha(1f);
                        selected.remove(person.getId());
                    }
                    if(checkeListener != null) {
                        checkeListener.onPersonCheck(person, checked, selected);
                    }
                }
            });
            pHolder.imgView.setAlpha(selected.contains(person.getId()) ? 0.5f : 1f);
        } else {
            ((LabelViewHolder)holder).bindItem(item.data.toString());
        }

        final GridSLM.LayoutParams lp = GridSLM.LayoutParams.from(itemView.getLayoutParams());
        // Overrides xml attrs, could use different layouts too.
        if (item.isHeader) {
            lp.headerDisplay = mHeaderDisplay;
            if (lp.isHeaderInline() || (mMarginsFixed && !lp.isHeaderOverlay())) {
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }

            lp.headerEndMarginIsAuto = !mMarginsFixed;
            lp.headerStartMarginIsAuto = !mMarginsFixed;
        }
        lp.setSlm(LinearSLM.ID);
        lp.setColumnWidth(mContext.getResources().getDimensionPixelSize(R.dimen.grid_column_width));
        lp.setFirstPosition(item.sectionFirstPosition);
        itemView.setLayoutParams(lp);
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isHeader ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setHeaderDisplay(int headerDisplay) {
        mHeaderDisplay = headerDisplay;
        notifyHeaderChanges();
    }

    public void setMarginsFixed(boolean marginsFixed) {
        mMarginsFixed = marginsFixed;
        notifyHeaderChanges();
    }

    private void notifyHeaderChanges() {
        for (int i = 0; i < mItems.size(); i++) {
            LineItem item = mItems.get(i);
            if (item.isHeader) {
                notifyItemChanged(i);
            }
        }
    }

    private static class LineItem {
        public int sectionFirstPosition;
        public boolean isHeader;
        public Object data;

        public LineItem(Object data, boolean isHeader, int sectionFirstPosition) {
            this.isHeader = isHeader;
            this.data = data;
            this.sectionFirstPosition = sectionFirstPosition;
        }
    }

    public interface PersonCheckeListener {
        void onPersonCheck(Person p, boolean checked, Set<Long> allCheked);
    }
}