package com.gd.halo.bean.data;

import com.gd.halo.bean.Jztk;

import java.util.List;

/**
 * Created by zhouxin on 2016/11/10.
 * Description:
 */
public class JztkData extends BaseData{

    /* 选择考试科目类型，1：科目1；4：科目4*/
    public static final String SUBJECT_1 = "1";
    public static final String SUBJECT_4 = "4";
    /*驾照类型，可选择参数为：c1,c2,a1,a2,b1,b2；当subject=4时可省略*/
    public static final String MODEL_C1 = "c1";
    public static final String MODEL_C2 = "c2";
    public static final String MODEL_A1 = "a1";
    public static final String MODEL_A2 = "a2";
    public static final String MODEL_B1 = "b1";
    public static final String MODEL_B2 = "b2";
    /*测试类型，rand：随机测试（随机100个题目），order：顺序测试（所选科目全部题目）*/
    public static final String TESTTYPE_RAND = "rand";
    public static final String TESTTYPE_ORDER = "order";

    public List<Jztk> result;

}
