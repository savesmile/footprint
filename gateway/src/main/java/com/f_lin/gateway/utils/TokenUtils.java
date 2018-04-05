package com.f_lin.gateway.utils;

import com.f_lin.gateway.po.Token;

/**
 * token序列化反序列化工具
 *
 * @author F_lin
 * @since 2018/4/5
 **/
public class TokenUtils {
    public static String encryptionToken(Token token) {
        return Encryptor.input(token.toString()).aes(Token.DEFAULT_SALT).hex();
    }

    public static Token decryptionToken(String token) {
        return new Token(Encryptor.inputHex(token).unaes(Token.DEFAULT_SALT).string());
    }
}
