package com.example.demo;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

class PassWordTest {
    /**
     * 加密方法
     *
     * @param plainText 需加密文本
     */
    public static void testEncrypt(String plainText) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        // 加密的算法，这个算法是默认的
        config.setAlgorithm("PBEWithMD5AndDES");
        //加密的密钥，自定义
        config.setPassword("SDSARWfdf2me23rdcscasdD2137D");
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }

    /**
     * 解密方法
     *
     * @param encryptedText 需解密文本
     */
    public static void testDecrypt(String encryptedText) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        // 解密的算法，需同加密算法相同
        config.setAlgorithm("PBEWithMD5AndDES");
        //解密的密钥，需同加密密钥相同
        config.setPassword("SDSARWfdf2me23rdcscasdD2137D");
        standardPBEStringEncryptor.setConfig(config);
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }

    public static void main(String[] args) {
        testEncrypt("root");
        testEncrypt("WANG56887539jun");
        testEncrypt("jdbc:mysql://minecraft.alinnum.com:3306/spring-security?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true");
        testDecrypt("SOB7QqRgS0pOwasLoLXMvA==");
        testDecrypt("xaAJzR6TM/YBE+xmJDt1gc0WCI7fSZr7");
    }
}