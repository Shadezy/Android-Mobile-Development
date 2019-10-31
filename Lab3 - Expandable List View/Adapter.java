package com.cartmell.travis.tcartmelllab3;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseExpandableListAdapter implements View.OnClickListener {
    Activity activity;
    ArrayList<Manufacturer> manList = new ArrayList<>();

    public Adapter(Activity act, ArrayList<Manufacturer> manufacturers) {
        this.activity = act;
        this.manList = manufacturers;
    }

    @Override
    public int getGroupCount() {
        return manList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return manList.get(groupPosition).getModelCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return manList.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return manList.get(groupPosition).muscle_car_models.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {//this?
            convertView = activity.getLayoutInflater().inflate(R.layout.linear_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.text_data);
        tv.setText(manList.get(groupPosition).getManufacturerName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {//this?
            convertView = activity.getLayoutInflater().inflate(R.layout.child_item, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.text_child);
        tv.setText(manList.get(groupPosition).getModelName(childPosition));

        ImageView iv = convertView.findViewById(R.id.image_delete);
        iv.setTag(R.id.group_num, groupPosition);
        iv.setTag(R.id.child_num, childPosition);

        iv.setOnClickListener(this);
        return convertView;
    }

    public void onClick(View v) {
        View view = activity.findViewById(R.id.child_layout);
        final int groupPosition = (int) v.getTag(R.id.group_num);
        final int childPosition = (int) v.getTag(R.id.child_num);

        Snackbar snackbar = Snackbar.make(view, "Delete?", Snackbar.LENGTH_LONG)
                .setAction("Delete", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        manList.get(groupPosition).deleteModel(childPosition);
                        notifyDataSetChanged();
                    }
                });
        snackbar.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
