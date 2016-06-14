package com.gd.halo.support.xml;

import android.content.Context;

import com.gd.halo.R;
import com.gd.halo.bean.NewsLabel;
import com.gd.halo.util.ResUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouxin on 2016/6/3.
 * Description:
 */
public class PullParser {

    /**
     * 解析xml
     * <list>
     * <site>
     * <url>http://www.xinhuanet.com/local/news_province.xml</url>
     * <name>国内</name>
     * </site>
     * </list>
     */
    public static List<NewsLabel> parseNewsLabel(Context context) {
        List<NewsLabel> list = new ArrayList<>();
        NewsLabel bean = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            InputStreamReader reader = new InputStreamReader(ResUtil.getRowInputStream(context, R.raw.news_api));
            xpp.setInput(reader);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    String name = xpp.getName();    //获取解析器当前指向的元素的名称
                    System.out.println("Start tag " + name);
                    if (name.equals("site")) {
                        bean = new NewsLabel();
                    } else if (name.equals("url")) {
                        if (bean != null) {
                            bean.setUrl(xpp.nextText());    //获取解析器当前指向元素的下一个文本节点的值
                        }
                    } else if (name.equals("name")) {
                        if (bean != null) {
                            bean.setTitle(xpp.nextText());
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    System.out.println("End tag " + xpp.getName());
                    if (xpp.getName().equals("site")) {
                        list.add(bean);
                        bean = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    System.out.println("Text " + xpp.getText());
                }
                eventType = xpp.next();
            }
            System.out.println("End document");
            return list;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
