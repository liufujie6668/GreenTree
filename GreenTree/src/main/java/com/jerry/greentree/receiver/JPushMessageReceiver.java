package com.jerry.greentree.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.jerry.greentree.activity.HotelDetailActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * JPush自定义消息处理
 */
public class JPushMessageReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        Bundle bundle = intent.getExtras();

        // 处理通知消息
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action))
        {

        }
        else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action))
        {
            // 解析通知内容

            // 获取酒店ID
            String jsonString = bundle.getString(JPushInterface.EXTRA_EXTRA);

            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);

                int hotelId = jsonObject.getInt("hotelId");

                // 打开酒店详情的Activity
                Intent hotelDetailIntent = new Intent();
                hotelDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                hotelDetailIntent.setClass(context, HotelDetailActivity.class);
                hotelDetailIntent.putExtra("hotelId", hotelId+"");
                context.startActivity(hotelDetailIntent);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        // 处理自定义消息
    }
}
