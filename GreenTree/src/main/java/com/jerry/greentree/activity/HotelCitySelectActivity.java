package com.jerry.greentree.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jerry.greentree.R;
import com.jerry.greentree.adapter.CityAdapter;
import com.jerry.greentree.entity.City;
import com.jerry.greentree.entity.CitySortEntity;
import com.jerry.greentree.entity.HotelTopic;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.jerry.greentree.view.SlideBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_hotel_city_select)
public class HotelCitySelectActivity extends Activity
{
    @ViewInject(R.id.lv_city)
    private ListView mLvCity;

    @ViewInject(R.id.tv_letter)
    private TextView mTvLetter;

    @ViewInject(R.id.sbar_letter)
    private SlideBar mSlideBar;

    // 城市列表
    private List<CitySortEntity> citys = new ArrayList<CitySortEntity>();
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        initViews();

        loadCity();
    }

    /**
     *
     */
    private void initViews()
    {
        cityAdapter = new CityAdapter(this,citys);
        mLvCity.setAdapter(cityAdapter);

        // 设置类型选择监听
        mSlideBar.setChangeCityCategoryListener(new SlideBar.ChangeCityCategoryListener()
        {
            @Override
            public void changed(String hitString)
            {
                // 通过这个位置来改变城市列表中选择的类别

                // 获取选择字母，在城市列表中该字母第一次出现的位置
                int position = -1;

                for (int i = 0; i < citys.size(); i++)
                {
                    if (hitString.equals(citys.get(i).letter))
                    {
                        position = i;
                        break;
                    }
                }

                mLvCity.setSelection(position);

            }
        });

        mSlideBar.setTipTextView(mTvLetter);
    }

    /**
     * 加载城市
     */
    private void loadCity()
    {
        GreenTreeNetworkUtil.getInstance().getCitys(new RequestDataCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Gson gson = new Gson();
                City city = gson.fromJson(response, City.class);

                // 热门
                List<City.ResponseDataEntity.HotEntity>
                                hots = city.getResponseData().getHot();

                CitySortEntity citySortEntity = null;

                // 把热门城市添加在集合中
                for (City.ResponseDataEntity.HotEntity entity : hots)
                {
                    citySortEntity = new CitySortEntity();
                    citySortEntity.letter = "热门";
                    citySortEntity.cityName = entity.getCityName();
                    citySortEntity.cityId = entity.getId();

                    citys.add(citySortEntity);

                }

                // ["热门",226,"上海"]
                // ["热门",447,"北京"]
                // ["热门",221,"南京"]

                // 以首字母排序的城市列表
                List<City.ResponseDataEntity.CommonEntity>
                                commons = city.getResponseData().getCommon();

                // 首字母循环
                for (City.ResponseDataEntity.CommonEntity common : commons)
                {
                    // 对应字母城市循环
                    for (City.ResponseDataEntity.CommonEntity.ItemsEntity item : common.getItems())
                    {
                        citySortEntity = new CitySortEntity();
                        citySortEntity.letter = common.getTitle();
                        citySortEntity.cityName = item.getCityName();
                        citySortEntity.cityId = item.getId()+"";

                        citys.add(citySortEntity);
                    }
                }

                cityAdapter.notifyDataSetChanged();

                // ["热门",226,"上海"]
                // ["热门",447,"北京"]
                // ["热门",221,"南京"]

                // ["A",664,"安庆"]
                // ["A",3590,"安吉县"]

                // ["B",3612,"巴州"]
                // ["B",3006,"白山"]
                // .....
            }

            @Override
            public void onFailure(HttpException error)
            {

            }
        });
    }

    /**
     * 显示视图内容
     */
    private void setViews()
    {

    }
}
