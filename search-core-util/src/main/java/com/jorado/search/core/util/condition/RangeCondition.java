package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;

/**
 * 区间查询条件
 */
public class RangeCondition extends Condition {

    private String from;
    private String to;

    public RangeCondition(String field, Object from) {
        this(field, from.toString());
    }

    public RangeCondition(String field, String from) {
        this(field, from, "*");
    }

    public RangeCondition(String field, Object from, Object to) {
        this(field, from.toString(), to.toString());
    }

    public RangeCondition(String field, String from, String to) {
        super(field);
        this.from = from;
        this.to = to;
    }

    public static Condition newCondition(String field, Object from) {
        return new RangeCondition(field, from);
    }

    public static Condition newCondition(String field, String from) {
        return new RangeCondition(field, from);
    }

    public static Condition newCondition(String field, Object from, Object to) {
        return new RangeCondition(field, from, to);
    }

    public static Condition newCondition(String field, String from, String to) {
        return new RangeCondition(field, from, to);
    }

    @Override
    public QueryMode getQueryMode() {
        return QueryMode.RANGE;
    }

    @Override
    public String getValue() {
        return String.format("%s,%s", from, to);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
