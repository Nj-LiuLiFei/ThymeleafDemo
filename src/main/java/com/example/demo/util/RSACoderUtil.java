package com.example.demo.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSACoderUtil {

    /**
     * 非对称加密密钥算法
     */
    private  static  final  String KEY_ALGORITHM = "RSA";

    /**
     * 私钥
     */
    private  static  String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIzkN515FNtfNZRxRUY" +
            "Ix7PiEqGFv7KbLXWgVKU7ugDezsEy7gU6v04zF1JhxV3DbdA5jCvr9yYxT4NUq31VwWJ7SlJTTG4Yu3Mtf90iXUTrXmm4oTB/" +
            "IWwKF0bbtkUbUOz0MQp4Xpt/o4OlTpMVoifTQ6mrHLgnYfzGolHQG3L3AgMBAAECgYEAi5Ezyqn8UEfjktYWUoc2FOrZm+" +
            "obePmhsckOsNQWsPAGjno+MS39/qA79R7nXbfPm5q2UXghz1K/upBwhOsjckaDspEOefZXUjHCkvA2s3z15aeRwCQ/" +
            "+P06aL6du/vpvlE+HH0PfmWgiYmtEWYTeW3mCZ/Ff48EH90XWI7VMTECQQDhMarFjo0FJTYMhYWaJj8C27JVXKA7KUVlXVsTT" +
            "PE+h+r+udg9CdlN1jyXxo6X1Wpj2z8KyDPseo3FMlDZ+6YpAkEAoCpCxN5E3oFvnjBfouV7UOcd+D0JeSbMd+TR3iEFjwZ5ZG" +
            "ZE1dn8zHfny3m5E3t0qDJgR6pkM3HUmksBbhI0HwJBAM4oZvs3FnjYaROrhPTrAJKASFGHW5Tton86qO8RaGVjjsmln4W5p3t" +
            "vnscf2li74wsJozwerhJW28Tk3EnC7IkCQHv3+fIzMA8hFGnWe0+0W7yFBvdrFvgFBeIO5DoQSSMpGbCST9lOfpMsg+dEcfIYb" +
            "nT5ePg/dIs1Ih/CynqN8HUCQQCi6nMWDsY8ntVtQEHB7VlaeT8COCweN7Tv/VKMJOHLnKgGC4wDm14fqqnPmVfAHKXdqk4oCBv" +
            "QOdB2vu1o5iTF";

    /**
     * 公钥
     */
    private  static  String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCM5DedeRTbXzWUcUVGCMez4hKhhb+y" +
            "my11oFSlO7oA3s7BMu4FOr9OMxdSYcVdw23QOYwr6/cmMU+DVKt9VcFie0pSU0xuGLtzLX/dIl1E615puKEwfyFsChdG27ZFG1D" +
            "s9DEKeF6bf6ODpU6TFaIn00Opqxy4J2H8xqJR0Bty9wIDAQAB";

    /**
     * 解密数据长度,由于一次性解密的数据量太大，容易出现最大值错误
     * 分开解密，一次解密128位。
     */
    private  static  final int MAX_DECRYPT_BLOCK = 128;

    /**
     * RSA密钥长度
     * 默认1024位
     * 密钥长度必须是64的倍数
     * 范围在512-65536位之间
     */
    private static  final int KEY_SIZE = 1024;

    /**
     * slf4j 日志
     */
    private static  final Logger logger = LoggerFactory.getLogger(RSACoderUtil.class);

    /**
     * 初始化密钥
     */
    private static void initKey(){
        if(PRIVATE_KEY !=null && PUBLIC_KEY != null){
            return ;
        }
        //实例化密钥对生成器
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            //初始化密钥对生成器
            keyPairGenerator.initialize(KEY_SIZE);
            //生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            //公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            //保存密钥对
            PRIVATE_KEY = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
            System.out.println("==========私钥—内部解密使用，不得公开===========");
            System.out.println(PRIVATE_KEY);
            System.out.println("==========公钥—数据传输加密使用，可以对外公开===========");
            PUBLIC_KEY = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            System.out.println(PUBLIC_KEY);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }
    /**
     * 获取公钥
     * @return String
     */
    public static String getPrivateKey(){
        return PRIVATE_KEY;
    }
    /**
     * 获取私钥
     * @return byte[]  私钥
     */
    private static byte[] getPrivateKeyToByte(){
        return Base64.decodeBase64(getPrivateKey()) ;
    }

    /**
     * 获取公钥
     * @return String
     */
    public static String getPublicKey(){
        return PUBLIC_KEY;
    }

    /**
     * 获取公钥
     * @return byte[]
     */
    public static byte[] getPublicKeyToByte(){
        return Base64.decodeBase64(getPublicKey());
    }

    /**
     * 公钥加密
     * @param  text 待加密数据
     * @return String 加密数据
     */
    public static String encryptByPublicKey(String text){
        byte[] data;
        if(text==null || text.isEmpty()) {
            return "";
        }
        data = text.getBytes();
        //获取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(getPublicKeyToByte());
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            return Base64.encodeBase64String(cipher.doFinal(data));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥解密
     * @param  text 待解密数据
     * @return String 解密数据
     */
    public static  String decryptByPrivateKey(String text){
        byte[] data;
        if(text==null || text.isEmpty()) {
            return "";
        }
        data = Base64.decodeBase64(text);
        //取得私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(getPrivateKeyToByte());
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            //生成私钥
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //对数据进行解密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            //分段解密
            int inputLen = data.length;
            //开始点
            int offSet = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while (inputLen-offSet > 0) {
                if(inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    out.write(cipher.doFinal(data,offSet,MAX_DECRYPT_BLOCK));
                }else {
                    out.write(cipher.doFinal(data,offSet,inputLen-offSet));
                }
                offSet=offSet+MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData,"UTF-8");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    static {
        //initKey();
    }

}
