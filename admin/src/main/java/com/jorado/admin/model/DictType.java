package com.jorado.admin.model;

public enum DictType {

    POSITION(1), COMPANY(2);

    private int value;

    DictType(int value) {
        this.value = value;
    }

    public static DictType getDataType(int value) {

        switch (value) {

            case 2:
                return COMPANY;

            default:
                return POSITION;

        }
    }

    public int getValue() {
        return this.value;
    }
}
