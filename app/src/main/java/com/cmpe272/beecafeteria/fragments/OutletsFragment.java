package com.cmpe272.beecafeteria.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cmpe272.beecafeteria.R;
import com.cmpe272.beecafeteria.adapter.OutletsAdapter;
import com.cmpe272.beecafeteria.modelResponse.Outlet;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OutletsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OutletsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    @Bind(R.id.outletList)
    TwoWayView outletList;

    //Context of application
    private Context context;

    //Adapter object to set in RecyclerList of outlets
    private OutletsAdapter outletsAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment OutletsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OutletsFragment newInstance(String param1) {
        OutletsFragment fragment = new OutletsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public OutletsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_outlets, container, false);
        ButterKnife.bind(this,view);

        outletsAdapter = new OutletsAdapter(context,getData());

        outletList.setAdapter(outletsAdapter);

        final ItemClickSupport itemClick = ItemClickSupport.addTo(outletList);

        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int i, long l) {
                Toast.makeText(context,"Item clicked: " + i,Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    /**
     * Return data for navigation drawer
     * @return list of navigation drawer items
     */
    public static List<Outlet> getData() {
        List<Outlet> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < 3; i++) {
            Outlet outlet = new Outlet();
            outlet.setOutletName("Subway");
            outlet.setExpenseRating(3);
            data.add(outlet);
        }
        return data;
    }

}
