package com.jerry.greentree.entity;

import java.util.List;

/**
 * Created by Administrator on 16-1-6.
 */
public class City
{
    private String result;
    private String message;
    private ResponseDataEntity responseData;

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setResponseData(ResponseDataEntity responseData)
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

    public ResponseDataEntity getResponseData()
    {
        return responseData;
    }

    public static class ResponseDataEntity
    {
        private List<?> History;
        private List<?> Recent;

        /**
         * id : 226
         * cityName : 上海
         */

        private List<HotEntity> Hot;
        /**
         * title : A
         * items : [{"id":664,"cityName":"安庆"},{"id":3590,"cityName":"安吉县"}]
         */

        private List<CommonEntity> Common;

        public void setHistory(List<?> History)
        {
            this.History = History;
        }

        public void setRecent(List<?> Recent)
        {
            this.Recent = Recent;
        }

        public void setHot(List<HotEntity> Hot)
        {
            this.Hot = Hot;
        }

        public void setCommon(List<CommonEntity> Common)
        {
            this.Common = Common;
        }

        public List<?> getHistory()
        {
            return History;
        }

        public List<?> getRecent()
        {
            return Recent;
        }

        public List<HotEntity> getHot()
        {
            return Hot;
        }

        public List<CommonEntity> getCommon()
        {
            return Common;
        }

        public static class HotEntity
        {
            private String id;
            private String cityName;

            public void setId(String id)
            {
                this.id = id;
            }

            public void setCityName(String cityName)
            {
                this.cityName = cityName;
            }

            public String getId()
            {
                return id;
            }

            public String getCityName()
            {
                return cityName;
            }
        }

        public static class CommonEntity
        {
            private String title;
            /**
             * id : 664
             * cityName : 安庆
             */

            private List<ItemsEntity> items;

            public void setTitle(String title)
            {
                this.title = title;
            }

            public void setItems(List<ItemsEntity> items)
            {
                this.items = items;
            }

            public String getTitle()
            {
                return title;
            }

            public List<ItemsEntity> getItems()
            {
                return items;
            }

            public static class ItemsEntity
            {
                private int id;
                private String cityName;

                public void setId(int id)
                {
                    this.id = id;
                }

                public void setCityName(String cityName)
                {
                    this.cityName = cityName;
                }

                public int getId()
                {
                    return id;
                }

                public String getCityName()
                {
                    return cityName;
                }
            }
        }
    }
}
