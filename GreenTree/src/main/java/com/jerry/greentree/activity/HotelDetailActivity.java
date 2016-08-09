package com.jerry.greentree.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.greentree.R;
import com.jerry.greentree.adapter.BannerListAdapter;
import com.jerry.greentree.app.GreenTreeApplication;
import com.jerry.greentree.db.HotelCollect;
import com.jerry.greentree.entity.HotelDetailMessage;
import com.jerry.greentree.fragment.HotelDescMessageFragment;
import com.jerry.greentree.fragment.HotelRoomFragment;
import com.jerry.greentree.fragment.HotelTopicFragment;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.onekeyshare.OnekeyShare;

@ContentView(R.layout.activity_hotel_detail)
public class HotelDetailActivity extends FragmentActivity
{
    private static final String TAG = "HotelDetailActivity";

    @ViewInject(R.id.vp_hotel_images)
    private ViewPager mVpBanner;

    @ViewInject(R.id.ll_point)
    private LinearLayout mLlPoints;

    @ViewInject(R.id.tv_hotel_name)
    private TextView mTvHotelName;

    @ViewInject(R.id.tv_hotel_addr)
    private TextView mTvHotelAddr;

    @ViewInject(R.id.iv_wifi)
    private ImageView mIvWiFi;

    @ViewInject(R.id.iv_park)
    private ImageView mIvPark;

    @ViewInject(R.id.iv_breakfast)
    private ImageView mIvBreakfast;

    @ViewInject(R.id.iv_collect)
    private ImageView mIvCollect;

    @ViewInject(R.id.iv_share)
    private ImageView mIvShare;

    @ViewInject(R.id.iv_call)
    private ImageView mIvCall;

    private String hotelId = null;
    private String collect = null;

    private DbUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        initViews();

        // 获取帮助类
        networkUtil = GreenTreeNetworkUtil.getInstance();

        // 获取DBUtils
        dbUtils = GreenTreeApplication.getApp().getDbUtils();

