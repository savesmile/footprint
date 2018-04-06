package com.f_lin.utils;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Base64;

/**
 * Base64Utils
 */
public class Base64Utils {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final Base64.Decoder MIME_DECODER = Base64.getMimeDecoder();

    /**
     * @param src origin string
     * @return
     */
    public static final String encode(String src) {
        return ENCODER.encodeToString(src.getBytes());
    }

    /**
     * @param src origin string
     * @return
     * @throws IOException
     */
    public static final String decode(String src) throws IOException {
        return new String(DECODER.decode(src));
    }

    /**
     * @param src origin string
     * @return
     * @throws IOException
     */
    public static final InputStream switchBase64InputStream(String src) throws IOException {
        // 去除图片信息前缀
        if (src.contains(",")) {
            src = src.split(",")[1];
        }
        byte[] bytes = MIME_DECODER.decode(src);
        return new ByteArrayInputStream(bytes);
    }


}
