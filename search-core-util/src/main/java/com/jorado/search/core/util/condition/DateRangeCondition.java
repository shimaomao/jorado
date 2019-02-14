package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;

/**
 * 日期区间查询条件
 */
public final class DateRangeCondition extends Condition {

    public DateRangeCondition(String field, String from) {
        super(field, String.format("[%s TO *]", from));
    }

    public DateRangeCondition(String field, String from, String to) {
        super(field, String.format("[%s TO %s]", from, to));
    }

    public static Condition newCondition(String field, String from) {
        return new DateRangeCondition(field, from);
    }

    public static Condition newCondition(String field, String from, String to) {
        return new DateRangeCondition(field, from, to);
    }

    @Override
    protected QueryMode setMode() {
        return QueryMode.DATE_RANGE;
    }

    @Override
    protected String buildQuery() {
        return String.format("%s:%s", this.getField(), this.getValue());
    }
}
