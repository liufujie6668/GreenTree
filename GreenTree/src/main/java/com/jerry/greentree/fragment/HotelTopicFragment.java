package com.jerry.greentree.fragment;

/**
 * Created by liufujie on 15-12-31.
 */
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.jerry.greentree.R;
import com.jerry.greentree.entity.HotelTopic;
import com.jerry.greentree.util.GreenTreeNetworkUtil;
import com.jerry.greentree.util.RequestDataCallback;
import com.lidroid.xutils.exception.HttpException;

import java.util.HashMap;
import java.util.Map;

public class HotelTopicFragment extends Fragment
{
    private TextView tvAvgScore,tvComment;

    private ProgressBar pbSleep;
    private ProgressBar pbService;
    private ProgressBar pbBath;
    private ProgressBar pbRoom;

    private TextView tvScoreSleep;
    private TextView tvScoreService;
    private TextView tvScoreBath;
    private TextView tvScoreRoom;

    private String hotelId = "";

    public HotelTopicFragment()
    {

    }

    public static HotelTopicFragment newInstance(String hotelId)
    {
        HotelTopicFragment topicFragment = new HotelTopicFragment();

        Bundle bundle = new Bundle();

        bundle.putString("hotelId",hotelId);

        topicFragment.setArguments(bundle);

        return topicFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            hotelId = bundle.getString("hotelId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.frag_hotel_detail_topic, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        tvAvgScore = (TextView) view.findViewById(R.id.tv_avg_score);
        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        tvScoreSleep = (TextView) view.findViewById(R.id.tv_score_sleep);
        pbSleep = (ProgressBar) view.findViewById(R.id.pb_sleep);
        tvScoreService = (TextView) view.findViewById(R.id.tv_score_service);
        pbService = (ProgressBar) view.findViewById(R.id.pb_service);
        tvScoreBath = (TextView) view.findViewById(R.id.tv_score_bath);
        pbBath = (ProgressBar) view.findViewById(R.id.pb_bath);
        tvScoreRoom = (TextView) view.findViewById(R.id.tv_score_room);
        pbRoom = (ProgressBar) view.findViewById(R.id.pb_room);

        loadTopicData();
    }

    /**
     * 加载数据
     */
    private void loadTopicData()
    {
        Map<String,Object> params = new HashMap<>();
        params.put("hotelId",hotelId);

        GreenTreeNetworkUtil.getInstance().getHotelTopicMessage(params, new RequestDataCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                Gson gson = new Gson();
                HotelTopic topic = gson.fromJson(response, HotelTopic.class);

                if (topic != null && topic.getResponseData() != null)
                {
                    setViews(topic.getResponseData());
                }
            }

            @Override
            public void onFailure(HttpException error)
            {

            }
        });
    }

    /**
     * 设置视图内容
     */
    private void setViews(HotelTopic.ResponseDataEntity responseData)
    {

//        tvAvgScore = (TextView) view.findViewById(R.id.tv_avg_score);
//        tvComment = (TextView) view.findViewById(R.id.tv_comment);
//        tvScoreSleep = (TextView) view.findViewById(R.id.tv_score_sleep);
//        pbSleep = (ProgressBar) view.findViewById(R.id.pb_sleep);
//        tvScoreService = (TextView) view.findViewById(R.id.tv_score_service);
//        pbService = (ProgressBar) view.findViewById(R.id.pb_service);
//        tvScoreBath = (TextView) view.findViewById(R.id.tv_score_bath);
//        pbBath = (ProgressBar) view.findViewById(R.id.pb_bath);
//        tvScoreRoom = (TextView) view.findViewById(R.id.tv_score_room);
//        pbRoom = (ProgressBar) view.findViewById(R.id.pb_room);

        tvAvgScore.setText(responseData.getScore().getAvgPoint());

        tvComment.setText("（"+responseData.getTotalItems()+"条评论)");

        tvScoreSleep.setText(responseData.getScore().getSleepPoint());

        tvScoreService.setText(responseData.getScore().getServicePoint());

        tvScoreRoom.setText(responseData.getScore().getHealthPoint());

        tvScoreBath.setText(responseData.getScore().getLavePoint());

        // 2.0

        pbSleep.setProgress((int)(Float.parseFloat(
                responseData.getScore().getSleepPoint()) * 10));

        pbService.setProgress((int)(Float.parseFloat(
                responseData.getScore().getServicePoint()) * 10));

        pbRoom.setProgress((int)(Float.parseFloat(
                responseData.getScore().getHealthPoint()) * 10));

        pbBath.setProgress((int)(Float.parseFloat(
                responseData.getScore().getLavePoint()) * 10));


    }

}
