package com.jorado.core.util;

import java.util.Calendar;

public enum EnumTimeUnit {
    SECOND(13, 1),
    MINUTE(12, 1),
    HOUR(10, 1),
    DAY(5, 1),;

    private int interval;

    public int getInterval() {
        return interval;
    }

    public EnumTimeUnit adjustInterval(int interval) {

        this.interval = interval;
        if (this == EnumTimeUnit.SECOND) {
            EnumTimeUnit unit = EnumTimeUnit.SECOND;
            unit.interval = interval;
            return unit;
        } else if (this == EnumTimeUnit.HOUR) {
            EnumTimeUnit unit = EnumTimeUnit.HOUR;
            unit.interval = interval;
            return unit;
        } else if (this == EnumTimeUnit.DAY) {
            EnumTimeUnit unit = EnumTimeUnit.DAY;
            unit.interval = interval;
            return unit;
        } else {
            EnumTimeUnit unit = EnumTimeUnit.MINUTE;
            unit.interval = interval;
            return unit;
        }

    }

    private int value = 13;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private EnumTimeUnit(int value, int interval) {
        this.value = value;
        this.interval = interval;
    }

    public Calendar addNextTime() {
        Calendar nextTime = Calendar.getInstance();
        nextTime.add(this.getValue(), this.getInterval());
        return nextTime;
    }

}
