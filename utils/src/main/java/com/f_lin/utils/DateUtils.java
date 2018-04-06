package com.f_lin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtils

 */
public class DateUtils {
    private static final int NUM1 = -1;
    private static final Long NUM1000 = 1000L;
    private static final Long NUM60 = 60L;
    private static final Long NUM24 = 24L;
    private static final Long NUM30 = 30L;

    /**
     * @param source source
     * @param format format
     * @return
     * @throws ParseException
     */
    public static final Date pasreString(String source, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(source);
    }

    /**
     * @param date 日期
     * @return
     */
    public static int getAge(Date date) {
        return new Date().getYear() - date.getYear();
    }

    /**
     * @param date 日期
     * @return
     */
    public static boolean month(Date date) {
        if (date == null || System.currentTimeMillis() - date.getTime() > NUM1000 * NUM60 * NUM60 * NUM24 * NUM30) {
            return false;
        }
        return true;
    }

    /**
     * 比较两个时间的大小
     *
     * @param date  date
     * @param date1 date1
     * @return
     */
    public static int compare(Date date, Date date1) {
        long timestap = 0;
        long timestap1 = 0;
        if (date != null) {
            timestap = date.getTime();
        }
        if (date1 != null) {
            timestap1 = date1.getTime();
        }
        long l = timestap - timestap1;
        return l > 0 ? 1 : l == 0 ? 0 : NUM1;
    }


}
