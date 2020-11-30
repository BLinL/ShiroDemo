package com.eg.shiro.demo.uitls;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/*加密算法*/
public final class EncryptUtil {

    private static final String SALT = "DEFAULT_SALT";

    private static final String ALGORITH_NAME = "SHA-256";

    private static final int HASH_ITERATIONS = 2;

    public static String encrypt(String str){
        return new SimpleHash(
                ALGORITH_NAME,
                str,
                ByteSource.Util.bytes(SALT),
                HASH_ITERATIONS).toString();
    }

    public static String encrypt(String str,String salt){
        return new SimpleHash(
                ALGORITH_NAME,
                str,
                ByteSource.Util.bytes(salt),
                HASH_ITERATIONS).toString();
    }

    public static void main(String[] args) {
        System.out.println(encrypt("admin","admin.salt"));
        System.out.println(encrypt("test","test"));
    }

}
