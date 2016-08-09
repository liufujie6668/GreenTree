package com.jerry.greentree.adapter;

/**
 * Created by Administrator on 16-1-6.
 */


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jerry.greentree.R;
import com.jerry.greentree.entity.CitySortEntity;

public class CityAdapter extends BaseAdapter
{

    private List<CitySortEntity> citys = new ArrayList<CitySortEntity>();

    private Context context;
    private LayoutInflater layoutInflater;

    public CityAdapter(Context context,List<CitySortEntity> citys)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.citys = citys;
    }

    @Override
    public int getCount()
    {
        return citys.size();
    }

    @Override
    public CitySortEntity getItem(int position)
    {
        return citys.get(position);
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
            convertView = layoutInflater.inflate(R.layout.select_city_item, null);
            viewHolder = new ViewHolder();

            viewHolder.category = (TextView) convertView.findViewById(R.id.category);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.category.setVisibility(View.GONE);

        // 获取CitySortEntity

        CitySortEntity entity = getItem(position);

        String letter = entity.letter;

        // 热门&A 列表中第一项 显示标题和第一个城市,第二项就不显示标题

        int hitIndex = -1;

        for (int i = 0; i < citys.size(); i++)
        {
            // 拿到现在处理的这个城市的letter
            // 和整个集合中的字母去比较
            // 如果找到第一个，则保留找到的位置，推出循环
            if (citys.get(i).letter.equals(letter))
            {
                hitIndex = i;
                break;
            }
        }

        // 如果和处理的位置相同，则认为是每个字母开始的第一个对象
        if (hitIndex == position)
        {
            viewHolder.category.setVisibility(View.VISIBLE);
            viewHolder.category.setText(entity.letter);
        }

        viewHolder.title.setText(entity.cityName);

        viewHolder.title.setTag(position);

        viewHolder.title.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // 获取位置
                Integer position = (Integer)view.getTag();

                CitySortEntity citySortEntity = citys.get(position);

                Intent intent = new Intent();

                intent.setAction("action.select.city");
                intent.putExtra("cityEntity", citySortEntity);

                context.sendBroadcast(intent);

                ((Activity)context).finish();
            }
        });

        // ["热门",226,"上海"]
        // ["热门",447,"北京"]
        // ["热门",221,"南京"]

        // ["A",664,"安庆"]
        // ["A",3590,"安吉县"]

        // ["B",3612,"巴州"]
        // ["B",3006,"白山"]

        return convertView;
    }

    protected class ViewHolder
    {
        private TextView category;
        private TextView title;
    }
}

