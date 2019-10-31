package com.cartmell.travis.tcartmelllab6;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        MainActivity act = (MainActivity) getActivity();
        Model model = act.getSelectedModel();

        TextView modelName = (TextView) view.findViewById(R.id.textModel);
        TextView modelYear = (TextView) view.findViewById(R.id.textYear);
        TextView modelEngine = (TextView) view.findViewById(R.id.textEngine);
        ImageView modelImage = (ImageView) view.findViewById(R.id.imageCar);

        modelName.setText(model.getName());
        modelYear.setText(model.getYear());
        modelEngine.setText(model.getEngine_size());
        modelImage.setImageResource(model.getId());

        // Inflate the layout for this fragment
        return view;
    }

}
