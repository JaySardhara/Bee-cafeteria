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
import com.cmpe272.beecafeteria.adapter.DailyDealsAdapter;
import com.cmpe272.beecafeteria.modelResponse.DailyDeals;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DealsFragment extends Fragment {


    @Bind(R.id.dealsList)
    TwoWayView dailyDealsList;

    //Context of application
    private Context context;

    //Adapter object to set in RecyclerList of outlets
    private DailyDealsAdapter dailyDealsAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DealsFragment newInstance(String param1) {
        DealsFragment fragment = new DealsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public DealsFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_deals, container, false);
        ButterKnife.bind(this, view);

        dailyDealsAdapter = new DailyDealsAdapter(context,getData());

        dailyDealsList.setAdapter(dailyDealsAdapter);

        final ItemClickSupport itemClick = ItemClickSupport.addTo(dailyDealsList);

        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int i, long l) {
                Toast.makeText(context,"Deal Selected: " + i, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Return data for navigation drawer
     * @return list of navigation drawer items
     */
    public static List<DailyDeals> getData() {
        List<DailyDeals> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < 3; i++) {
            DailyDeals dailyDeals = new DailyDeals();
            dailyDeals.setDailyDealTitle("Subway 20-20");
            dailyDeals.setDailyDealDescription("Get 20% off on each veggies between 6:00-9:00 PM");
            data.add(dailyDeals);
        }
        return data;
    }

}
