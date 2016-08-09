package com.jerry.greentree.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jerry.greentree.R;
import com.jerry.greentree.adapter.CategoryAdapter;
import com.jerry.greentree.entity.Category;
import com.jerry.greentree.entity.KeyWord;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_hotel_keyword_select)
public class HotelKeywordSelectActivity extends Activity
{
    private static final String TAG = "HotelKeywordActivity";
    @ViewInject(R.id.lv_keyword)
    private ListView mLvkeyword;

    @ViewInject(R.id.lv_sub_keyword)
    private ListView mLvSubKeyword;

    // 大分类的适配器
    private CategoryAdapter<KeyWord.ResponseDataEntity> mCategoryAdapter;
    // 大分类集合
    private List<KeyWord.ResponseDataEntity> categories = new ArrayList<>();

    // 小分类的适配器
    private CategoryAdapter<KeyWord.ResponseDataEntity.ItemsEntity> mItemAdatper;
    // 小分类集合
    private List<KeyWord.ResponseDataEntity.ItemsEntity> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);



        initViews();

        loadData();
    }

    // 初始化视图
    private void initViews()
    {
        mCategoryAdapter = new CategoryAdapter<>(this, categories,
                new CategoryAdapter.ChangeCategoryListener()
        {
            @Override
            public void changeCategory(int position)
            {
                // 清理右边子分类
                items.clear();

                // 添加当前位置的子分类
                items.addAll(categories.get(position).getItems());

                mItemAdatper.notifyDataSetChanged();
            }
        });
        mLvkeyword.setAdapter(mCategoryAdapter);

        mItemAdatper = new CategoryAdapter<>(this, items,
                new CategoryAdapter.ChangeCategoryListener()
        {
            @Override
            public void changeCategory(int position)
            {
                KeyWord.ResponseDataEntity.ItemsEntity entity = items.get(position);

                // 关键字id
                String keywordId = entity.getItemId();

                // 关键字名称
                String keywordName = entity.getTitle();

                Intent intent = new Intent();
                intent.setAction("action.select");
                intent.putExtra("keywordId", keywordId);
                intent.putExtra("keywordName",keywordName);
                // 发送广播
                sendBroadcast(intent);

                // 关闭Activity
                HotelKeywordSelectActivity.this.finish();
            }
        });
        mLvSubKeyword.setAdapter(mItemAdatper);
    }

    // 加载关键字
    private void loadData()
    {
        Intent intent = getIntent();

        Map<String,Object> params = new HashMap<>();
        params.put("cityId",intent.getStringExtra("cityId") == null
                            ? "447"
                            : intent.getStringExtra("cityId"));

        GreenTreeNetworkUtil.getInstance().getHotelKeywords(
                params,
                new RequestDataCallback()
                {
                    @Override
                    public void onSuccess(String response)
                    {
                        Gson gson = new Gson();
                        KeyWord keyWord = gson.fromJson(response, KeyWord.class);

                        if (keyWord != null && keyWord.getResponseData() != null)
                        {
                            // 显示大分类
                            categories.addAll(keyWord.getResponseData());
                            mCategoryAdapter.notifyDataSetChanged();

                            // 默认显示第一个大分类的小分类
                            items.addAll(keyWord.getResponseData().get(0).getItems());
                            mItemAdatper.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error)
                    {
                        Log.i("liufujie test", "onFailure: 读取发生错误。。。。");
                    }
                });
    }
}
