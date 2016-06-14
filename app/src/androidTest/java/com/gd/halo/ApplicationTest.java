package com.gd.halo;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    /**
     * 注意：所有测试方法必须以test开头，否则没有测试选项
     * @throws Exception
     */
    public void test() throws Exception {
        int input1 = 1;
        int input2 = 2;
        assertEquals(input1, input2);
    }
}