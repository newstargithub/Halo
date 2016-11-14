package com.gd.halo.bean;

/**
 * Created by zhouxin on 2016/11/11.
 * Description: 驾照题库
 * 请注意:	当四个选项都为空的时候表示判断题,item1:正确 item2:错误,请开发者自行判断!
 *
 */
public class Jztk {

    /**
     * id : 14
     * question : 这个标志是何含义？
     * answer : 3
     * item1 : 专用停车场
     * item2 : 露天停车场
     * item3 : 室内停车场
     * item4 : 内部停车场
     * explains : 你看那个P字上边还有个遮挡，说明是室内的，如果没有上边那个遮挡就是露天停车场。
     * url : http://images.juheapi.com/jztk/c1c2subject1/14.jpg
     */

    private int id;
    private String question;    //问题
    private String answer;      //答案
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String explains;    //答案解释
    private String url;

    public String myAnswer;
    public int checkPosition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
