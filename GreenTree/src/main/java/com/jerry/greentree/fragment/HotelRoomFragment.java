package com.jerry.greentree.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jerry.greentree.R;
import com.jerry.greentree.adapter.RoomListAdapter;
import com.jerry.greentree.entity.HotelDetailMessage;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class HotelRoomFragment extends Fragment
{
    @ViewInject(R.id.lv_rooms)
    private ListView mLvRooms;

    private List<HotelDetailMessage.ResponseDataEntity.RoomsEntity> romms;

    public HotelRoomFragment()
    {
    }

    /**
     * 工厂模式
     *
     * @param romms
     * @return
     */
    public static HotelRoomFragment
    newInstance(List<HotelDetailMessage.ResponseDataEntity.RoomsEntity> romms)
    {
        HotelRoomFragment fragment = new HotelRoomFragment();

        Bundle args = new Bundle();

        args.putSerializable("rooms", (ArrayList) romms);

        fragment.setArguments(args);

        return fragment;
    }

    /**
     * 获取参数
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null)
        {
            romms = (ArrayList) bundle.getSerializable("rooms");
        }
    }

    /**
     * 创建视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.frag_hotel_room, container, false);

        ViewUtils.inject(this, view);

        return view;
    }

    private RoomListAdapter roomListAdapter;

    /**
     * 视图加载完毕后调用
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        roomListAdapter = new RoomListAdapter(getActivity(), romms);
        mLvRooms.setAdapter(roomListAdapter);

        //setHeight();
    }

    /**
     * 设置ListView&GridView高度
     */
    public void setHeight()
    {
        int listViewHeight = 0;

        int count = roomListAdapter.getCount();

        for (int i = 0; i < count; i++)
        {
            View temp = roomListAdapter.getView(i, null, mLvRooms);
            temp.measure(0, 0);
            listViewHeight += temp.getMeasuredHeight();
        }

        ViewGroup.LayoutParams layoutParams = this.mLvRooms.getLayoutParams();

        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = listViewHeight;

        mLvRooms.setLayoutParams(layoutParams);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();

    }
}
