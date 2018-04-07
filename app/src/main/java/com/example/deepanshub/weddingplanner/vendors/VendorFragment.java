package com.example.deepanshub.weddingplanner.vendors;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepanshub.weddingplanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendorFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    VendorRecyclerViewAdapter vendoradapter;
    List<Model> model;

    public VendorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vendor, container, false);

        mRecyclerView = view.findViewById(R.id.vendorRecyclerView);
        layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        model = new ArrayList<>();
        vendoradapter = new VendorRecyclerViewAdapter(model, getActivity());

        model.add(new Model(R.drawable.venue, "Venue"));
        model.add(new Model(R.drawable.catering, "Catering"));
        model.add(new Model(R.drawable.officiate, "Officiate"));
        model.add(new Model(R.drawable.photgrapher, "PhotoGrapher"));
        model.add(new Model(R.drawable.wedding_dj, "DJ"));
        model.add(new Model(R.drawable.photgrapher, "PhotoGrapher"));


        mRecyclerView.setAdapter(vendoradapter);
        return view;
    }

}
