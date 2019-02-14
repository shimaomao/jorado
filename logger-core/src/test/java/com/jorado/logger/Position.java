package com.jorado.logger;

import com.jorado.logger.util.JsonUtils;

/**
 * 职位详情
 */
public class Position {
    private String ID;
    private String JOB_ID;
    private String JOB_TITLE;
    private String JOB_DESC;
    private String JOB_URL;
    private String JOB_TYPE;
    private String JOB_NATURE;
    private String COMPANY_ID;
    private String CITY_ID;
    private String COUNTY_ID;
    private String ROOT_COMPANY_ID;
    private String MAX_SALARY;
    private String MIN_SALARY;
    private String QUANTITY_NUM;
    private String EMAIL;
    private String JOB_SOURCE_TYPE;
    private double IS_OVERSEAS_JOB;
    private double ACCEPT_MICRO_RESUME;
    private String IS_SME_JOB;
    private String DATE_CREATED;
    private String CITY_NAME;
    private String COUNTY_NAME;
    private String JOB_TYPE_NAME;
    private String COMPANY_NAME;
    private String COMPANY_NAME_SHORT;
    private String COMPANY_TYPE;
    private String COMPANY_SIZE;
    private String COMPANY_URL;
    private String INDUSTRY_ID;
    private String INDUSTRY_NAME;
    private double score;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getJOB_ID() {
        return JOB_ID;
    }

    public void setJOB_ID(String JOB_ID) {
        this.JOB_ID = JOB_ID;
    }

    public String getJOB_TITLE() {
        return JOB_TITLE;
    }

    public void setJOB_TITLE(String JOB_TITLE) {
        this.JOB_TITLE = JOB_TITLE;
    }

    public String getJOB_DESC() {
        return JOB_DESC;
    }

    public void setJOB_DESC(String JOB_DESC) {
        this.JOB_DESC = JOB_DESC;
    }

    public String getJOB_URL() {
        return JOB_URL;
    }

    public void setJOB_URL(String JOB_URL) {
        this.JOB_URL = JOB_URL;
    }

    public String getJOB_TYPE() {
        return JOB_TYPE;
    }

    public void setJOB_TYPE(String JOB_TYPE) {
        this.JOB_TYPE = JOB_TYPE;
    }

    public String getJOB_NATURE() {
        return JOB_NATURE;
    }

    public void setJOB_NATURE(String JOB_NATURE) {
        this.JOB_NATURE = JOB_NATURE;
    }

    public String getCOMPANY_ID() {
        return COMPANY_ID;
    }

    public void setCOMPANY_ID(String COMPANY_ID) {
        this.COMPANY_ID = COMPANY_ID;
    }

    public String getCITY_ID() {
        return CITY_ID;
    }

    public void setCITY_ID(String CITY_ID) {
        this.CITY_ID = CITY_ID;
    }

    public String getCOUNTY_ID() {
        return COUNTY_ID;
    }

    public void setCOUNTY_ID(String COUNTY_ID) {
        this.COUNTY_ID = COUNTY_ID;
    }

    public String getROOT_COMPANY_ID() {
        return ROOT_COMPANY_ID;
    }

    public void setROOT_COMPANY_ID(String ROOT_COMPANY_ID) {
        this.ROOT_COMPANY_ID = ROOT_COMPANY_ID;
    }

    public String getMAX_SALARY() {
        return MAX_SALARY;
    }

    public void setMAX_SALARY(String MAX_SALARY) {
        this.MAX_SALARY = MAX_SALARY;
    }

    public String getMIN_SALARY() {
        return MIN_SALARY;
    }

    public void setMIN_SALARY(String MIN_SALARY) {
        this.MIN_SALARY = MIN_SALARY;
    }

    public String getQUANTITY_NUM() {
        return QUANTITY_NUM;
    }

