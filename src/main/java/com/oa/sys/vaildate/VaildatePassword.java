package com.oa.sys.vaildate;
import com.oa.sys.util.EncryptUtil;
/**
 * 校验密码是否有效
 */

public class VaildatePassword {
    public  static final int SALT_SIZE=8;
    public  static final int HASH_ITERATIONS=1024;

    public boolean vaildatePsd(String plainPsd,String encryptPsd) {
        //将密文逆转，截取salt
        byte[] salt = EncryptUtil.decodeHex(encryptPsd.substring(0, SALT_SIZE * 2));
        //重新平凑 盐+密码 进行加密
        byte[] hashPassword = EncryptUtil.sha1(plainPsd.getBytes(),salt, HASH_ITERATIONS);
        String newEncrypPsd = EncryptUtil.encodeHex(salt) + EncryptUtil.encodeHex(hashPassword);
        boolean flag = false;
        flag = newEncrypPsd.equals(encryptPsd);
        return flag;
    }
    public  String encyptPassword(String plainPassword){
        //生成随机数，所谓的盐
        byte[] salt =EncryptUtil.generateSalt(SALT_SIZE);
        //盐+密码   进行sha1的加密
        byte[] hashPass = EncryptUtil.sha1(plainPassword.getBytes(), salt, HASH_ITERATIONS);
        //盐可逆加密+(盐+密码 sha1加密后)可逆加密
        return EncryptUtil.encodeHex(salt) + EncryptUtil.encodeHex(hashPass);

    }


}
