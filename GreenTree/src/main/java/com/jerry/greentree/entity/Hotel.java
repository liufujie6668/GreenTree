package com.jerry.greentree.entity;

import java.util.List;

/**
 * Created by jerry on 15/11/3.
 */
public class Hotel
{

    private String result;
    private String message;

    private ResponseData responseData;

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setResponseData(ResponseData responseData)
    {
        this.responseData = responseData;
    }

    public String getResult()
    {
        return result;
    }

    public String getMessage()
    {
        return message;
    }

    public ResponseData getResponseData()
    {
        return responseData;
    }

    public static class ResponseData
    {
        private String totalItems;
        private String totalPage;
        private String currentPage;


        private List<HotelItem> items;

        public void setTotalItems(String totalItems)
        {
            this.totalItems = totalItems;
        }

        public void setTotalPage(String totalPage)
        {
            this.totalPage = totalPage;
        }

        public void setCurrentPage(String currentPage)
        {
            this.currentPage = currentPage;
        }

        public void setItems(List<HotelItem> items)
        {
            this.items = items;
        }

        public String getTotalItems()
        {
            return totalItems;
        }

        public String getTotalPage()
        {
            return totalPage;
        }

        public String getCurrentPage()
        {
            return currentPage;
        }

        public List<HotelItem> getItems()
        {
            return items;
        }

        public static class HotelItem
        {
            private String id;
            private String name;
            private String imageUrl;
            private String score;
            private String distance;
            private String price;
            private String isFull;
            private String longitude;
            private String latitude;
            private String isPromotion;
            private List<String> service;

            public void setId(String id)
            {
                this.id = id;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public void setImageUrl(String imageUrl)
            {
                this.imageUrl = imageUrl;
            }

            public void setScore(String score)
            {
                this.score = score;
            }

            public void setDistance(String distance)
            {
                this.distance = distance;
            }

            public void setPrice(String price)
            {
                this.price = price;
            }

            public void setIsFull(String isFull)
            {
                this.isFull = isFull;
            }

            public void setLongitude(String longitude)
            {
                this.longitude = longitude;
            }

            public void setLatitude(String latitude)
            {
                this.latitude = latitude;
            }

            public void setIsPromotion(String isPromotion)
            {
                this.isPromotion = isPromotion;
            }

            public void setService(List<String> service)
            {
                this.service = service;
            }

            public String getId()
            {
                return id;
            }

            public String getName()
            {
                return name;
            }

            public String getImageUrl()
            {
                return imageUrl;
            }

            public String getScore()
            {
                return score;
            }

            public String getDistance()
            {
                return distance;
            }

            public String getPrice()
            {
                return price;
            }

            public String getIsFull()
            {
                return isFull;
            }

            public String getLongitude()
            {
                return longitude;
            }

            public String getLatitude()
            {
                return latitude;
            }

            public String getIsPromotion()
            {
                return isPromotion;
            }

            public List<String> getService()
            {
                return service;
            }
        }
    }
}
