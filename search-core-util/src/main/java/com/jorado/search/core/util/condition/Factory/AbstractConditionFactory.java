package com.jorado.search.core.util.condition.Factory;

import com.jorado.search.core.util.condition.*;
import com.jorado.search.core.util.enums.QueryOperate;
import com.jorado.search.core.util.exception.InvalidConditionException;

import java.util.List;

public abstract class AbstractConditionFactory implements ConditionFactory {

    @Override
    public String buildQuery(Condition condition) {

        try {

            StringBuilder sb = new StringBuilder();
            sb.append(this.toQuery(condition));
            sb.append(this.buildQuery(condition.getOrCondition(), QueryOperate.OR));
            sb.append(this.buildQuery(condition.getAndCondition(), QueryOperate.AND));
            sb.append(this.buildQuery(condition.getNotCondition(), QueryOperate.NOT));

            return sb.toString();
        } catch (Throwable ex) {
            throw new InvalidConditionException("Loop condition is not allowed！！！");
        }
    }

    protected abstract String toQuery(InCondition condition);

    protected abstract String toQuery(RangeCondition condition);

    protected abstract String toQuery(KeywordCondition condition);

    protected abstract String toQuery(CoordinateCondition condition);

    protected abstract String toQuery(QueryCondition condition);

    private String toQuery(Condition condition) {

        switch (condition.getQueryMode()) {
            case IN:
                return toQuery((InCondition) condition);
            case RANGE:
                return toQuery((RangeCondition) condition);
            case KEYWORD:
                return toQuery((KeywordCondition) condition);
            case COORDINATE:
                return toQuery((CoordinateCondition) condition);
            default:
                return toQuery((QueryCondition) condition);

        }
    }

    private String buildQuery(List<Condition> conditions, QueryOperate queryOperate) {
        StringBuilder sb = new StringBuilder();
        if (!conditions.isEmpty()) {
            sb.append(String.format(" %s ", queryOperate));

            sb.append("(");

            int index = 0;
            for (Condition c : conditions) {
                if (index > 0) {
                    sb.append(String.format(" %s ", queryOperate));
                }
                sb.append(this.buildQuery(c));
                index++;
            }

            sb.append(")");

        }
        return sb.toString();
    }
}
