package com.jerry.greentree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jerry.greentree.R;
import com.jerry.greentree.app.GreenTreeApplication;
import com.jerry.greentree.db.HotelCollect;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by liufujie on 2016/4/24.
 */
public class HotelCollectAdapter extends BaseAdapter {

    private List<HotelCollect> hotelCollectList;

    private Context context;

    private LayoutInflater layoutInflater;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public HotelCollectAdapter(Context context, List<HotelCollect> hotelCollectList) {
        this.hotelCollectList = hotelCollectList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

        imageLoader = GreenTreeApplication.getApp().getImageLoader();
        options = GreenTreeApplication.getApp().getImageOptions();
    }

    @Override
    public int getCount() {
        return hotelCollectList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotelCollectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.hotel_collect_item, null);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_hotel_image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_hotel_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HotelCollect hotelCollect = hotelCollectList.get(position);

        //加载图片
        imageLoader.displayImage(hotelCollect.hotelImageUrl, viewHolder.imageView, options);

        viewHolder.textView.setText(hotelCollect.hotelName.toString());

        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
