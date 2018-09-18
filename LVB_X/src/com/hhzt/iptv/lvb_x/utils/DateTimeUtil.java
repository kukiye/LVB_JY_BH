package com.hhzt.iptv.lvb_x.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.hhzt.iptv.R;
import com.hhzt.iptv.lvb_x.Constant;
import com.hhzt.iptv.lvb_x.LVBXApp;
import com.hhzt.iptv.lvb_x.log.LogUtil;

import static com.hhzt.iptv.lvb_x.utils.Lunar.chineseDateFormat;

/**
 * @author wujichang 2013-04-18
 */
@SuppressLint("SimpleDateFormat")
public class DateTimeUtil {
    public static final long MINUTE_MILLIS = 60 * 1000;
    public static final long HOUR_MILLIS = MINUTE_MILLIS * 60;
    /**
     * milliseconds of a day
     */
    public static final long DAY_MILLIS = HOUR_MILLIS * 24;
    /**
     * milliseconds of a half day
     */
    public static final long HALF_DAY_MILLIS = DAY_MILLIS / 2;
    /**
     * milliseconds of a week
     */
    public static final long WEEK_MILLIS = DAY_MILLIS * 7;
    /**
     * milliseconds of a month
     */
    public static final long MONTH_MILLIS = WEEK_MILLIS * 30;
    public static final long HALF_MONTH_MILLIS = MONTH_MILLIS / 2;
    /**
     * yyyyMMdd
     */
    public static final String DATE_DEFAULT_FORMATE = "yyyyMMdd";
    /**
     * yyyy-MM-dd 2010-05-11
     */
    public static final String DATE_DEFAULT_FORMATE_OTHER = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss 2010-05-11 17:22:26
     */
    public static final String DATE_FORMATE_ALL = "yyyy-MM-dd HH:mm:ss";

    /**
     * MM-dd HH:mm:ss 05-15 12:12:45
     */
    public static final String DATE_FORMAT_NO_YEAR = "MM-dd HH:mm:ss";

    /**
     * MM-dd 12-15
     */
    public static final String DATE_FORMATE_MONTH_DATE = "MM-dd";
    /**
     * yyyy-MM-dd HH:mm 2010-05-11 17:22
     */
    public static final String DATE_FORMATE_ALL_NO_SECONDES = "yyyy-MM-dd HH:mm";

    /**
     * MM-dd HH:mm 02-12 12:45
     */
    public static final String DATE_FROMAT_NO_YEAR_SECONDS = "MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss 2010-05-11 17:22:26 am
     */
    public static final String DATE_FORMATE_ALL_DETAILE = "yyyy-MM-dd HH:mm:ss aa";
    /**
     * dd/MM/yyyy, hh:mm
     */
    public static final String DATE_FORMATE_TRANSACTION = "dd/MM/yyyy, hh:mm";
    /**
     * MM/dd HH:mm 10/12 23:12
     */
    public static final String DATE_FORMATE_DAY_HOUR_MINUTE = "MM/dd HH:mm";

    /**
     * MM/dd HH:mm:ss
     */
    public static final String DATE_FORMATE_DAY_HOUR_MINUTE_SECONDES = "MM/dd HH:mm:ss";

    /**
     * MM/dd
     */
    public static final String DATE_FORMATE_MONTH_DAY = "MM/dd";

    /**
     * yyyy/MM/dd
     */
    public static final String DATE_FORMATE_YEAR_MONTH_DAY = "yyyy/MM/dd";

    /**
     * HH:mm
     */
    public static final String DATE_FORMATE_HOUR_MINUTE = "HH:mm";
    public static final String DATE_FORMATE_HOUR_MINUTE_SECOND = "HH:mm:ss";

    /**
     * yyyyMMdd
     */
    public static final String DATE_FORMATE_TO_SERVER = "yyyyMMddHHmmss";

