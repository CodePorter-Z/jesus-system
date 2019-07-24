package com.jesus.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String YYYY_MM = "yyyy-MM";

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 根据字符串获取日期
     * @param date    时间格式字符串
     * @param format  自定义格式 默认=yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static Date stringConvertToDate(String date, String format) throws ParseException {
        if(CommonUtil.isNull(date)){
            return null;
        }
        if(CommonUtil.isNull(format)){
            format = YYYY_MM_DD;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(date);
    }

    /**
     * 计算两个时间类型的差
     * @param startDate     开始时间
     * @param endDate       结束时间
     * @return       相差毫秒数
     */
    public static String getDateDiff(Date startDate, Date endDate) {
        if(CommonUtil.isNull(startDate) || CommonUtil.isNull(endDate)){
            return null;
        }
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        return computeDiff(startTime, endTime, 4);
    }

    /**
     *  时间计算辅助
     *      可获取到相差 天数，小时，分钟，毫秒
     * @param l1 开始时间时间戳
     * @param l2 结束时间时间戳
     * @param i  表达式
     * @return   相差值
     */
    private static String computeDiff(long l1, long l2, int i) {
        String value = "";
        switch (i) {
            case 1:
                //计算相差天数 获取一天的毫秒数 24*60*60*1000
                value = String.valueOf((l2 - l1) / (24 * 60 * 60 * 1000 * 30));
                break;
            case 2:
                //计算相差小时数 小时毫秒数
                value = String.valueOf((l2 - l1) / (60 * 60 * 1000));
                break;
            case 3:
                //分钟毫秒数
                value = String.valueOf((l2 - l1) / (60 * 1000));
                break;
            case 4:
                //秒毫秒数
                value = String.valueOf((l2 - l1) / 1000);
                break;
        }
        return value;
    }

    public static Date setAddDayTime(Date startDate, int days) {
        return setDateByDay(startDate,1,days);
    }

    public static Date setSubtractDayTime(Date startDate,int days){
        return setDateByDay(startDate,0,days);
    }

    public static Date setDateByDay(Date startDate, int mode, int days) {
        if (startDate == null) {
            startDate = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if (mode == 1) {
            c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        } else {
            c.set(Calendar.DATE, c.get(Calendar.DATE) - days);
        }
        return c.getTime();
    }

    public static String dateConvertToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Integer dayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        //首先定义一个calendar，必须使用getInstance()进行实例化
        Calendar aCalendar = Calendar.getInstance();
        //里面野可以直接插入date类型
        aCalendar.setTime(date);
        //计算此日期是一周中的哪一天
        int x = aCalendar.get(Calendar.DAY_OF_WEEK);
        return x;
    }

    public static Date currentTime() {
        return new Date();
    }

    public static Long getThisTimestamp(){
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
//        Date date = null;
//        try {
//            date = stringConvertToDate("2019-02-25","yyyy-MM-dd");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(date);
//        System.out.println(getDateDiff(date,new Date(),1));
//        System.out.println(setDateByDay(date,1,31));
//        System.out.println(dateConvertToString(setDateByDay(date,2,31),"yyyy-MM-dd HH:mm:ss"));
//        System.out.println(dayOfWeek(date));

//        System.out.println(dateConvertToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
//
//        String s1 = "runoob";
//        String s2 = "runoob";
//        System.out.println("s1 == s2 is:" + s1 == s2);
//
//        double[] data = new double[0];
//        System.out.println(getMaxValue(data));
//
//        A. s1 == s2 is: true
//        B. s1 == s2 is: false
//        C. true
//        D. false

//        System.out.println(StringUtils.isEmpty("1"));

//        System.out.println(CommonUtil.isObjectToNull());
    }


}

