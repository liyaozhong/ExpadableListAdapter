package code.joshuali.expadablelistadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyExpandableListAdapter baseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayList data = new ArrayList();
        for(int i = 0 ;i < 100 ; i ++){
            data.add(new Object());
        }
        baseAdapter = new MyExpandableListAdapter(this, data);
        listView.setAdapter(baseAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
