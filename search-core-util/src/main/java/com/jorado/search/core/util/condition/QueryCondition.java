package com.jorado.search.core.util.condition;

import com.jorado.search.core.util.enums.QueryMode;

/**
 * 查询条件
 */
public final class QueryCondition extends Condition {

    private String value;

    public QueryCondition(String field, String value) {
        super(field);
        this.value = value;
    }

    public QueryCondition(String field, Object value) {
        this(field, value.toString());
    }

    @Override
    public QueryMode getQueryMode() {
        return QueryMode.NORMAL;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
