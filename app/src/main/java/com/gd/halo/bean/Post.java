package com.gd.halo.bean;

/**
 * Created by zhouxin on 2016/6/7.
 * Description:
 */
public class Post {

    /**
     * count : 32
     * date : 2016-06-07
     * excerpt : 摘录了『如何评价电影《美人鱼》以及为什么这么多人黑周星驰？』、『你最喜欢的英国男演员是谁？』、『有哪些笑完之后发人深省的笑话？』、『自学PHP有哪些书籍和教程值得推荐？』、『什么样的细节会让你突然觉得这个人智商高得可怕？』、『学生生涯中，你听说过哪些奇葩的和老师作对的方法?』、『男朋友没钱是一种怎样的体验？』、『如果一个司机被交警拦下后，在交警面前喝下酒，怎么判断他之前是否是酒驾呢？』、『有哪些冷门却好用的东西可以网购？』等问题下的32个答案
     * id : 2184
     * name : archive
     * pic : http://www.kanzhihu.com/wp-content/uploads/2016/06/archive-2016-06-07.jpg
     * publishtime : 1465290000
     */

    private String count;
    private String date;
    private String excerpt;
    private String id;
    private String name;
    private String pic;
    private String publishtime;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }
}
