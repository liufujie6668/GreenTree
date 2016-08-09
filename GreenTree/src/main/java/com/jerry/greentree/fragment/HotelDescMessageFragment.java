package com.jerry.greentree.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.greentree.R;


public class HotelDescMessageFragment extends Fragment
{
    private String description;

    public HotelDescMessageFragment()
    {

    }

    public static HotelDescMessageFragment newInstance(String description)
    {
        HotelDescMessageFragment fragment = new HotelDescMessageFragment();

        Bundle args = new Bundle();
        args.putString("desc", description);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            description = getArguments().getString("desc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        TextView textView = new TextView(getActivity());

        textView.setText(description);

        return textView;
    }

}
