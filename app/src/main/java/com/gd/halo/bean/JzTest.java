package com.gd.halo.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Created by zhouxin on 2016/11/15.
 * Description:
 */
@Table("jz_test")
public class JzTest {

    @Column("id")
    public int id;
    @Column("duration")
    public long duration;  //剩余时间
    @Column("createdAt")
    public Date createdAt; //开始时间
    @Column("publishedAt")
    public Date publishedAt; //交卷时间
    @Column("score")
    public int score;   //得分

}
