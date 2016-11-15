package com.gd.halo.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by zhouxin on 2016/11/15.
 * Description:
 */
@Table("jz_test_question")
public class JzTestQuestion {
    @Column("id")
    public int id;
    @Column("test_id")
    public int test_id;
    @Column("question_id")
    public int question_id;
    @Column("check_position")
    public int checkPosition;
}
