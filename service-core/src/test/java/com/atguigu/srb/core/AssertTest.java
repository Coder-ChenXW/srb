package com.atguigu.srb.core;

import com.atguigu.common.result.ResponseEnum;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class AssertTest {


    @Test
    public void test1(){
        Object o = null;

        if (o == null) {
            throw new IllegalArgumentException("参数错误");
        }

    }

    @Test
    public void test2(){
        Object o = null;
        Assert.notNull(o, "参数错误");
    }

}
