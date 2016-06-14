package com.gd.halo.bean;

import com.gd.halo.util.Constant;
import com.gd.halo.util.PatternUtils;

import java.io.Serializable;

/**
 * 缓存到数据库需要实现接口Serializable
 */
public class NewsBean implements Serializable{
    private String title;
    private String link;
    private String description;
    private String pubTime;

    private int is_collected = 0;

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public void setPubTimeWithFormat(String pubTime) {
        this.pubTime = formatTime(pubTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = formatClearHtmlLabel(title);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public int getIs_collected() {
        return is_collected;
    }

    public void setIs_collected(int is_collected) {
        this.is_collected = is_collected;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionWithFormat(String description) {
        this.description = formatClearHtmlLabel(description);
    }

    private String formatTime(String pubTime) {
        String date = PatternUtils.RegexFind("-.{4} ", pubTime) + "年" +
                formatMonth(PatternUtils.RegexFind("-.{3}-", pubTime)) + "月" +
                PatternUtils.RegexFind(",.{1,2}-", pubTime) + "日" +
                PatternUtils.RegexFind(" .{2}:", pubTime) + "点" +
                PatternUtils.RegexFind(":.{2}:", pubTime) + "分" +
                PatternUtils.RegexFind(":.{2} ", pubTime) + "秒";
        return date;
    }

    private String formatClearHtmlLabel(String string) {
        return this.description = PatternUtils.RegexReplace("<[^>\n]*>", string, "");
    }

    private int formatMonth(String month) {
        for (int i = 1; i < Constant.MONTH.length; i++)
            if (month.equalsIgnoreCase(Constant.MONTH[i]))
                return i;
        return -1;
    }
}

