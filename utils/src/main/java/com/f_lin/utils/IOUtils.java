package com.f_lin.utils;

import java.io.*;

/**
 * IOUtils
 *
 */
public final class IOUtils {

    private static final int EOF = -1;
    private static final int BUFFER_SIZE = 4096;

    /**
     * Reader to String
     *
     * @param reader reader
     * @return
     * @throws IOException
     */
    public static String toString(Reader reader) throws IOException {
        try (Reader closeableReader = reader; Writer writer = new StringWriter()) {
            char[] buffer = new char[BUFFER_SIZE];
            int n;
            while ((n = closeableReader.read(buffer)) != EOF) {
                writer.write(buffer, 0, n);
            }
            return writer.toString();
        }
    }

    /**
     * InputStream to String
     *
     * @param input input
     * @return
     * @throws IOException
     */
    public static String toString(InputStream input) throws IOException {
        return toString(new InputStreamReader(input));
    }

    /**
     * Input to Output
     *
     * @param input  input
     * @param output output
     * @return
     * @throws IOException
     */
    public static long output(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        long count;
        int n = input.read(buffer);
        for (count = 0; n != EOF; count += n) {
            output.write(buffer, 0, n);
        }
        return count;
    }
}
