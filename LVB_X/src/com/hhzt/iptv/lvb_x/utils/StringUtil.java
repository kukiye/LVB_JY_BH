/**
 * Copyright (c) 2013--2016
 * All rights reserved.
 *
 * @author Jhonson
 * 2013-8-16 下午3:22:41
 */
package com.hhzt.iptv.lvb_x.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String URL_REG_EXPRESSION = "^(https?://)?([a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+)+(/*[A-Za-z0-9/\\-_&:?\\+=//.%]*)*";
    public static final String EMAIL_REG_EXPRESSION = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";

    /**
     * @param s
     * @return
     */
    public static boolean isUrl(String s) {
        if (s == null) {
            return false;
        }
        return Pattern.matches(URL_REG_EXPRESSION, s);
    }

    /**
     * @param s
     * @return
     */
    public static boolean isEmail(String s) {
        if (s == null) {
            return true;
        }
        return Pattern.matches(EMAIL_REG_EXPRESSION, s);
    }

    /**
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if (input == null || "null".equalsIgnoreCase(input) || "".equals(input) || "0".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(String input) {
        if (input == null || "null".equalsIgnoreCase(input) || "".equals(input)) {
            return true;
        }

        return false;
    }

    /**
     * @param s
     * @return
     */
    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }
        return Pattern.matches("\\s*", s);
    }

    /**
     * @param spliter
     * @param arr
     * @return
     */
    public static String join(String spliter, Object[] arr) {
        if (arr == null || arr.length == 0) {
            return "";
        }
        if (spliter == null) {
            spliter = "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                break;
            }
            if (arr[i] == null) {
                continue;
            }
            builder.append(arr[i].toString());
            builder.append(spliter);
        }
        return builder.toString();
    }

    public static String join(Object[] elements, CharSequence separator) {
        return join(Arrays.asList(elements), separator);
    }

    public static String join(Iterable<? extends Object> elements, CharSequence separator) {
        StringBuilder builder = new StringBuilder();

        if (elements != null) {
            Iterator<? extends Object> iter = elements.iterator();
            if (iter.hasNext()) {
                builder.append(String.valueOf(iter.next()));
                while (iter.hasNext()) {
                    builder.append(separator).append(String.valueOf(iter.next()));
                }
            }
        }

        return builder.toString();
    }

    public static String fixLastSlash(String str) {
        String res = str == null ? File.separator : str.trim() + File.separator;
        if (res.length() > 2 && res.charAt(res.length() - 2) == '/')
            res = res.substring(0, res.length() - 1);
        return res;
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static int convertToInt(String str) throws NumberFormatException {
        int s, e;
        for (s = 0; s < str.length(); s++)
            if (Character.isDigit(str.charAt(s)))
                break;
        for (e = str.length(); e > 0; e--)
            if (Character.isDigit(str.charAt(e - 1)))
                break;
        if (e > s) {
            try {
                return Integer.parseInt(str.substring(s, e));
            } catch (NumberFormatException ex) {
                throw new NumberFormatException();
            }
        } else {
            throw new NumberFormatException();
        }
    }

    public static String generateTime(double time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds) : String.format(Locale.getDefault(),
                "%02d:%02d", minutes, seconds);
    }

    public static int getLongTime(String time) {
        String[] times = time.split(":");
        int milliseconds = 0;
        if (times.length == 2) {
            milliseconds = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
        } else if (times.length == 3) {
            milliseconds = Integer.parseInt(times[0]) * 3600 + Integer.parseInt(times[1]) * 60 + Integer.parseInt(times[2]);
        }
        return milliseconds * 1000;
    }

    /**
     * @param f
     * @return
     * @throws IOException
     */
    public static String fromFile(File f) throws IOException {
        InputStream is = new FileInputStream(f);
        byte[] bs = new byte[is.available()];
        is.read(bs);
        is.close();
        return new String(bs);
    }

    /**
     * @param f
     * @param s
     * @throws IOException
     */
    public static void toFile(File f, String s) throws IOException {
        // 只有手机rom有足够的空间才写入本地缓存
        if (CommonUtil.enoughSpaceOnPhone(s.getBytes().length)) {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(s.getBytes());
            fos.close();
        }
    }

    /**
     * 判断number参数是否是整型数表示方式
     *
     * @param number
     * @return
     */
    public static boolean isIntegerNumber(String number) {
        if (StringUtil.isEmpty(number)) {
            return false;
        }
        number = number.trim();
        String intNumRegex = "\\-{0,1}\\d+";// 整数的正则表达式
        if (number.matches(intNumRegex))
            return true;
        else
            return false;
    }

    /**
     * 判断number参数是否是浮点数表示方式
     *
     * @param number
     * @return
     */
    public static boolean isFloatPointNumber(String number) {
        if (StringUtil.isEmpty(number)) {
            return false;
        }
        number = number.trim();
        String pointPrefix = "(\\-|\\+){0,1}\\d*\\.\\d+";// 浮点数的正则表达式-小数点在中间与前面
        String pointSuffix = "(\\-|\\+){0,1}\\d+\\.";// 浮点数的正则表达式-小数点在后面
        if (number.matches(pointPrefix) || number.matches(pointSuffix))
            return true;
        else
            return false;
    }

    public static int[] stringToIntArray(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        String[] strs = str.split("\\.");
        int[] nums = new int[strs.length];
        if (strs != null && strs.length > 0) {
            for (int i = 0; i < strs.length; i++) {
                if (isIntegerNumber(strs[i])) {
                    nums[i] = StringUtil.convertToInt(strs[i]);
                }
            }
        }
        return nums;
    }
}