package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;
import com.jorado.search.core.util.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件
 */
public final class QueryCondition extends Condition {

    public QueryCondition(String query) {
        super(query);
    }

    public QueryCondition(String field, String value) {
        super(field, value);
    }

    public QueryCondition(String field, Object value) {
        this(field, value.toString());
    }

    public QueryCondition(String field, List<String> value) {
        this(field, StringUtils.joinString(value));
    }

    public QueryCondition(String field, String[] value) {
        this(field, StringUtils.joinString(value));
    }

    public QueryCondition(String field, String from, String to) {
        this(field, String.format("[%s TO %s]", from, to));
    }

    public static Condition newCondition(String query) {
        return new QueryCondition(query);
    }

    public static Condition newCondition(String field, String value) {
        return new QueryCondition(field, value);
    }

    public static Condition newCondition(String field, Object value) {
        return new QueryCondition(field, value);
    }

    public static Condition newCondition(String field, List<String> value) {
        return new QueryCondition(field, value);
    }

    public static Condition newCondition(String field, String[] value) {
        return new QueryCondition(field, value);
    }

    public static Condition newCondition(String field, String from, String to) {
        return new QueryCondition(field, from, to);
    }

    @Override
    protected QueryMode setMode() {

        if (this.getValue().contains(",") || this.getValue().contains(";")) {
            return QueryMode.IN;
        }

        if (this.getValue().startsWith("[")) {
            return QueryMode.RANGE;
        }

        if (this.getValue().contains("#")) {
            return QueryMode.DATE_RANGE;
        }

        if (this.getValue().contains("-")) {
            return QueryMode.RANGE;
        }

        return QueryMode.NORMAL;
    }

    @Override
    protected String buildQuery() {

        if (this.getMode() == QueryMode.RANGE) {

            if (this.getValue().startsWith("[")) {
                return String.format("%s:%s", this.getField(), this.getValue());
            }

            String[] ranges = StringUtils.splitString(this.getValue(), "-", true);
            if (ranges.length == 2) {
                return String.format("%s:[%s TO %s]", this.getField(), ranges[0], ranges[1]);
            }
        }

        if (this.getMode() == QueryMode.DATE_RANGE) {
            if (this.getValue().startsWith("[")) {
                return String.format("%s:%s", this.getField(), this.getValue());
            }
            String[] ranges = StringUtils.splitString(this.getValue(), "^", true);
            return String.format("%s:[%s TO %s]", this.getField(), ranges[0], ranges[1]);
        }

        if (this.getMode() == QueryMode.IN) {
            this.setValue(this.getValue().replaceAll(";", ","));
            String[] values = StringUtils.splitString(this.getValue(), ",", true);
            List<String> items = new ArrayList<>();
            for (String v : values) {
                items.add(String.format("%s:%s", this.getField(), v));
            }
            return StringUtils.joinString(items, " OR ");
        }

        return String.format("%s:%s", this.getField(), this.getValue());
    }
}
