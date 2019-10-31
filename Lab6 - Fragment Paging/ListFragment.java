package com.cartmell.travis.tcartmelllab6;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private static final String ARG_LIST = "key";
    private ArrayList<Manufacturer> manList;


    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(ArrayList<Manufacturer> manList) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST, manList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            manList = (ArrayList<Manufacturer>) getArguments().getSerializable(ARG_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        Adapter adapter = new Adapter(getActivity(), manList);
        ExpandableListView expand = view.findViewById(R.id.list_frag_expandable);
        expand.setAdapter(adapter);
        expand.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                MainActivity activity = (MainActivity) getActivity();
                activity.changePage(manList.get(groupPosition).muscle_car_models.get(childPosition));
                //Toast.makeText(getContext(), "Make: " + manList.get(groupPosition).getManufacturerName() + " Model: " + manList.get(groupPosition).getModelName(childPosition), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        //delete original expandable list view in activity_main. Then move viewpager to activity_main.

        // Inflate the layout for this fragment
        return view;
    }
}
