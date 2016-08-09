package com.jerry.greentree.entity;

import java.util.List;

/**
 * Created by Administrator on 15-12-31.
 */
public class HotelTopic
{

    /**
     * result : 0
     * message : 接口通信成功！
     * responseData : {"totalItems":"30","totalPage":"3","currentPage":1,"score":{"HealthPoint":"4.2","LavePoint":"4.1","ServicePoint":"4.7","SleepPoint":"4.3","AvgPoint":"4.1"},"comments":[{"score":"1","userName":"****942","content":"环境很一般，空调不给遥控器，电吹风还要楼下租借，一次性拖鞋都没有，茶叶包没有，牙杯都没盖子，早餐送你吃兰州小碗拉面，无线差的一塌糊涂，晚上直接用流量上网，格林联盟只需感受，没有最差只有更差","date":"2015/12/14"},{"score":"5","userName":"****282","content":"网上订和门市订有区别吗，为什么就没早饭，","date":"2015/12/11"},{"score":"5","userName":"****282","content":"晚上开房时说有早饭，早上说没了，太不专业，冷","date":"2015/12/11"},{"score":"4","userName":"****455","content":"无线比以前有改进，每个房间都有无线路由器，不错，就是早餐还没改善，服务态度依然很好","date":"2015/11/30"},{"score":"4","userName":"****455","content":"无线需加强，早餐需改善，服务态度不错","date":"2015/11/2"},{"score":"4","userName":"****455","content":"早餐需改善，无线需加强，服务态度不错！","date":"2015/11/2"},{"score":"4.8","userName":"****765","content":"还可以吧，仍有提升空间","date":"2015/10/19"},{"score":"4","userName":"****868","content":"很一般，还很远不是很方便。","date":"2015/10/18"},{"score":"5","userName":"****455","content":"服务态度不错，交通方便，早餐不咋的","date":"2015/10/17"},{"score":"5","userName":"****455","content":"早餐需改善，交通方便，服务态度不错","date":"2015/10/17"}]}
     */

    private String result;
    private String message;
    /**
     * totalItems : 30
     * totalPage : 3
     * currentPage : 1
     * score : {"HealthPoint":"4.2","LavePoint":"4.1","ServicePoint":"4.7","SleepPoint":"4.3","AvgPoint":"4.1"}
     * comments : [{"score":"1","userName":"****942","content":"环境很一般，空调不给遥控器，电吹风还要楼下租借，一次性拖鞋都没有，茶叶包没有，牙杯都没盖子，早餐送你吃兰州小碗拉面，无线差的一塌糊涂，晚上直接用流量上网，格林联盟只需感受，没有最差只有更差","date":"2015/12/14"},{"score":"5","userName":"****282","content":"网上订和门市订有区别吗，为什么就没早饭，","date":"2015/12/11"},{"score":"5","userName":"****282","content":"晚上开房时说有早饭，早上说没了，太不专业，冷","date":"2015/12/11"},{"score":"4","userName":"****455","content":"无线比以前有改进，每个房间都有无线路由器，不错，就是早餐还没改善，服务态度依然很好","date":"2015/11/30"},{"score":"4","userName":"****455","content":"无线需加强，早餐需改善，服务态度不错","date":"2015/11/2"},{"score":"4","userName":"****455","content":"早餐需改善，无线需加强，服务态度不错！","date":"2015/11/2"},{"score":"4.8","userName":"****765","content":"还可以吧，仍有提升空间","date":"2015/10/19"},{"score":"4","userName":"****868","content":"很一般，还很远不是很方便。","date":"2015/10/18"},{"score":"5","userName":"****455","content":"服务态度不错，交通方便，早餐不咋的","date":"2015/10/17"},{"score":"5","userName":"****455","content":"早餐需改善，交通方便，服务态度不错","date":"2015/10/17"}]
     */

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
        private String totalItems;
        private String totalPage;
        private int currentPage;
        /**
         * HealthPoint : 4.2
         * LavePoint : 4.1
         * ServicePoint : 4.7
         * SleepPoint : 4.3
         * AvgPoint : 4.1
         */

        private ScoreEntity score;
        /**
         * score : 1
         * userName : ****942
         * content : 环境很一般，空调不给遥控器，电吹风还要楼下租借，一次性拖鞋都没有，茶叶包没有，牙杯都没盖子，早餐送你吃兰州小碗拉面，无线差的一塌糊涂，晚上直接用流量上网，格林联盟只需感受，没有最差只有更差
         * date : 2015/12/14
         */

        private List<CommentsEntity> comments;

        public void setTotalItems(String totalItems)
        {
            this.totalItems = totalItems;
        }

        public void setTotalPage(String totalPage)
        {
            this.totalPage = totalPage;
        }

        public void setCurrentPage(int currentPage)
        {
            this.currentPage = currentPage;
        }

        public void setScore(ScoreEntity score)
        {
            this.score = score;
        }

        public void setComments(List<CommentsEntity> comments)
        {
            this.comments = comments;
        }

        public String getTotalItems()
        {
            return totalItems;
        }

        public String getTotalPage()
        {
            return totalPage;
        }

        public int getCurrentPage()
        {
            return currentPage;
        }

        public ScoreEntity getScore()
        {
            return score;
        }

        public List<CommentsEntity> getComments()
        {
            return comments;
        }

        public static class ScoreEntity
        {
            private String HealthPoint;
            private String LavePoint;
            private String ServicePoint;
            private String SleepPoint;
            private String AvgPoint;

            public void setHealthPoint(String HealthPoint)
            {
                this.HealthPoint = HealthPoint;
            }

            public void setLavePoint(String LavePoint)
            {
                this.LavePoint = LavePoint;
            }

            public void setServicePoint(String ServicePoint)
            {
                this.ServicePoint = ServicePoint;
            }

            public void setSleepPoint(String SleepPoint)
            {
                this.SleepPoint = SleepPoint;
            }

            public void setAvgPoint(String AvgPoint)
            {
                this.AvgPoint = AvgPoint;
            }

            public String getHealthPoint()
            {
                return HealthPoint;
            }

            public String getLavePoint()
            {
                return LavePoint;
            }

            public String getServicePoint()
            {
                return ServicePoint;
            }

            public String getSleepPoint()
            {
                return SleepPoint;
            }

            public String getAvgPoint()
            {
                return AvgPoint;
            }
        }

        public static class CommentsEntity
        {
            private String score;
            private String userName;
            private String content;
            private String date;

            public void setScore(String score)
            {
                this.score = score;
            }

            public void setUserName(String userName)
            {
                this.userName = userName;
            }

            public void setContent(String content)
            {
                this.content = content;
            }

            public void setDate(String date)
            {
                this.date = date;
            }

            public String getScore()
            {
                return score;
            }

            public String getUserName()
            {
                return userName;
            }

            public String getContent()
            {
                return content;
            }

            public String getDate()
            {
                return date;
            }
        }
    }
}
