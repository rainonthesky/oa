package com.oa.sys.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class EncrptTest {
    // 测试不可逆的md5加密算法 e10adc3949ba59abbe56e057f20f883e
    @Test
    public void testMD5() {
        String plainPsd = "123456";
        String encrptPsd = DigestUtils.md5Hex(plainPsd.getBytes());
        System.out.println(encrptPsd);
    }
}