    /**
     * dd/MM/yyyy HH:mm:ss
     */
    public static final String DATE_FORMATE_DMY_TIME = "dd/MM/yyyy HH:mm:ss";

    /**
     * "yyyy-MM-dd hh:mm:ss"
     */
    public static final String DATE_FORMATE_YMD_TIME = "yyyy/MM/dd hh:mm:ss";

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat dateFormate = new SimpleDateFormat();

    public static long ONE_DAY_TIME = 1000 * 60 * 60 * 24;

    /**
     * @param milliseconds
     * @return the time formated by "yyyy-MM-dd HH:mm:ss"
     */
    public static String toTime(long milliseconds) {
        Date date = new Date();
        date.setTime(milliseconds);
        return toTime(date, DATE_FORMATE_ALL);
    }

    /**
     * @param milliseconds
     * @param pattern
     * @return the time formated
     */
    public static String toTime(long milliseconds, String pattern) {
        Date date = new Date();
        date.setTime(milliseconds);
        return toTime(date, pattern);
    }

    /**
     * @param milliseconds
     * @return 农历日期
     */
    public static String toLunarDate(long milliseconds) {
        Lunar lunar = null;
        Date date = new Date();
        date.setTime(milliseconds);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String d = dateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Lunar.chineseDateFormat.parse(d));

