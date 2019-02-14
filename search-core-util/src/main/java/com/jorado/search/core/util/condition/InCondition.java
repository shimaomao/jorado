package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;
import com.jorado.search.core.util.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 多值查询条件
 */
public final class InCondition extends Condition {

    public InCondition(String query) {
        super(query);
    }

    public InCondition(String field, String value) {
        super(field, value);
    }

    public InCondition(String field, List<String> values) {
        this(field, StringUtils.joinString(values));
    }

    public InCondition(String field, String[] values) {
        this(field, StringUtils.joinString(values));
    }

    public static Condition newCondition(String query) {
        return new InCondition(query);
    }

    public static Condition newCondition(String field, String value) {
        return new InCondition(field, value);
    }

    public static Condition newCondition(String field, List<String> value) {
        return new InCondition(field, value);
    }

    public static Condition newCondition(String field, String[] value) {
        return new InCondition(field, value);
    }

    @Override
    protected QueryMode setMode() {
        return QueryMode.IN;
    }

    @Override
    protected String buildQuery() {
        this.setValue(this.getValue().replaceAll(";", ","));
        String[] values = StringUtils.splitString(this.getValue(), ",", true);
        List<String> items = new ArrayList<>();
        for (String v : values) {
            items.add(String.format("%s:%s", this.getField(), v));
        }
        return StringUtils.joinString(items, " OR ");
    }
}
