package com.gd.halo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhouxin on 2016/6/2.
 * Description:
 */
public class PatternUtils {

    public static String RegexFind(String regex,String string,int start,int end){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        String res = string;
        while (matcher.find()){
            res = matcher.group();
        }
        return res.substring(start, res.length() - end);
    }

    public static String RegexFind(String regex,String string){
        return RegexFind(regex, string, 1, 1);
    }

    public static String RegexReplace(String regex,String string,String replace){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        return matcher.replaceAll(replace);
    }
}
