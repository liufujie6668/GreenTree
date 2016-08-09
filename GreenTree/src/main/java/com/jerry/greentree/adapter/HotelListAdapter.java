package com.jerry.greentree.adapter;

/**
 * Created by Administrator on 15-12-29.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.jerry.greentree.R;
import com.jerry.greentree.activity.HotelDetailActivity;
import com.jerry.greentree.app.GreenTreeApplication;
import com.jerry.greentree.entity.Hotel;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HotelListAdapter<T> extends BaseAdapter
{
    private List<T> objects = new ArrayList<T>();

    private Context context;
    private LayoutInflater layoutInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public HotelListAdapter(Context context,List<T> objects)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;

        imageLoader = GreenTreeApplication.getApp().getImageLoader();
        options = GreenTreeApplication.getApp().getImageOptions();
    }

    @Override
    public int getCount()
    {
        return objects.size();
    }

    @Override
    public T getItem(int position)
    {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.select_lv_item, null);
            viewHolder = new ViewHolder();

            ViewUtils.inject(viewHolder,convertView);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.position = position;

        // 列表中视图处理
        Hotel.ResponseData.HotelItem hotelItem
                = (Hotel.ResponseData.HotelItem)getItem(position);

        // 添加事件处理
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ViewHolder vh = (ViewHolder)view.getTag();
                Hotel.ResponseData.HotelItem hotelItem
                        = (Hotel.ResponseData.HotelItem)getItem(vh.position);

                // 跳转到详情页
                Intent intent = new Intent();

                intent.setClass(context,HotelDetailActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // 传递参数，酒店ID
                intent.putExtra("hotelId",hotelItem.getId());

                context.startActivity(intent);
            }
        });


        // 酒店名称
        viewHolder.selectLvItemTitle.setText(hotelItem.getName());

        // 酒店价格
        viewHolder.selectLvItemPrice.setText(hotelItem.getPrice());

        // 酒店评分
        viewHolder.selectLvItemScore.setText(hotelItem.getScore());

        // 先不显示图片
        viewHolder.selectLvItemIvWifi.setVisibility(View.GONE);
        viewHolder.selectLvItemIvPark.setVisibility(View.GONE);
        viewHolder.selectLvItemIvBreakfast.setVisibility(View.GONE);

        List<String> services = hotelItem.getService();
        for (String service : services)
        {
            if ("41".equals(service))
            {
                viewHolder.selectLvItemIvWifi.setVisibility(View.VISIBLE);
            }
            else if ("51".equals(service))
            {
                viewHolder.selectLvItemIvPark.setVisibility(View.VISIBLE);
            }
            else if ("91".equals(service))
            {
                viewHolder.selectLvItemIvBreakfast.setVisibility(View.VISIBLE);
            }
        }

        // 显示酒店图片
        imageLoader.displayImage(
                hotelItem.getImageUrl(),viewHolder.selectLvItemIv,options);

        return convertView;
    }

    protected class ViewHolder
    {
        @ViewInject(R.id.select_lv_item_iv)
        private ImageView selectLvItemIv;

        @ViewInject(R.id.select_lv_item_title)
        private TextView selectLvItemTitle;

        @ViewInject(R.id.select_lv_item_iv_wifi)
        private ImageView selectLvItemIvWifi;

        @ViewInject(R.id.select_lv_item_iv_park)
        private ImageView selectLvItemIvPark;

        @ViewInject(R.id.select_lv_item_iv_breakfast)
        private ImageView selectLvItemIvBreakfast;

        @ViewInject(R.id.select_lv_item_price)
        private TextView selectLvItemPrice;

        @ViewInject(R.id.select_lv_item_score)
        private TextView selectLvItemScore;

        // 列表项点击的位置
        private int position;
    }
}
