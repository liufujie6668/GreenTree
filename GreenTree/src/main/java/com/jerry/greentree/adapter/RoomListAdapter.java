package com.jerry.greentree.adapter;

/**
 * Created by Administrator on 15-12-31.
 */


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jerry.greentree.R;
import com.jerry.greentree.entity.HotelDetailMessage;

public class RoomListAdapter extends BaseAdapter
{
    private List<HotelDetailMessage.ResponseDataEntity.RoomsEntity>
            objects = new ArrayList<HotelDetailMessage.ResponseDataEntity.RoomsEntity>();

    private Context context;
    private LayoutInflater layoutInflater;

    public RoomListAdapter(Context context,
                           List<HotelDetailMessage.ResponseDataEntity.RoomsEntity> objects)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    @Override
    public int getCount()
    {
        return objects.size();
    }

    @Override
    public HotelDetailMessage.ResponseDataEntity.RoomsEntity getItem(int position)
    {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.frag_rooms_list, null);
            viewHolder = new ViewHolder();

            viewHolder.tvRoomType = (TextView) convertView.findViewById(R.id.tv_room_type);
            viewHolder.tvRoomPrice = (TextView) convertView.findViewById(R.id.tv_room_price);
            viewHolder.tvRoomIsFull = (TextView) convertView.findViewById(R.id.tv_room_isFull);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取酒店对象
        HotelDetailMessage.ResponseDataEntity.RoomsEntity room = getItem(position);

        viewHolder.tvRoomType.setText(room.getTypeName());

        viewHolder.tvRoomPrice.setText(room.getPrice());

        if ("true".equals(room.getIsFull()))
        {
            viewHolder.tvRoomIsFull.setText("满房");
        }
        else
        {
            viewHolder.tvRoomIsFull.setText("未满");
            viewHolder.tvRoomIsFull.setTextColor(
                    context.getResources().getColor(R.color.color_orange));
        }

        return convertView;
    }

    protected class ViewHolder
    {
        private TextView tvRoomType;
        private TextView tvRoomPrice;
        private TextView tvRoomIsFull;
    }
}
