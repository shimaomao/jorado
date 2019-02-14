package com.jorado.core.util;

import java.util.Calendar;

/**
 * Created by Administrator on 14-12-15.
 */
public class TimespanUnit {

    private int value=13;
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    private int interval;
    public int getInterval() {
        return interval;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }
    private TimespanUnit(int value,int interval){
        this.value=value;
        this.interval=interval;
    }

    public static TimespanUnit SECOND = new TimespanUnit(13,1);
    public static TimespanUnit MINUTE= new TimespanUnit(12,1);
    public static TimespanUnit HOUR= new TimespanUnit(10,1);
    public static TimespanUnit DAY= new TimespanUnit(5,1);

    public Calendar addNextTime(){
        Calendar nextTime=Calendar.getInstance();
        nextTime.add(this.getValue(),this.getInterval());
        return nextTime;
    }



    public TimespanUnit adjustInterval(int interval) {

        TimespanUnit unit = new TimespanUnit(this.value,interval);
        return unit;

    }

}
