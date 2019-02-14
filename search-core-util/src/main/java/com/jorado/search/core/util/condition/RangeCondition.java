package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;

/**
 * 区间查询条件
 */
public final class RangeCondition extends Condition {

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
        super(field, String.format("[%s TO %s]", from, to));
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
    protected QueryMode setMode() {
        return QueryMode.RANGE;
    }

    @Override
    protected String buildQuery() {
        return String.format("%s:%s", this.getField(), this.getValue());
    }
}
