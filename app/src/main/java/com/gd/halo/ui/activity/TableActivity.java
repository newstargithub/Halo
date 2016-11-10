package com.gd.halo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gd.halo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableActivity extends AppCompatActivity {
    @BindView(R.id.list_view)
    ListView list_view;
    private Context mContext;

    private String[] classList = {
            "ScrollingActivity",
            "TabActivity",
            "ui.activity.NewsActivity"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        mContext = this;
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, classList);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = classList[position];
                String packageName = mContext.getPackageName();
                if(!item.startsWith(packageName)) {
                    item = packageName + "." + item;
                }
                try {
                    Class<?> aClass = Class.forName(item);
                    Intent intent = new Intent(mContext, aClass);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
