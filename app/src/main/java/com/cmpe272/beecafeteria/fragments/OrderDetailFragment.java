package com.cmpe272.beecafeteria.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmpe272.beecafeteria.R;
import com.cmpe272.beecafeteria.modelResponse.Order;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rushil on 11/20/2015.
 */
public class OrderDetailFragment extends Fragment {

    @Bind(R.id.txtOutlet)
    TextView txtOutlet;

    @Bind(R.id.txtCounter)
    TextView txtCounter;

    @Bind(R.id.txtDescription)
    TextView txtDescription;

    @Bind(R.id.txtDate)
    TextView txtDate;

    @Bind(R.id.txtStatus)
    TextView txtStatus;

    @Bind(R.id.txtTotal)
    TextView txtTotal;

    //Context of application
    private Context context;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "order_details";

    private Order orderDetails;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param orderDetails Parameter 1.
     * @return A new instance of fragment DealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailFragment newInstance(Order orderDetails) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, Parcels.wrap(orderDetails));
        fragment.setArguments(args);
        return fragment;
    }

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderDetails = Parcels.unwrap(getArguments().getParcelable(ARG_PARAM1));
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
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        ButterKnife.bind(this, view);

        if(orderDetails != null){
            txtOutlet.setText(orderDetails.getOutlateName());
            txtCounter.setText(orderDetails.getCounter());
            txtDescription.setText(orderDetails.getOrderDescription());
            txtDate.setText(orderDetails.getOrderDate());
            txtStatus.setText(orderDetails.getStatus());
            txtTotal.setText("$ "+orderDetails.getTotal());
        }

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}