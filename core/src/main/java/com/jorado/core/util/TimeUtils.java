package com.jorado.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static Date parse(String text) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date time = null;
        try {
            time = formatDate.parse(text);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("time=null; ParseException e");
        }
        return time;
    }

    public static String getStringDateShort(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static EnumCompare compare(Date time) {

        return compare(new Date(), time);
    }

    public static EnumCompare compare(Date firstTime, Date secondTime) {
        Calendar ftc = Calendar.getInstance();
        ftc.setTime(firstTime);
        Calendar stc = Calendar.getInstance();
        stc.setTime(secondTime);
        int i = ftc.compareTo(stc);
        if (i > 0) {
            return EnumCompare.GT;
        } else if (i < 0) {
            return EnumCompare.LT;
        } else {
            return EnumCompare.EQ;
        }

    }

    public static boolean compare(Date firstTime, EnumCompare compare, Date secondTime) {
        //System.out.println("firstTime="+TimeHepler.getStringDateShort(firstTime));
        //System.out.println("secondTime="+TimeHepler.getStringDateShort(secondTime));
        Calendar ftc = Calendar.getInstance();
        ftc.setTime(firstTime);
        Calendar stc = Calendar.getInstance();
        stc.setTime(secondTime);
        int i = ftc.compareTo(stc);
        if (i > 0) {
            return compare == EnumCompare.GT;
        } else if (i < 0) {
            return compare == EnumCompare.LT;
        } else {
            return compare == EnumCompare.EQ;
        }

    }

    public static enum EnumCompare {
        EQ, GT, LT
    }
}

