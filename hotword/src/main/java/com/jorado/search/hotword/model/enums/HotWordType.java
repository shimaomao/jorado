package com.jorado.search.hotword.model.enums;

/**
 * 热词类型
 */
public enum HotWordType {

    FULL_INDEX(0), POSITION(1), COMPANY(2), SCHOOL(3), CITY(4);

    private int value;

    HotWordType(int value) {
        this.value = value;
    }

    public static HotWordType getDataType(int value) {

        switch (value) {
            case 1:
                return POSITION;

            case 2:
                return COMPANY;

            case 3:
                return SCHOOL;

            case 4:
                return CITY;

            default:
                return FULL_INDEX;
        }
    }

    public int getValue() {
        return this.value;
    }
}
