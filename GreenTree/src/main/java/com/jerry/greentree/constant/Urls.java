package com.jerry.greentree.constant;

/**
 * Created by jerry on 15/11/2.
 */
public class Urls
{
    // 首页广告栏接口
    public static final String BANNER_LIST =
            "http://app.greentree.cn/Api/index.php/Other/getBannerNew";

    // 酒店列表接口
    public static final String HOTEL_LIST =
            "http://app.greentree.cn/Api/index.php/Search/searchHotelBrand";

    // 酒店详情接口
    public static final String HOTEL_DETAIL =
            "http://app.greentree.cn/Api/index.php/Hotel/getHotelDetail_v240";

    // 酒店详情 - 评分
    public static final String HOTEL_DETAIL_TOPIC =
            "http://app.greentree.cn/Api/index.php/Topic/getHotelCommentsNew";

    // 关键字
    public static final String HOTEL_KEY_WORD =
            "http://app.greentree.cn/Api/index.php/City/getKeywords";

    // 入住城市
    public static final String CITY_LIST =
            "http://app.greentree.cn/Api/index.php/City/getCityListNew";
}
