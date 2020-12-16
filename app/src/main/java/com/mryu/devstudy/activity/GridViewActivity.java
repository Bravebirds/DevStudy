package com.mryu.devstudy.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.mryu.devstudy.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GridViewActivity extends AppCompatActivity {
    private GridView mGrid;
    private List<String> lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        initView();
        setView();
    }

    private void setView() {
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),""+ lists.get(i).toString(),Toast.LENGTH_SHORT
                ).show();
            }
        });

    }

    private void initView() {
        mGrid = (GridView) findViewById(R.id.grid);
        lists = new ArrayList<>();
        lists.add("gridview(1)");
        lists.add("gridview(2)");
        lists.add("gridview(3)");
        lists.add("gridview(4)");
        lists.add("gridview(5)");
        lists.add("gridview(6)");
        lists.add("gridview(7)");
        lists.add("gridview(8)");
        AdapterGridTestAdapter adapterGridTestAdapter = new AdapterGridTestAdapter();
        mGrid.setAdapter(adapterGridTestAdapter);
    }

    public class AdapterGridTestAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public String getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.adapter_grid_test, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((String) getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(String object, ViewHolder holder) {
            holder.adapterTest.setText(object);
        }

        protected class ViewHolder {
            private TextView adapterTest;

            public ViewHolder(View view) {
                adapterTest = (TextView) view.findViewById(R.id.adapter_test);
            }
        }
    }
}
