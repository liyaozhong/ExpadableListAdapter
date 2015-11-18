package code.joshuali.expadablelistadapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joshuali on 15/11/18.
 */
public class MyExpandableListAdapter extends ExpandableListAdapter{

    public MyExpandableListAdapter(Activity context, ArrayList data) {
        super(context, data);
    }

    @Override
    public View getContentView(Context context) {
        TextView textView = new TextView(context);
        return textView;
    }

    @Override
    public void configContentView(int position, View contentView) {
        ((TextView)contentView).setText(""+position);
    }
}