    public void setQUANTITY_NUM(String QUANTITY_NUM) {
        this.QUANTITY_NUM = QUANTITY_NUM;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getJOB_SOURCE_TYPE() {
        return JOB_SOURCE_TYPE;
    }

    public void setJOB_SOURCE_TYPE(String JOB_SOURCE_TYPE) {
        this.JOB_SOURCE_TYPE = JOB_SOURCE_TYPE;
    }

    public double getIS_OVERSEAS_JOB() {
        return IS_OVERSEAS_JOB;
    }

    public void setIS_OVERSEAS_JOB(double IS_OVERSEAS_JOB) {
        this.IS_OVERSEAS_JOB = IS_OVERSEAS_JOB;
    }

    public double getACCEPT_MICRO_RESUME() {
        return ACCEPT_MICRO_RESUME;
    }

    public void setACCEPT_MICRO_RESUME(double ACCEPT_MICRO_RESUME) {
        this.ACCEPT_MICRO_RESUME = ACCEPT_MICRO_RESUME;
    }

    public String getIS_SME_JOB() {
        return IS_SME_JOB;
    }

    public void setIS_SME_JOB(String IS_SME_JOB) {
        this.IS_SME_JOB = IS_SME_JOB;
    }

    public String getDATE_CREATED() {
        return DATE_CREATED;
    }

    public void setDATE_CREATED(String DATE_CREATED) {
        this.DATE_CREATED = DATE_CREATED;
    }

    public String getCITY_NAME() {
        return CITY_NAME;
    }

    public void setCITY_NAME(String CITY_NAME) {
        this.CITY_NAME = CITY_NAME;
    }

    public String getCOUNTY_NAME() {
        return COUNTY_NAME;
    }

    public void setCOUNTY_NAME(String COUNTY_NAME) {
        this.COUNTY_NAME = COUNTY_NAME;
    }

    public String getJOB_TYPE_NAME() {
        return JOB_TYPE_NAME;
    }

    public void setJOB_TYPE_NAME(String JOB_TYPE_NAME) {
        this.JOB_TYPE_NAME = JOB_TYPE_NAME;
    }

    public String getCOMPANY_NAME() {
        return COMPANY_NAME;
    }

    public void setCOMPANY_NAME(String COMPANY_NAME) {
        this.COMPANY_NAME = COMPANY_NAME;
    }

    public String getCOMPANY_NAME_SHORT() {
        return COMPANY_NAME_SHORT;
    }

    public void setCOMPANY_NAME_SHORT(String COMPANY_NAME_SHORT) {
        this.COMPANY_NAME_SHORT = COMPANY_NAME_SHORT;
    }

    public String getCOMPANY_TYPE() {
        return COMPANY_TYPE;
    }

    public void setCOMPANY_TYPE(String COMPANY_TYPE) {
        this.COMPANY_TYPE = COMPANY_TYPE;
    }

    public String getCOMPANY_SIZE() {
        return COMPANY_SIZE;
    }

    public void setCOMPANY_SIZE(String COMPANY_SIZE) {
        this.COMPANY_SIZE = COMPANY_SIZE;
    }

    public String getCOMPANY_URL() {
        return COMPANY_URL;
    }

    public void setCOMPANY_URL(String COMPANY_URL) {
        this.COMPANY_URL = COMPANY_URL;
    }

    public String getINDUSTRY_ID() {
        return INDUSTRY_ID;
    }

    public void setINDUSTRY_ID(String INDUSTRY_ID) {
        this.INDUSTRY_ID = INDUSTRY_ID;
    }

    public String getINDUSTRY_NAME() {
        return INDUSTRY_NAME;
    }

    public void setINDUSTRY_NAME(String INDUSTRY_NAME) {
        this.INDUSTRY_NAME = INDUSTRY_NAME;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public static void main(String[] args) {
        Position p = new Position();
        String jsonStr = JsonUtils.toJson(p);
        System.out.println(jsonStr);
    }
}
