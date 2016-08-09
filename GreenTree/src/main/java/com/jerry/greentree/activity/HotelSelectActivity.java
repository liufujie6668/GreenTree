package com.jerry.greentree.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jerry.greentree.R;
import com.jerry.greentree.adapter.HotelListAdapter;
import com.jerry.greentree.entity.Hotel;
import com.jerry.greentree.entity.KeyWord;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_hotel_select)
public class HotelSelectActivity extends Activity
{
    @ViewInject(R.id.plv_hotel_list)
    private PullToRefreshListView mPtrLvHotelList;

    // 查询类型
    private int mSelectType = 1;

    // 等待进度对话框
    private ProgressDialog mProgressDialog;

    // 适配器对象
    private HotelListAdapter<Hotel.ResponseData.HotelItem> mHotelListAdapter;

    // 酒店集合
    private List<Hotel.ResponseData.HotelItem> mHotelList;

    private String keywordId;
    private String cityId;
    private String checkInTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        mNetworkUtil = GreenTreeNetworkUtil.getInstance();

        initViews();

        // 关键字参数获取
        Intent intent = getIntent();
        keywordId = intent.getStringExtra("keywordId");
        cityId = intent.getStringExtra("cityId");
        checkInTime = intent.getStringExtra("checkInTime");

        loadData();
    }

    /**
     * 初始化视图
     */
    private void initViews()
    {
        // 显示正在加载
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在加载中...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

//        mPtrLvHotelList.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//            {
//
//            }
//        });

        // 适配器
        mHotelList = new ArrayList<>();
        mHotelListAdapter =
                new HotelListAdapter
                        <Hotel.ResponseData.HotelItem>(getApplicationContext(),mHotelList);

        mPtrLvHotelList.setAdapter(mHotelListAdapter);

        mPtrLvHotelList.getRefreshableView().setDivider(null);
        mPtrLvHotelList.getRefreshableView().setSelector(android.R.color.transparent);

        // 同时支持上下拉刷新
        mPtrLvHotelList.setMode(PullToRefreshBase.Mode.BOTH);

        // 设置事件

        // 设置PullToRefreshListView的监听
        mPtrLvHotelList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            // 处理下拉刷新事件
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                //mHotelList.clear();

                // 加载第一页
                mCurrentPageNO = 1;

                loadData();
            }

            // 处理上拉刷新的事件
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
            {
                // 加载下一页
                mCurrentPageNO++;

                loadData();
            }
        });

//        // 如果只是需要上拉或者下拉刷新，则只需要设置为OnRefreshListener
//        mPtrLvHotelList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        mPtrLvHotelList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>()
//        {
//            // 无论是下拉，还是上拉，都只会调用此方法
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView)
//            {
//
//            }
//        });
    }

    // 事件设置

    /**
     * 回到首页
     */
    @OnClick(R.id.iv_home)
    private void goHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        this.finish();
    }

    /**
     * 切换酒店类型
     */
    @OnRadioGroupCheckedChange(R.id.rg_select_type)
    private void changeSelectType(RadioGroup radioGroup, int checkedId)
    {
        mCurrentPageNO = 1;
        mHotelList.clear();
        mPtrLvHotelList.getRefreshableView().setSelection(0);

        switch (checkedId)
        {
            case R.id.rb_referee:
                mSelectType = 0;
                break;
            case R.id.rb_distance:
                mSelectType = 1;
                break;

            case R.id.rb_price:
                mSelectType = 3;
                break;

            case R.id.rb_score:
                mSelectType = 4;
                break;

            case R.id.rb_filter:
                break;
        }

        loadData();

    }

    private Map<String,Object> params = new HashMap<>();

    private int mCurrentPageNO = 1;

    private GreenTreeNetworkUtil mNetworkUtil;

    /**
     * 加载数据
     */
    private void loadData()
    {
        params.clear();

        // 设置参数
        params.put("pagesize",10);
        params.put("ordertype",mSelectType);
        params.put("pageindex",mCurrentPageNO);
        params.put("keyword",keywordId);
        params.put("cityId",cityId);
        params.put("checkintime",checkInTime);

        // 访问网络，获取酒店数据

        // 获取酒店数据
        mNetworkUtil.getHotelList(params, new RequestDataCallback()
        {
            // 成功获取酒店数据
            @Override
            public void onSuccess(String response)
            {
                // 解析酒店数据
                Gson gson = new Gson();
                Hotel hotel = gson.fromJson(response, Hotel.class);

                if (mCurrentPageNO == 1)
                {
                    mHotelList.clear();
                }

                if (hotel != null && hotel.getResponseData() != null)
                {
                    // 添加酒店数据
                    mHotelList.addAll(hotel.getResponseData().getItems());

                    // 刷新列表视图
                    mHotelListAdapter.notifyDataSetChanged();
                }

                mPtrLvHotelList.onRefreshComplete();

                // 取消ProgressDialog
                if (mProgressDialog != null && mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(HttpException error)
            {
                mPtrLvHotelList.onRefreshComplete();

                // 取消ProgressDialog
                if (mProgressDialog != null && mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    // 生命周期方法
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
