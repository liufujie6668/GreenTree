package com.jerry.greentree.entity;

import java.util.List;

/**
 * Created by Administrator on 16-1-5.
 */
public class KeyWord
{
    private String result;
    private String message;

    private List<ResponseDataEntity> responseData;

    public void setResult(String result)
    {
        this.result = result;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setResponseData(List<ResponseDataEntity> responseData)
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

    public List<ResponseDataEntity> getResponseData()
    {
        return responseData;
    }

    public static class ResponseDataEntity
    {
        private String groupName;

        private List<ItemsEntity> items;

        public void setGroupName(String groupName)
        {
            this.groupName = groupName;
        }

        public void setItems(List<ItemsEntity> items)
        {
            this.items = items;
        }

        public String getGroupName()
        {
            return groupName;
        }

        public List<ItemsEntity> getItems()
        {
            return items;
        }

        public static class ItemsEntity
        {
            private String itemId;
            private String title;
            private String count;

            public void setItemId(String itemId)
            {
                this.itemId = itemId;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public void setCount(String count)
            {
                this.count = count;
            }

            public String getItemId()
            {
                return itemId;
            }

            public String getTitle()
            {
                return title;
            }

            public String getCount()
            {
                return count;
            }
        }
    }
}
