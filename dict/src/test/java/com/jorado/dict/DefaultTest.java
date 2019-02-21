package com.jorado.dict;

import org.junit.Test;

public class DefaultTest {

    @Test
    public void test() {
        System.out.println(DictUtils.getSchoolName("2"));

        System.out.println(DictUtils.getProvinceNameByRegion("3294"));
        System.out.println(DictUtils.getCityName("622"));
        System.out.println(DictUtils.getEducationName("1", 1));
        System.out.println(DictUtils.getMajorName("73"));
        System.out.println(DictUtils.getProvinceCodeByRegion("919"));
        System.out.println(DictUtils.getProvinceCodeByRegion("544"));
        System.out.println(DictUtils.getRegionName("2164"));


        System.err.println(DictUtils.getParentTypeName("6"));
        System.err.println(DictUtils.getSubJobTypeName("6"));
        System.err.println(DictUtils.getSubJobTypeName("9"));
        System.err.println(DictUtils.getParentTypeName("656"));
        System.err.println(DictUtils.getSubJobTypeName("050"));

    }
}
