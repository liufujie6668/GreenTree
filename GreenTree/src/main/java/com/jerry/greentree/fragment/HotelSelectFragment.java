package com.jerry.greentree.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jerry.greentree.R;
import com.jerry.greentree.activity.HotelCitySelectActivity;
import com.jerry.greentree.activity.HotelKeywordSelectActivity;
import com.jerry.greentree.activity.HotelSelectActivity;
import com.jerry.greentree.entity.CitySortEntity;
import com.jerry.greentree.view.HotelSelectItemView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class HotelSelectFragment extends Fragment
{
    @ViewInject(R.id.item_city)
    private HotelSelectItemView item_city;

    @ViewInject(R.id.item_keyword)
    private HotelSelectItemView item_keyword;

    @ViewInject(R.id.item_time)
    private HotelSelectItemView item_time;

    @ViewInject(R.id.item_days)
    private HotelSelectItemView item_days;

    public HotelSelectFragment()
    {

    }

    public static HotelSelectFragment newInstance()
    {
        HotelSelectFragment fragment = new HotelSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }
        // 开启一个广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.select");
        intentFilter.addAction("action.select.city");
        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    // 查询关键字，在搜索酒店的时候需要传递此参数
    private String keywordId;
    private String checkInTime;
    private CitySortEntity cityEntity = new CitySortEntity();

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    // 此广播接收器用于接受查询条件
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();

            if ("action.select".equals(action))
            {
                // 关键字id
                keywordId = intent.getStringExtra("keywordId");

                // 关键字名称
                String keywordName = intent.getStringExtra("keywordName");

                // 设置自定义View显示的内容
                item_keyword.setContent(keywordName);
            }
            else if ("action.select.city".equals(action))
            {
                cityEntity = (CitySortEntity)
                        intent.getSerializableExtra("cityEntity");

                item_city.setContent(cityEntity.cityName);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_hotel_select, container, false);
        ViewUtils.inject(this, view);

        initViews();

        return view;
    }

    /**
     * 初始化视图
     */
    private void initViews()
    {
        item_city.setContent(getString(R.string.city));
        item_keyword.setContent(getString(R.string.key));
        item_time.setContent(getString(R.string.time));
        item_days.setContent(getString(R.string.day));
    }

    private CheckDateDialogFragment dialogFragment;

    /**
     * 选择入住时间
     * @param view
     */
    @OnClick(R.id.item_time)
    public void selectCheckDate(View view)
    {
        if (dialogFragment == null)
        {
            dialogFragment = new CheckDateDialogFragment();
        }
        dialogFragment.show(getChildFragmentManager(), "checkInTime");
    }

    /**
     * 使用DialogFragment显示时间选择控件
     */
    @SuppressLint("ValidFragment")
    public class CheckDateDialogFragment
                extends DialogFragment implements OnDateSelectedListener
    {
        AlertDialog dialog;
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {

            View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_calendar_view,null);

            MaterialCalendarView materialCalendarView
                    = (MaterialCalendarView) view.findViewById(R.id.cal_time);

            materialCalendarView.setOnDateChangedListener(this);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setView(view);

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialog.dismiss();
                }
            });

            dialog = builder.create();

            return dialog;
        }


        // CalendarView选择事件的回调方法
        @Override
        public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected)
        {
            Date selectDate = date.getDate();

            // yyyy-MM-dd
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            checkInTime = format.format(selectDate);

            // 设置到入住时间控件上
            item_time.setContent(checkInTime);

            this.dismiss();
        }
    }

    @OnClick(R.id.item_city)
    public void selectCity(View view)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), HotelCitySelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    /**
     * 选择入住天数
     * @param view
     */
    @OnClick(R.id.item_days)
    public void selectDays(View view){
        final EditText etDays = new EditText(getActivity());
        new AlertDialog.Builder(getActivity())
                .setTitle("请输入天数")
                .setView(etDays)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item_days.setContent(etDays.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 关键字搜索
     * @param view
     */
    @OnClick(R.id.item_keyword)
    public void selectKeyWord(View view)
    {
        Intent intent = new Intent();

        intent.setClass(getActivity(), HotelKeywordSelectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 传递的城市ID
        intent.putExtra("cityId", cityEntity.cityId);

        getActivity().startActivity(intent);

        // 用广播接收器来接受结果

        // 返回结果在Fragment中onActivityResult方法中获取
        //startActivityForResult(intent,100);

        // 返回结果在Fragment所在Activityy中onActivityResult方法中获取
        //getActivity().startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 搜索酒店
     * @param view
     */
    @OnClick(R.id.btn_hotel_select)
    public void selectHotel(View view)
    {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(getActivity(), HotelSelectActivity.class);
        intent.putExtra("keywordId", keywordId);
        intent.putExtra("cityId",cityEntity.cityId);
        intent.putExtra("checkInTime",checkInTime);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

}