            lunar = new Lunar(calendar);


        } catch (ParseException e) {
            LogUtil.i(e.getMessage());
            e.printStackTrace();
        }


        return lunar.toString();
    }

    /**
     * @param date
     * @param pattern
     * @return
     */
    public static String toTime(Date date, String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = DATE_DEFAULT_FORMATE;
        }
        dateFormate.applyPattern(pattern);
        if (date == null) {
            date = new Date();
        }
        try {
            return dateFormate.format(date);
        } catch (Exception e) {
            LogUtil.e("Exception=" + e);
            return "";
        }
    }

    public static Date toDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date dateResult = null;
        try {
            dateResult = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateResult;
    }

    /**
     * 获得指定日期的前几天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay, String pattern, int index) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - index);

        String dayBefore = new SimpleDateFormat(pattern).format(c.getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay, String pattern) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
        return dayAfter;
    }

    /**
     * 计算某天和今天相隔多少天 精确到ms
     *
     * @param day
     * @return
     */
    public static int getDiffDaysFromToday(String day) {
        Date date = new Date();
        long currentDate = date.getTime();
        int diff1 = (int) (currentDate / ONE_DAY_TIME);

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_DEFAULT_FORMATE_OTHER, Locale.CHINA);
        long dayValue = 0;
        try {
            dayValue = sdf.parse(day).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int diff2 = (int) (dayValue / ONE_DAY_TIME);
        return diff2 - diff1;
    }

    /**
     * 计算某天和今天相隔多少天 精确到天
     *
     * @param fDate
     * @param oDate
     * @return
     */
    public static int daysOfTwo(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;
    }

    /**
     * 计算某天和今天相隔多少天 精确到天
     *
     * @param fDate
     * @param oDate
     * @return
     * @throws ParseException
     */
    public static int daysOfTwo(String fDate, String oDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_DEFAULT_FORMATE, Locale.CHINA);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(fDate);
            date2 = sdf.parse(oDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return daysOfTwo(date1, date2);
    }

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getTimeStr(String pattern) {
        String str = "";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        str = df.format(new Date());
        return str;
    }

    public static int getCurrenYear() {
        Calendar aCalendar = Calendar.getInstance();
        return aCalendar.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        Calendar aCalendar = Calendar.getInstance();
        return aCalendar.get(Calendar.MONTH) + 1;
    }

    public static int getCurrentDay() {
        Calendar aCalendar = Calendar.getInstance();
        return aCalendar.get(Calendar.DATE);
    }

    public static int getCurrentHour() {
        Calendar aCalendar = Calendar.getInstance();
        return aCalendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinute() {
        Calendar aCalendar = Calendar.getInstance();
        return aCalendar.get(Calendar.MINUTE);
    }

    public static String GetNowDate(String pattern) {
        String temp_str = "";
        Date dt = new Date();
        // 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        temp_str = sdf.format(dt);
        return temp_str;
    }

    public static long ServerTimeToTime(String serverTimeString) {
        long millionSeconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_TO_SERVER);

        try {
            millionSeconds = sdf.parse(serverTimeString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millionSeconds;
    }

    /**
     * 获取居今天往后间隔指定的天数
     *
     * @param date
     * @param num
     * @param pattern
     * @return
     */
    public static String getDayDateString(Date date, int num, String pattern) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        date = calendar.getTime(); // 这个时间就是日期往后推num天的结果
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取日期时间
     *
     * @param dateString
     * @return
     */
    public static String getDateTimeAllDetails(String dateString, String sorucePattern, String desPattern) {
        Date date = DateTimeUtil.toDate(dateString, sorucePattern);
        String result = toTime(date.getTime(), desPattern);

        return result;
    }

    /**
     * 获取日期时间数据数组
     *
     * @return
     */
    public static ArrayList<String> initDateTimeFormat(int totalDays, String pattern) {
        Date date = new Date();
        ArrayList<String> dateTimeItemList = new ArrayList<String>();
        date.setTime(System.currentTimeMillis());
        for (int i = 0; i < totalDays; i++) {
            String dayString = DateTimeUtil.getDayDateString(date, i, pattern);
            dateTimeItemList.add(dayString);
        }
        return dateTimeItemList;
    }

    /**
     * 获取日期时间数据数组/重载
     *
     * @return
     */
    public static ArrayList<String> initDateTimeFormat(long beginTimeMillis, int totalDays, String pattern) {
        Date date = new Date();
        ArrayList<String> dateTimeItemList = new ArrayList<String>();
        date.setTime(beginTimeMillis);
        for (int i = 0; i < totalDays; i++) {
            String dayString = DateTimeUtil.getDayDateString(date, i, pattern);
            dateTimeItemList.add(dayString);
        }
        return dateTimeItemList;
    }

    /**
     * 获取某天为星期几
     *
     * @param num
     * @return
     */
    public static String getWeekTime(int num) {
        Date date = new Date();// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);// 把日期往后增加一天.整数往后推,负数往前移动
        String week = convertDayToWeek(calendar.get(Calendar.DAY_OF_WEEK));
        return week;
    }

    public static String getWeekTime(int num, long milliseconds) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(milliseconds);
        calendar.add(Calendar.DATE, num);// 把日期往后增加一天.整数往后推,负数往前移动
        String week = convertDayToWeek(calendar.get(Calendar.DAY_OF_WEEK));
        return week;
    }

    /**
     * 获取距当天几天后是星期几
     *
     * @param day
     * @return
     */
    public static String convertDayToWeek(int day) {
        String week = null;
        switch (day) {
            case 1:
                week = LVBXApp.getApp().getString(R.string.sunday);
                break;
            case 2:
                week = LVBXApp.getApp().getString(R.string.monday);
                break;
            case 3:
                week = LVBXApp.getApp().getString(R.string.tuesday);
                break;
            case 4:
                week = LVBXApp.getApp().getString(R.string.wednesday);
                break;
            case 5:
                week = LVBXApp.getApp().getString(R.string.thursday);
                break;
            case 6:
                week = LVBXApp.getApp().getString(R.string.friday);
                break;
            case 7:
                week = LVBXApp.getApp().getString(R.string.saturday);
                break;
            default:
                week = "error";
                break;
        }
        return week;
    }

    /**
     * 获取指定时区当前的时间
     *
     * @param timeZone
     * @param pattern
     * @return
     */
    public static String getTimeInTimeZone(String timeZone, String pattern) {
        DateFormat sdf = new SimpleDateFormat(pattern);
        Calendar ca = Calendar.getInstance();
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String time = sdf.format(ca.getTime());
        return time;
    }


}
