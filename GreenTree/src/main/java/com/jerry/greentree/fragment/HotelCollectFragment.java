package com.jerry.greentree.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jerry.greentree.R;
import com.jerry.greentree.activity.HotelDetailActivity;
import com.jerry.greentree.adapter.HotelCollectAdapter;
import com.jerry.greentree.app.GreenTreeApplication;
import com.jerry.greentree.db.HotelCollect;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

public class HotelCollectFragment extends Fragment
{
    public static HotelCollectFragment newInstance()
    {
        HotelCollectFragment fragment = new HotelCollectFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public HotelCollectFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_hotel_collect, container, false);

        ViewUtils.inject(this, view);

        return view;
    }

    @ViewInject(R.id.lv_hotel_collect_list)
    private ListView lvHotelCollect;

    private DbUtils dbUtils;

    private List<HotelCollect> hotelCollectList;

    private HotelCollectAdapter hotelCollectAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbUtils = GreenTreeApplication.getApp().getDbUtils();

        try {
            hotelCollectList = dbUtils.findAll(HotelCollect.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (hotelCollectList != null){
            hotelCollectAdapter = new HotelCollectAdapter(getActivity(), hotelCollectList);
            lvHotelCollect.setAdapter(hotelCollectAdapter);
        }

        lvHotelCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String hotelId = hotelCollectList.get(position).hotelId;
                String isCollect = "COLLECT";

                Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
                intent.putExtra("hotelId", hotelId);
                intent.putExtra("Collect", isCollect);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

}