        // 获取酒店ID
        Intent intent = getIntent();
        hotelId = intent.getStringExtra("hotelId");
        collect = intent.getStringExtra("Collect");
        loadHotelDetail();
    }

    // 酒店图片内容
    private BannerListAdapter mAdapter;
    private List<HotelDetailMessage.ResponseDataEntity.ImagesEntity> mList;

    // 酒店房间
    private List<HotelDetailMessage.ResponseDataEntity.RoomsEntity> mRoomsList;

    // 视图处理
    private void initViews()
    {
        setViewFacusableAndClick(false);

        // 显示酒店图片显示
        mList = new ArrayList<>();
        mAdapter = new BannerListAdapter(getApplicationContext(), mList);

        // 设置ViewPager适配器
        mVpBanner.setAdapter(mAdapter);

        // ViewPager设置页面改变监听
        mVpBanner.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                // 切换选择圆点
                mImageViews.get(mCurrentPageNO).setSelected(false);
                mCurrentPageNO = position;
                mImageViews.get(position).setSelected(true);
            }
        });

        mRoomsList = new ArrayList<>();
    }

    /**
     * 返回实现
     * @param view
     */
    @OnClick(R.id.iv_back)
    public void doBack(View view){
        //判断是否由收藏跳转的
        if (collect != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
        this.finish();
    }

    /**
     * 收藏实现
     * @param view
     */
    @OnClick(R.id.iv_collect)
    public void doCollect(View view)
    {
        HotelCollect hotelCollect = null;
        try
        {
            hotelCollect = dbUtils.findFirst(
                    Selector.from(
                        HotelCollect.class).where("hotel_id","=",hotelId));

            // 如果收藏了，则取消收藏
            if (hotelCollect != null)
            {
                // 删除收藏的内容
                dbUtils.delete(HotelCollect.class,
                               WhereBuilder.b("hotel_id","=",hotelId));

                Toast.makeText(HotelDetailActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
            }
            // 如果没有收藏，则收藏
            else if (hotelCollect == null)
            {
                hotelCollect = new HotelCollect();
                hotelCollect.hotelId = hotelId;
                hotelCollect.hotelName = responseData.getName();
                hotelCollect.hotelImageUrl = responseData.getImages().get(0).getImg();

                dbUtils.save(hotelCollect);

                Toast.makeText(HotelDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            }
        }
        catch (DbException e)
        {
            Log.i(TAG, "doCollect: 出现异常");
            e.printStackTrace();
        }
    }

    /**
     * 分享
     * @param view
     */
    @OnClick(R.id.iv_share)
    public void doShare(View view)
    {
        // 一键分享对象
        OnekeyShare oks = new OnekeyShare();

        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 设置酒店名称
        oks.setTitle(responseData.getName());

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");

        // text是分享文本，所有平台都需要这个字段
        oks.setText(responseData.getDescription());

        // 设置酒店图片
        oks.setImageUrl(responseData.getImages().get(0).getImg());

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");

        // site是分享此内容的网站名称，仅在QQ空间&QQ使用
        oks.setSite(getString(R.string.app_name));

        // siteUrl是分享此内容的网站地址，仅在QQ空间&QQ使用
        oks.setSiteUrl("http://www.baidu.com");

        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 拨打电话
     * @param view
     */
    @OnClick(R.id.iv_call)
    private void takeCall(View view){
        String telNum = responseData.getPhone();
        Toast.makeText(this, telNum, Toast.LENGTH_SHORT).show();
        if (telNum != null){
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telNum));
            startActivity(intent);
        }
    }

    /**
     * 切换酒店信息
     * @param rg
     * @param checkedId
     */
    @OnRadioGroupCheckedChange(R.id.rg_hotel_detail)
    public void changeHotelMessage(RadioGroup rg, int checkedId)
    {
        fragmentManager = getSupportFragmentManager();

        switch (checkedId)
        {
            case R.id.rb_rooms:
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_message, HotelRoomFragment.newInstance(mRoomsList))
                        .commit();
                break;
            case R.id.rb_desc:
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_message,
                                HotelDescMessageFragment.newInstance(responseData.getDescription()))
                        .commit();
                break;
            case R.id.rb_topic:
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_message, HotelTopicFragment.newInstance(hotelId))
                        .commit();
                break;
        }
    }

    private GreenTreeNetworkUtil networkUtil;
    private HotelDetailMessage.ResponseDataEntity responseData;

    // 数据处理
    private void loadHotelDetail()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("hotelId", hotelId);

        // 获取酒店详情数据
        networkUtil.getHotelDetailMessage(
                params,
                new RequestDataCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Gson gson = new Gson();

                        HotelDetailMessage message =
                                gson.fromJson(response, HotelDetailMessage.class);

                        if (message != null && message.getResponseData() != null) {
                            responseData = message.getResponseData();

                            // 先获取酒店图片
                            mList.addAll(responseData.getImages());
                            mAdapter.notifyDataSetChanged();

                            // 添加导航圆点
                            addNavPoints();

                            // 酒店信息

                            // 酒店名称
                            mTvHotelName.setText(responseData.getName());

                            // 酒店地址
                            mTvHotelAddr.setText(responseData.getAddress());

                            // 提供的服务
                            List<String> services =
                                    responseData.getService();

                            // 显示酒店服务
                            for (String service : services) {
                                if ("41".equals(service)) {
                                    mIvWiFi.setVisibility(View.VISIBLE);
                                } else if ("51".equals(service)) {
                                    mIvPark.setVisibility(View.VISIBLE);
                                } else if ("91".equals(service)) {
                                    mIvBreakfast.setVisibility(View.VISIBLE);
                                }
                            }

                            // 获取酒店房间数据
                            mRoomsList.addAll(responseData.getRooms());

                            // 显示房间数据
                            showRooms();

                            setViewFacusableAndClick(true);

                        }
                    }

                    @Override
                    public void onFailure(HttpException error)
                    {

                    }
                });
    }

    /**
     * 设置控件是否可以点击
     * @param b
     */
    private void setViewFacusableAndClick(boolean b){
        mIvCollect.setFocusable(b);
        mIvCollect.setClickable(b);

        mIvShare.setFocusable(b);
        mIvShare.setClickable(b);

        mIvCall.setFocusable(b);
        mIvCall.setClickable(b);
    }

    private FragmentManager fragmentManager;

    /**
     * 显示酒店房间
     */
    private void showRooms()
    {
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                        .replace(R.id.fl_message, HotelRoomFragment.newInstance(mRoomsList))
                        .commit();
    }

    // 用于保存已经添加的圆点
    private List<ImageView> mImageViews = new ArrayList<>();

    // 当前选择页
    private int mCurrentPageNO = 0;

    /**
     * 添加圆点
     */
    private void addNavPoints()
    {
        if (mList == null || mList.isEmpty())
        {
            return;
        }

        ImageView addedImageView = null;

        // 布局参数
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        params.rightMargin = 10;

        for (int i = 0; i < mList.size(); i++)
        {
            addedImageView = new ImageView(this);

            // 设置背景
            addedImageView.setBackgroundResource(R.drawable.selector_nav_points);

            // 默认不选择
            addedImageView.setSelected(false);

            // 设置布局参数
            addedImageView.setLayoutParams(params);

            // 添加到布局中
            mLlPoints.addView(addedImageView);

            // 添加到集合中
            mImageViews.add(addedImageView);
        }

        // 默认选择第一个
        mImageViews.get(0).setSelected(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
