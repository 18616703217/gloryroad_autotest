package com.gloryroad.demo.utils;

import java.util.Calendar;
import java.util.Date;

public class TimesUtil {

    public static long secondToMillisecond(Integer t){
        long millisecondTime = 0;

        if(t > 60 || t < 0){
            return millisecondTime;
        }
        millisecondTime = t * 1000;

        return millisecondTime;
    }

    public static long minuteToMillisecond(Integer t){
        long millisecondTime = 0;

        if(t > 60 || t < 0){
            return millisecondTime;
        }
        millisecondTime = t * 60 * 1000;

        return millisecondTime;
    }

    public static long hourToMillisecond(Integer t){
        long millisecondTime = 0;

        if(t > 24 || t < 0){
            return millisecondTime;
        }
        millisecondTime = t * 60 * 60 * 1000;
        return millisecondTime;
    }

    public static long dayToMillisecond(Integer t){
        long millisecondTime = 0;
        if(t < 0){
            return millisecondTime;
        }
        millisecondTime = t * 24 * 60 * 60 * 1000;
        return millisecondTime;
    }

    public static long weekToMillisecond(Integer t){
        long millisecondTime = 0;

        if(t < 0){
            return millisecondTime;
        }

        millisecondTime = (long) t * 7 * 24 * 60 * 60 * 1000;
        return millisecondTime;
    }

    public static long monthToMillisecond(Integer t, long oldActionTime){
        long millisecondTime = 0;

        if(t < 0){
            return millisecondTime;
        }
        Calendar calendar = Calendar.getInstance();

        Date oldActionDate = new Date(oldActionTime);
        calendar.setTime(oldActionDate);
        calendar.add(Calendar.MONTH, 1);//增加一个月
        millisecondTime = calendar.getTime().getTime() - oldActionDate.getTime();

        return millisecondTime;
    }
}
