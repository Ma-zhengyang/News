package com.example.mazhengyang.news.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mazhengyang on 18-8-16.
 */

public class NewsBean{

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2018-08-20 00:00","title":"快狗打车回应改名：没有其他任何含义指向","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/9F73885D594743930ED59A417B3AAF020F77C9A7_size63_w600_h721.jpeg","url":"http://news.ifeng.com/a/20180820/59897988_0.shtml"},{"ctime":"2018-08-20 00:00","title":"警惕！死亡游戏又有新玩法？翻版\u201c蓝鲸游戏\u201d又来了","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/3CC8DB8F44A500B8F08D8CB17DE86ECA2F68EB25_size46_w900_h648.jpeg","url":"http://news.ifeng.com/a/20180820/59898093_0.shtml"},{"ctime":"2018-08-20 00:00","title":"广州一3岁男童称遭幼儿园老师针扎下体 警方介入调查","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/192131119C5DC4BAD9460309896415F40A787629_size144_w600_h450.jpeg","url":"http://news.ifeng.com/a/20180820/59897192_0.shtml"},{"ctime":"2018-08-20 00:00","title":"不看新闻联播就是下等人？本人回应了","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p3.ifengimg.com/a/2018_34/64081c39c8c4fbf_size53_w750_h750.jpg","url":"http://news.ifeng.com/a/20180820/59897713_0.shtml"},{"ctime":"2018-08-20 00:00","title":"南京东路，3位异乡人的最后旅程","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/4C15426C2A95BD4FDE02119E67305BABD713B093_size149_w1080_h720.jpeg","url":"http://news.ifeng.com/a/20180820/59896802_0.shtml"},{"ctime":"2018-08-20 00:00","title":"为帮儿还债亲妈堵女儿门要钱八小时 女儿卖房还离婚","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/EE9B6262D77DB6CEDBE7DB074EB084BFEF380A50_size111_w540_h302.png","url":"http://news.ifeng.com/a/20180820/59897024_0.shtml"},{"ctime":"2018-08-20 00:00","title":"用烟头为难环卫工人，是有多缺德","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95","url":"http://news.ifeng.com/a/20180820/59896123_0.shtml"},{"ctime":"2018-08-20 00:00","title":"新婚妻子肚子上竟有剖腹产痕迹 报警后才知真相\u2026","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/D8515526FD895C54380E3F6DA8FEBF63C7958933_size19_w656_h468.jpeg","url":"http://news.ifeng.com/a/20180820/59896162_0.shtml"},{"ctime":"2018-08-20 00:00","title":"异烟肼灭犬论文作者：实验仅限实验室 为防治狂犬病","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p1.ifengimg.com/fck/2018_34/1ef09d1a37334b2_w1400_h1050.jpg","url":"http://news.ifeng.com/a/20180820/59894120_0.shtml"},{"ctime":"2018-08-20 00:00","title":"海外抗癌仿制药黑市调查：代购中的假药风险","description":"凤凰社会","picUrl":"http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/B77D6E69BA2444E35AABC998D91B85769E1936E7_size115_w450_h300.jpeg","url":"http://news.ifeng.com/a/20180820/59895527_0.shtml"}]
     */

    private int code;
    private String msg;
    private List<Entity> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Entity> getEntitylist() {
        return newslist;
    }

    public void setEntitylist(List<Entity> entitylist) {
        this.newslist = newslist;
    }

    public class Entity {
        /**
         * ctime : 2018-08-20 00:00
         * title : 快狗打车回应改名：没有其他任何含义指向
         * description : 凤凰社会
         * picUrl : http://d.ifengimg.com/w150_h95/p0.ifengimg.com/pmop/2018/0820/9F73885D594743930ED59A417B3AAF020F77C9A7_size63_w600_h721.jpeg
         * url : http://news.ifeng.com/a/20180820/59897988_0.shtml
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
