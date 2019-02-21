package com.jorado.search.core.util.condition.Factory;

import com.jorado.search.core.util.condition.*;

public class ApiConditionFactory extends AbstractConditionFactory {

    @Override
    protected String toQuery(InCondition condition) {
        return String.format("%s:array|%s", condition.getField(), condition.getValue());
    }

    @Override
    protected String toQuery(RangeCondition condition) {
        return String.format("%s:range|%s", condition.getField(), condition.getValue());
    }

    @Override
    protected String toQuery(KeywordCondition condition) {
        return String.format("%s:string|%s", condition.getField(), condition.getValue());
    }

    @Override
    protected String toQuery(CoordinateCondition condition) {
        return String.format("%s:coordinate|%s", condition.getField(), condition.getValue());
    }

    @Override
    protected String toQuery(QueryCondition condition) {
        return String.format("%s:string|%s", condition.getField(), condition.getValue());
    }
}
