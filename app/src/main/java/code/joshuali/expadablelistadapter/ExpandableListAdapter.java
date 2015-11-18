package code.joshuali.expadablelistadapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by joshuali on 15/11/18.
 */
public abstract class ExpandableListAdapter<T extends Object> extends BaseAdapter {
    private Activity context;
    private ArrayList<T> data;
    private ViewHolder lastExpandItem;
    private int lastExpandPosition = -1;

    public ExpandableListAdapter(Activity context, ArrayList<T> data) {
        super();
        this.context = context;
        this.data = new ArrayList<T>(data);
    }

    public abstract View getContentView(Context context);
    public abstract void configContentView(final int position, View contentView);

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.expadable_adapter, null);
            holder = new ViewHolder();
            holder.rootView = (LinearLayout)convertView.findViewById(R.id.expandView);
            holder.contentView = (FrameLayout)convertView.findViewById(R.id.contentView);
            holder.contentResuseView = getContentView(context);
            holder.contentView.addView(holder.contentResuseView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        configContentView(position, holder.contentResuseView);
        holder.expand = lastExpandPosition == position;
        if(holder.expand){
            holder.rootView.getLayoutParams().height = context.getResources().getDimensionPixelSize(R.dimen.adapter_expand_height);
            holder.rootView.setVisibility(View.VISIBLE);
        }else{
            holder.rootView.getLayoutParams().height = 0;
        }
        holder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.expand) {
                    collapse(holder);
                } else {
                    expand(holder, position);
                }
            }
        });
        return convertView;
    }

    public void expand(final ViewHolder v, final int position) {
        if(lastExpandItem != null && lastExpandItem.expand){
            collapse(lastExpandItem);
            if(lastExpandItem == v){
                return;
            }
        }
        lastExpandPosition = position;
        lastExpandItem = v;
        final int start = 0;
        final int end = context.getResources().getDimensionPixelSize(R.dimen.adapter_expand_height);
        v.rootView.measure(AbsListView.LayoutParams.MATCH_PARENT, end);

        v.rootView.getLayoutParams().height = start;
        v.rootView.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.rootView.getLayoutParams().height = (int)(start + (end - start) * interpolatedTime);
                v.rootView.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(400);
        v.rootView.clearAnimation();
        v.rootView.startAnimation(a);
        v.expand = true;
    }

    public void collapse(final ViewHolder v) {
        lastExpandPosition = -1;
        final int initialHeight = v.rootView.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.rootView.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                v.rootView.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(400);
        v.rootView.startAnimation(a);
        v.expand = false;
    }

    private class ViewHolder {
        boolean expand;
        View contentResuseView;
        LinearLayout rootView;
        FrameLayout contentView;
    }
}
